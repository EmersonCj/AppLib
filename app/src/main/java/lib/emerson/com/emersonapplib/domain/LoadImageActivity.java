package lib.emerson.com.emersonapplib.domain;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/7/19.
 *
 * http://blog.csdn.net/xiaanming/article/details/26810303
 */
public class LoadImageActivity extends baseActivity{
    @ViewInject(R.id.load_image_bt)
    private Button bt;
    @ViewInject(R.id.load_image_iv)
    private ImageView iv;
    private String imagUrl = "http://img.ivsky.com/img/tupian/pic/201608/15/maitian_jingse-002.jpg";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);
        x.view().inject(this);
        bt.setOnClickListener(l);
    }

    View.OnClickListener l =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /* android加载图片有很多方法，主流有xUtils，Imageloader，Volley
            * 必须要掌握图片的压缩，缓存等处理
            * xUtils:除了不能加载drawble的图片，其他都可以
            * Imageloader:可以加载任何来源的图片
            */

            //使用ImageLoader之前要在MyApplication进行初始化，加载图片主要方法有displayImage(), loadImage()
            //LoadImage();
            DisplayImage();


            //除了加载网络图片，还能加载其他来源的图片
            //LoadOtherImage();


        }
    };

    private void LoadImage() {
        /*
        * loadimage()加载图片,少用
        * */
        ImageSize mImageSize = new ImageSize(100, 100);  //指定图片的大小
        ImageLoader.getInstance().loadImage(imagUrl, mImageSize,new SimpleImageLoadingListener(){

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                iv.setImageBitmap(loadedImage);
            }

        });
    }

    private void DisplayImage() {
        /*
        * displayImage(),常用。这里不需要指定DisplayImageOptions，因为在MyApplication初始化的时候已经设置好默认的DisplayImageOptions
        * ImageLoader.getInstance().displayImage(uri, ImageView, DisplayImageOptions);
        * */
        ImageLoader.getInstance().displayImage(imagUrl, iv,new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                Log.e("LoadingComplete","LoadingComplete");
            }
        });
    }


    /*
    * 来源于Content provider,drawable,assets的图片，我们只需要给每个图片来源的地方加上Scheme包裹起来(Content provider除外)，
    * 然后当做图片的url传递到imageLoader中，Universal-Image-Loader框架会根据不同的Scheme获取到输入流*/
    private void LoadOtherImage() {
        String imageUri1 = "http://site.com/image.png";                  // 网络图片
        String imageUri2 = "file:///mnt/sdcard/image.png";               //SD卡图片
        String imageUri3 = "content://media/external/audio/albumart/13"; // 媒体文件夹
        String imageUri4 = "assets://image.png";                         // assets
        String imageUri5 = "drawable://" + R.drawable.fruit;             //  drawable文件
        ImageLoader.getInstance().displayImage(imageUri5,iv);
    }

}
