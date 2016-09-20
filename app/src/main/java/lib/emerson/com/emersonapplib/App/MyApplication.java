package lib.emerson.com.emersonapplib.App;

import android.app.Application;
import android.app.Service;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.xutils.DbManager;
import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.io.File;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.service.LocationService;


/**
 * Created by Administrator on 2016/6/8.
 */
public class MyApplication extends Application {
    public static MyApplication mMyApplication;
    public static DbManager.DaoConfig daoConfig;

    /** 百度地图用来实现定位功能的“客户端”*/
    public LocationClient mLocationClient;

    /**
     * Baidu地图在LocationClient每次定位成功后，都会去回调BDLocationListener监听器中的onReceiveLocation方法并传入定位获得的地点信息。
     * MyLocationListener是Baidu地图BDLocationListener监听器的实现类
     */
    public MyLocationListener mLocationListener = new MyLocationListener();

    /**定位得到的最终点（经纬度）*/
    public LatLng lastPoint;
    public  BDLocation mlocation;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化xUtils
        x.Ext.init(this);
        x.Ext.setDebug(true);               // 设置是否输出debug
        //初始化ImagerLoader
        initImagerLoader();
        initDb();       //初始化数据库
        initBaiduMap();
    }

    /**
     * 注意，该方法仅仅是对LocationClient这个定位客户端对象做了初始化 但是并没有启动它
     * 要想启动定位，必须显式的调用LocationClient的start方法才可以
     */
    private void initBaiduMap() {
        /*初始化定位sdk，建议在Application中创建*/
        SDKInitializer.initialize(getApplicationContext());

        mLocationClient = new LocationClient(getApplicationContext());          //声明LocationClient类
        mLocationClient.registerLocationListener(mLocationListener);            //注册监听函数
        initLocation();
    }


    //初始化LocationClient所需要的参数
    private void initLocation(){
        /*LocationClientOption该类是用来设置定位SDK的定位方式*/
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    /**
     * 当LocationClient定位成功以后的回调接口。定位的信息会被封装为BDLocation对象传入接口的onReceiveLocation方法中
     *
     * onReceiveLocation的逻辑是：
     * 1) 如果lastPoint为null，则将获得的位置信息提取出经纬度后赋值给lastPoint
     * 2) 如果lastPoint不为null，则比较一下这次获得的位置信息与lastPoint的位置，
     *      如果一样，则停止LocationClient，不再去获得位置信息了
     *      如果不一样，则将获得的位置信息提取出经纬度后赋值给lastPoint
     * <p/>
     * 只要没有显式调用停止LocationClient.stop()，根据设置的参数，1秒钟后LocationClient又会进行定位
     * 并将定位信息传入回调方法中与lastPoint进行比对。
     * 直到LocationClient获得的位置信息与lastPoint中的位置信息相等时，LocationClient才会定制获取位置信息
     *
     * @author Em
     */
    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            double latitude = location.getLatitude();
            double longtitude = location.getLongitude();
            mlocation = location;

            if (lastPoint != null) {
                if (lastPoint.latitude == location.getLatitude() && lastPoint.longitude == location.getLongitude()) {
                    // 若两次请求获取到的地理位置坐标是相同的，则不再定位
                    Log.e("location","两次获取坐标相同");
                    mLocationClient.stop();
                    return;
                }
            }

            //记录最近一次定位结果
            lastPoint = new LatLng(location.getLatitude(), location.getLongitude());
        }
    }

    private void initDb() {
        File file=new File(Environment.getExternalStorageDirectory().getPath());
        if(daoConfig==null){
            daoConfig=new DbManager.DaoConfig()
                    .setDbName("goods_yjc.db")  //设置数据库的名称
//                    .setDbDir(file)             //设置数据库存放的路径
                    .setDbVersion(1)            //设置数据库的版本
                    .setAllowTransaction(true)  //设置允许开启事务
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {   //设置一个版本升级的监听方法
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                        }
                    });
        }
    }

    public static MyApplication getInstance() {
        return mMyApplication;
    }


    public static DbManager.DaoConfig getDaoConfig(){
        return daoConfig;
    }

    /**
     * 初始化ImageLoader框架的对象,并设置
     */
    private void initImagerLoader() {
        //默认的Options
        DisplayImageOptions dconfig = null;
        //配置一些图片显示的选项
        dconfig = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.loadlose)//图片加载过程中显示的默认图片
                .showImageOnFail(R.drawable.loadlose)//加载图片出错显示的图片
                .cacheInMemory(true)//是否加载缓存
                .cacheOnDisc(true) //是否存储本地
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY) //图片的缩放方式
                .displayer(new FadeInBitmapDisplayer(100))//设置图片渐显的时间
                //==.FadeInBitmapDisplayer()//设置图片渐显的时间
                // .SimpleBitmapDisplayer()//正常显示一张图片
                // .displayer(new SimpleBitmapDisplayer())//设置正常显示一张图片
                .build();

        //ImageLoader的配置参数
        ImageLoaderConfiguration config =new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPoolSize(5)//设置线程数量一般1-5最好
                .denyCacheImageMultipleSizesInMemory()
                .threadPriority(Thread.MAX_PRIORITY-2)  //线程的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .memoryCacheExtraOptions(480, 800)  //缓存的最大宽高
                .memoryCache(new UsingFreqLimitedMemoryCache(2*1024*1024))  //自定义内存缓存，如果出现内存溢出可以不添加
                .memoryCacheSize(2*1024*1024)       //最大的缓存数量
                .discCacheSize(50 * 1024 * 1024)    // 50 Mb sd卡(本地)缓存的最大值
                // .diskCacheFileCount(100)  // 可以缓存的文件数量
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//加密
                .defaultDisplayImageOptions(dconfig)//设置默认情况下的Option
                .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000))
                .build(); // connectTimeout (5 s),
        ImageLoader
                .getInstance()
                .init(config);
        com.nostra13.universalimageloader.utils.L.disableLogging();
        com.nostra13.universalimageloader.utils.L.enableLogging();
    }


}