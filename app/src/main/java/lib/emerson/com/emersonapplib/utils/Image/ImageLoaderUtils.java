package lib.emerson.com.emersonapplib.utils.Image;

/**
 * Created by Administrator on 2016/7/21.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
        * 图片加载类
        * http://blog.csdn.net/lmj623565791/article/details/41874561
        * @author zhy
        *
            1、单例，包含一个LruCache用于管理我们的图片；
            2、任务队列，我们每来一次加载图片的请求，我们会封装成Task存入我们的TaskQueue;
            3、包含一个后台线程，这个线程在第一次初始化实例的时候启动，然后会一直在后台运行；任务呢？还记得我们有个任务队列么，有队列存任务，
               得有人干活呀；所以，当每来一次加载图片请求的时候，我们同时发一个消息到后台线程，后台线程去使用线程池去TaskQueue去取一个任务执行；
            4、调度策略；3中说了，后台线程去TaskQueue去取一个任务，这个任务不是随便取的，有策略可以选择，一个是FIFO，一个是LIFO，我倾向于后者。
        */
public class ImageLoaderUtils {


    private static ImageLoaderUtils mInstance;

    /**
     * 图片缓存的核心对象
     */
    private LruCache<String, Bitmap> mLruCache;
    /**
     * 线程池
     */
    private ExecutorService mThreadPool;
    private static final int DEAFULT_THREAD_COUNT = 1;
    /**
     * 队列的调度方式
     */
    private Type mType = Type.LIFO;
    /**
     * 任务队列
     */
    private LinkedList<Runnable> mTaskQueue;
    /**
     * 后台轮询线程
     */
    private Thread mPoolThread;
    private Handler mPoolThreadHandler;
    /**
     * UI线程中的Handler
     */
    private Handler mUIHandler;

    private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);
    private Semaphore mSemaphoreThreadPool;

    private boolean isDiskCacheEnable = true;

    private static final String TAG = "ImageLoader";

    public enum Type{
        //先进先出 ,后进先出
        FIFO, LIFO;
    }

    private ImageLoaderUtils(int threadCount, Type type){
        init(threadCount, type);
    }

    /**
     * 1,初始化
     *
     * @param threadCount
     * @param type
     */
    private void init(int threadCount, Type type){
        initBackThread();

        // 获取我们应用的最大可用内存，并初始化LruCache
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheMemory){
            @Override
            protected int sizeOf(String key, Bitmap value){
                return value.getRowBytes() * value.getHeight();
            }
        };

        //创建一个可重用固定线程数的线程池，且同一时刻只能允许threadCount个活动线程存在
        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<Runnable>();
        mType = type;
        mSemaphoreThreadPool = new Semaphore(threadCount);
    }


    /**
     * 初始化后台轮询线程，就是将普通线程变成Looper线程，所谓Looper线程就是循环工作的线程
     * 该Looper线程不断循环运行，一旦有新任务则执行，执行完继续等待下一个任务
     */
    private void initBackThread(){
        // 后台轮询线程
        mPoolThread = new Thread(){
            @Override
            public void run(){
                // 将当前线程初始化为Looper线程
                Looper.prepare();
                // 实例化handler
                mPoolThreadHandler = new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        // 每一次有任务过来，Looper线程去工作队列中取出一个任务进行执行
                        mThreadPool.execute(getTask());     //getTask: 根据Type从工作队列头或者尾进行取任务
                        try{
                            // 获取一个信号量
                            mSemaphoreThreadPool.acquire();
                        } catch (InterruptedException e)
                        {
                        }
                    }
                };
                //访问完后，释放一个信号量
                mSemaphorePoolThreadHandler.release();
                //启动looper
                Looper.loop();
            };
        };
        mPoolThread.start();
    }

    //获取ImageLoaderUtils示例，有两种形式
    public static ImageLoaderUtils getInstance()
    {
        if (mInstance == null)
        {
            synchronized (ImageLoaderUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new ImageLoaderUtils(DEAFULT_THREAD_COUNT, Type.LIFO);
                }
            }
        }
        return mInstance;
    }

    public static ImageLoaderUtils getInstance(int threadCount, Type type)
    {
        if (mInstance == null)
        {
            synchronized (ImageLoaderUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new ImageLoaderUtils(threadCount, type);
                }
            }
        }
        return mInstance;
    }


    /**
     * 根据path为imageview设置图片
     * 用户调用loadImage传入参数就可以完成本地或者网络图片的加载。
     * @param path
     * @param imageView
     */
    public void loadImage(final String path, final ImageView imageView,final boolean isFromNet){
        //为imageView设置标签
        imageView.setTag(path);
        if (mUIHandler == null){
            mUIHandler = new Handler()
            {
                public void handleMessage(Message msg)
                {
                    //获取得到图片信息，为imageview回调设置图片
                    ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
                    Bitmap bm = holder.bitmap;
                    ImageView imageview = holder.imageView;
                    String path = holder.path;
                    // 将图片的path与Imageview控件的getTag存储路径进行比较，相同才能显示，不进行这步会造成图片混乱
                    if (imageview.getTag().toString().equals(path)){
                        imageview.setImageBitmap(bm);
                    }
                };
            };
        }

        // 根据path在缓存中寻找bitmap
        Bitmap bm = getBitmapFromLruCache(path);
        if (bm != null){            //缓存中存在该bitmap，直接更新UI（即将该bitmap放到Imageview）
            refreashBitmap(path, imageView, bm);
        } else{                     //缓存中不存在该bitmap，开启一个任务去获取
            addTask(buildTask(path, imageView, isFromNet));
        }

    }

    /**
     * 根据传入的参数，新建一个任务
     * 我们新建任务，说明在内存中没有找到缓存的bitmap；
     * 所以该任务就是去根据path加载压缩后的bitmap返回即可，当然还要加入LruCache，设置回调显示
     * @param path
     * @param imageView
     * @param isFromNet
     * @return
     */

    /*
    * 一：首先我们判断是否是网络任务？（硬盘缓存不同于内存缓存LruCache）
        1，网络图片：（首先去硬盘缓存中找一下）
            1.1  硬盘缓存有，直接调用loadImageFromLocal进行加载
            1.2  硬盘缓存中没有，那么去判断是否开启了硬盘缓存：
                1.2.1  已经开启：先下载图片到本地，然后使用loadImageFromLocal进行加载
                1.2.3  没有开启：则直接从网络获取，然后直接加载到Imageview

        2，本地图片：直接调用loadImageFromLocal本地加载图片的方式进行加载

      二：然后把图片加入到内存缓存（LruCache类）中
      三：最后调用refreashBitmap（）更新UI页面
      */
    private Runnable buildTask(final String path, final ImageView imageView,final boolean isFromNet){
        return new Runnable(){
            @Override
            public void run(){
                Bitmap bm = null;
                if (isFromNet) {        //一：从网络加载
                    File file = getDiskCacheDir(imageView.getContext(),md5(path));
                    if (file.exists())  {        //1.1  先去缓存文件中去找，找到就直接加载
                        Log.e(TAG, "find image :" + path + " in disk cache .");
                        bm = loadImageFromLocal(file.getAbsolutePath(),imageView);
                    } else{                      //1.2  找不到就进行网络下载
                        if (isDiskCacheEnable){     //2.1   检测是否开启硬盘缓存，开启的话就先下载图片到本地，使用loadImageFromLocal加载本地图片的方式进行加载
                            boolean downloadState = DownloadImgUtils.downloadImgByUrl(path, file);
                            if (downloadState){// 如果下载成功
                                Log.e(TAG,"download image :" + path+ " to disk cache . path is "+ file.getAbsolutePath());
                                bm = loadImageFromLocal(file.getAbsolutePath(),imageView);
                            }
                        } else {                            //2.2   直接从网络加载图片到Imageview上
                            Log.e(TAG, "load image :" + path + " to memory.");
                            bm = DownloadImgUtils.downloadImgByUrl(path,imageView);
                        }
                    }
                } else{     //二：从本地加载
                    bm = loadImageFromLocal(path, imageView);
                }

                // 最后记得要把图片加入到缓存
                addBitmapToLruCache(path, bm);
                refreashBitmap(path, imageView, bm);
                //释放信号量
                mSemaphoreThreadPool.release();
            }
        };
    }

    //根据路径path，获取本地图片
    private Bitmap loadImageFromLocal(final String path, final ImageView imageView){
        Bitmap bm;
        // 1、获得imageView显示的大小
        ImageSizeUtil.ImageSize imageSize = ImageSizeUtil.getImageViewSize(imageView);

        // 2、根据imageView的大小对图片进行压缩
        bm = decodeSampledBitmapFromPath(path, imageSize.width,imageSize.height);
        return bm;
    }

    /**
     * 根据Type从任务队列头或者尾进行取出一个任务。
     *
     * @return
     */
    private Runnable getTask(){
        if (mType == Type.FIFO){
            return mTaskQueue.removeFirst();
        } else if (mType == Type.LIFO){
            return mTaskQueue.removeLast();
        }
        return null;
    }

    /**
     * 利用签名辅助类，将字符串字节数组
     * @param str
     * @return
     */
    public String md5(String str){
        byte[] digest = null;
        try{
            MessageDigest md = MessageDigest.getInstance("md5");
            digest = md.digest(str.getBytes());
            return bytes2hex02(digest);

        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 方式二
     *
     * @param bytes
     * @return
     */
    public String bytes2hex02(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for (byte b : bytes)
        {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() == 1)// 每个字节8为，转为16进制标志，2个16进制位
            {
                tmp = "0" + tmp;
            }
            sb.append(tmp);
        }

        return sb.toString();

    }

    private void refreashBitmap(final String path, final ImageView imageView,Bitmap bm){
        Message message = Message.obtain();
        ImgBeanHolder holder = new ImgBeanHolder();
        //设置信息，将图片和Imageview关联起来
        holder.bitmap = bm;
        holder.path = path;
        holder.imageView = imageView;

        message.obj = holder;
        mUIHandler.sendMessage(message);
    }

    /**
     * 根据图片需要显示的宽和高对图片进行压缩（本地的图片）
     *
     * @param path
     * @param width
     * @param height
     * @return
     */
    protected Bitmap decodeSampledBitmapFromPath(String path, int width,int height){
        //1，首先第一次解析，将inJustDecodeBounds设置为true，来获取图片的宽和高，但此时并不把图片加载到内存中
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        //2，计算inSampleSize值
        options.inSampleSize = ImageSizeUtil.caculateInSampleSize(options,
                width, height);

        //3，第二次解析，使用获得到的InSampleSize再次解析图片，因为这次图片是要读取出来的，所以inJustDecodeBounds设置为false
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    private synchronized void  addTask(Runnable runnable){
        //将任务线程加到工作队列中
        mTaskQueue.add(runnable);
        // if(mPoolThreadHandler==null)wait();
        try{
            if (mPoolThreadHandler == null)
                mSemaphorePoolThreadHandler.acquire();      //先尝试获取一个信号量
        } catch (InterruptedException e)
        {
        }
        mPoolThreadHandler.sendEmptyMessage(0x110);         //然后发送一个消息给后台线程，叫它去取出一个任务执行
    }

    /**
     * 获得缓存图片的地址
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public File getDiskCacheDir(Context context, String uniqueName){
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            cachePath = context.getExternalCacheDir().getPath();
        } else{
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }


    /**
     * 将图片加入LruCache
     *
     * @param path
     * @param bm
     */
    protected void addBitmapToLruCache(String path, Bitmap bm)
    {
        if (getBitmapFromLruCache(path) == null)
        {
            if (bm != null)
                mLruCache.put(path, bm);
        }
    }

    /**
     * 根据path在缓存中获取bitmap
     *
     * @param key
     * @return
     */
    private Bitmap getBitmapFromLruCache(String key){
        return mLruCache.get(key);
    }


    private class ImgBeanHolder
    {
        Bitmap bitmap;
        ImageView imageView;
        String path;
    }
}

