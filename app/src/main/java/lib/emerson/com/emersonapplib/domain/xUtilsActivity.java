package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.tscenter.biz.rpc.vkeydfp.result.BaseResult;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import lib.emerson.com.emersonapplib.App.MyApplication;
import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.entity.Store;
import lib.emerson.com.emersonapplib.entity.StudentInfo;
import lib.emerson.com.emersonapplib.utils.Image.BitmapUtils;
import lib.emerson.com.emersonapplib.utils.XUtils.MyCallBack;
import lib.emerson.com.emersonapplib.utils.XUtils.XutilsHttp;

/**
 * Created by Administrator on 2016/7/19.
 * XUtils3: http://blog.csdn.net/it1039871366/article/details/50607513
 *          http://blog.csdn.net/u011607885/article/details/52367105
 *
 * XUtils2: http://blog.csdn.net/dj0379/article/details/38356773/
 */


public class xUtilsActivity extends baseActivity {
    @ViewInject(R.id.iv_imgv)
    private ImageView imageView;
    private ImageOptions imageOptions;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xutils);
        /* (一) 注解模块的使用
            1.在Application的oncreate方法中加入下面代码:
            x.Ext.init(this);

            2.在Activity的oncreate方法中加入下面代码:
            x.view().inject(this);

            3.可以开始使用了。
        */
        x.view().inject(this);
        imageOptions = MyApplication.getInstance().getImageOptions();

    }


    /*
    * (二) 网络模块的使用
    * */
    @Event(value = R.id.btnHttp, type = View.OnClickListener.class)
    private void btnClickHttp(View view){
        String url="http://test.wxw7z.com/index.php/Api/Store/getStoreInfo";
        Map<String,String> map=new HashMap<>();
        map.put("store_id", "10");
        XutilsHttp.Get(url, map, new MyCallBack<String>(){

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Log.e("onSuccess", result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                Log.e("onError", ex.toString());

            }
        });

    }


    /*
    * 图片加载模块,加载方法有bind，loadDrawable，loadFile
    * */
    @Event(value = R.id.btnBitmap, type = View.OnClickListener.class)
    private void btnClickBitmap(View view){
        String url = "http://pic8.nipic.com/20100709/4752803_210430061441_2.jpg";

        /*
        * 加载图片的4个bind方法
        * */
        x.image().bind(imageView, url);
        x.image().bind(imageView, url, imageOptions);
        x.image().bind(imageView, url, imageOptions, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable arg0) {
                LogUtil.e("下载成功");
            }

            @Override
            public void onFinished() {
                LogUtil.e("下载完成");
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

                LogUtil.e("下载出错，" + arg0.getMessage());
            }

            @Override
            public void onCancelled(CancelledException arg0) {
                LogUtil.e("下载取消");
            }
        });


        /**
         * loadDrawable()方法加载图片
         */
        Callback.Cancelable cancelable = x.image().loadDrawable(url, imageOptions, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
                imageView.setImageDrawable(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
        //主动取消loadDrawable()方法
        //cancelable.cancel();

        /**
         * loadFile()方法
         * 应用场景：当我们通过bind()或者loadDrawable()方法加载了一张图片后，
         * 它会保存到本地文件中，那当我需要这张图片时，就可以通过loadFile()方法进行查找。
         * urls[0]：网络地址
         */
        x.image().loadFile(url,imageOptions,new Callback.CacheCallback<File>(){
            @Override
            public boolean onCache(File result) {
                //在这里可以做图片另存为等操作
                Log.e("JAVA","file："+result.getPath()+result.getName());
                return true; //相信本地缓存返回true
            }
            @Override
            public void onSuccess(File result) {
                Log.e("JAVA","file");
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });


    }




}
