package lib.emerson.com.emersonapplib.domain;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/7/19.
 */
public class LoadImageActivity extends baseActivity{
    @ViewInject(R.id.load_image_bt)
    private Button bt;
    @ViewInject(R.id.load_image_iv)
    private ImageView iv;
    private String imagUrl = "http://img0.imgtn.bdimg.com/it/u=659812866,2993970978&fm=21&gp=0.jpg";

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
            * 必须要掌握图片的压缩，缓存等处理*/
            //xUtilsLoadImage();    //xUtils:除了不能加载drawble的图片，其他都可以
            ImageLoaderAdd();       //Imageloader:可以加载任何来源的图片
                                    //Volley:
        }
    };

    private void ImageLoaderAdd() {
        //使用ImageLoader之前要在MyApplication进行初始化

        String imagePath = "/mnt/sdcard/image.png";
        String imageUrl = ImageDownloader.Scheme.FILE.wrap(imagePath);                              //图片来源于sd卡
        String contentprividerUrl = "content://media/external/audio/albumart/13";                   //图片来源于Content provider
        String assetsUrl = ImageDownloader.Scheme.ASSETS.wrap("image.png");                         //图片来源于assets
        String drawableUrl = ImageDownloader.Scheme.DRAWABLE.wrap("R.drawable.a");                  //图片来源于drawable
        ImageLoader.getInstance().displayImage(drawableUrl, iv);
    }

    private void xUtilsLoadImage() {
        // 设置加载图片的参数
        ImageOptions options = new ImageOptions.Builder()
                // 是否忽略GIF格式的图片
                .setIgnoreGif(false)
                // 图片缩放模式
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                // 下载中显示的图片
                .setLoadingDrawableId(R.drawable.loadlose)
                // 下载失败显示的图片
                .setFailureDrawableId(R.drawable.loadlose)
                // 得到ImageOptions对象
                .build();
        // 加载图片
        x.image().bind(iv, imagUrl, options, new Callback.CommonCallback<Drawable>() {
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

    }


}
