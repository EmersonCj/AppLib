package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.cache.LruCache;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/6/8.
 * http://blog.csdn.net/guolin_blog/article/details/9316683
 */

public class LruCacheActivity extends Activity{
    @ViewInject(R.id.lrucache_tv)
    private ImageView tv;
    private LruCache<String, Bitmap> mMemoryCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lrucache);
        x.view().inject(this);
        // 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
        // LruCache通过构造函数传入缓存值，以KB为单位。
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 使用最大可用内存值的1/8作为缓存的大小。
        int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    @Event(value = R.id.lrucache_bt,type = View.OnClickListener.class)
    private void onclick(View view){
        loadBitmap(R.drawable.fruit,tv);
    }


    /* 当向 ImageView 中加载一张图片时,首先会在 LruCache 的缓存中进行检查。
       如果找到了相应的键值，则会立刻更新ImageView ，否则开启一个后台线程来加载这张图片。*/
    public void loadBitmap(int resId, ImageView imageView) {
        final String imageKey = String.valueOf(resId);
        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            Log.e("TAG","bitmap 已存在");
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.loadlose);
            BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            task.execute(resId);
        }
    }


    /* BitmapFactory这个类提供了多个解析方法(decodeByteArray, decodeFile, decodeResource等)用于创建Bitmap对象，
        我们应该根据图片的来源选择合适的方法。比如SD卡中的图片可以使用decodeFile方法，
        网络上的图片可以使用decodeStream方法，资源文件中的图片可以使用decodeResource方法*/
    //进行图片缩放处理
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // 1，首先第一次解析，将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(res, resId, options);
        //2，计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        //3，第二次解析，因为这次图片是要读取出来的，所以inJustDecodeBounds设置为false
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    //计算缩放值inSampleSize（传入图片id，还有你期望的高度和宽度）
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    //开线程去加载图片
    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        ImageView image = null;

        public BitmapWorkerTask(ImageView imageView) {
            image = imageView;
        }

        // 在后台加载图片。
        @Override
        protected Bitmap doInBackground(Integer... params) {
            final Bitmap bitmap = decodeSampledBitmapFromResource(
                    getResources(), params[0], 100, 100);
            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            tv.setImageBitmap(bitmap);
            super.onPostExecute(bitmap);
        }
    }


    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

}
