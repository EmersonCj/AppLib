package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.utils.Image.ImageLoaderUtils;
import lib.emerson.com.emersonapplib.utils.Image.Images;
import lib.emerson.com.emersonapplib.view.MyScrollView;

/**
 * Created by Administrator on 2016/6/8.
 */
/*
* 1,http://blog.csdn.net/guolin_blog/article/details/9526203#comments   （用GridView做的，是规则的）
* 2,http://blog.csdn.net/guolin_blog/article/details/10470797           （用ScrollView做得，不规则的）
* 3,http://blog.csdn.net/guolin_blog/article/details/11100327           （在上面的基础上添加了查看功能）
* 下面的第一个按钮是第三个的demo（图片双击还原没做）
* */
public class PhotoWallActivity extends Activity implements View.OnClickListener {
    private Button btOne;
    private Button btTwo;
    private MyScrollView scrollView;
    private GridView gridView;

    private GridView mGridView;
    private String[] mUrlStrs = Images.imageThumbUrls;
    private ImageLoaderUtils mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photowallfalls);
        init();
    }

    private void init() {
        /* ------------------------------1---------------------------------  */
        btOne = (Button)findViewById(R.id.photo_wall_fall_bt_one);
        btTwo = (Button)findViewById(R.id.photo_wall_fall_bt_two);
        scrollView = (MyScrollView) findViewById(R.id.my_scroll_view);
        gridView = (GridView) findViewById(R.id.id_gridview);
        btOne.setOnClickListener(this);
        btTwo.setOnClickListener(this);
        /* ------------------------------2---------------------------------  */
        mImageLoader = ImageLoaderUtils.getInstance(3, ImageLoaderUtils.Type.LIFO);
        mGridView = (GridView) findViewById(R.id.id_gridview);
    }

    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.photo_wall_fall_bt_one){
            scrollView.setVisibility(View.VISIBLE);
            btOne.setVisibility(View.GONE);
            btTwo.setVisibility(View.GONE);
            gridView.setVisibility(View.GONE);
        }else {
            gridView.setVisibility(View.VISIBLE);
            btOne.setVisibility(View.GONE);
            btTwo.setVisibility(View.GONE);
            scrollView.setVisibility(View.GONE);
            setUpAdapter();
        }
    }

    /*第二个按钮的图片墙流程：
    * 1，对ImageLoaderUtils核心类进行初始化，主要是建立任务队列和后台轮询线程，线程池和初始化LruCache。
    * 2，然后用户调用loadImage（path，Imageview，isFromNet）为Imageview加载图片。先根据path在缓存中寻找bitmap
    *    2.1 缓存中存在该bitmap：直接更新UI（即将该bitmap放到Imageview，）             **完成加载**
    *    2.2 缓存中不存在该bitmap：开启一个任务去获取图片，跳到第三步
    *
    * 3，
        * 一：首先我们判断是否是网络任务？（硬盘缓存不同于内存缓存LruCache）
            1，网络图片：（首先去硬盘缓存中找一下）
                1.1  硬盘缓存有，直接调用loadImageFromLocal进行加载                        **完成加载**
                1.2  硬盘缓存中没有，那么去判断是否开启了硬盘缓存：
                    1.2.1  已经开启：先调用downloadImgByUrl（）下载图片到本地，然后使用loadImageFromLocal进行加载     **完成加载**
                    1.2.3  没有开启：则直接调用downloadImgByUrl（）从网络获取，然后直接加载到Imageview                **完成加载**

            2，本地图片：直接调用loadImageFromLocal本地加载图片的方式进行加载

        * 二：然后把图片加入到内存缓存（LruCache类）中
        * 三：最后调用refreashBitmap（）更新UI页面

        【注意：在loadImageFromLocal（）和downloadImgByUrl（）两个加载函数中，
          会利用BitmapFactory和caculateInSampleSize方法对图片进行压缩，以设配Imageview控件的大小】
    */
    private void setUpAdapter(){
        if (mGridView == null)
            return;
        if (mUrlStrs != null){
            mGridView.setAdapter(new ListImgItemAdaper(PhotoWallActivity.this, 0,mUrlStrs));
        } else{
            mGridView.setAdapter(null);
        }
    }


    private class ListImgItemAdaper extends ArrayAdapter<String> {
        public Context mcontext;

        public ListImgItemAdaper(Context context, int resource, String[] datas){
            super(context, 0, datas);
            mcontext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null){
                convertView = LayoutInflater.from(mcontext).inflate(R.layout.item_fragment_list_imgs, parent, false);
            }
            ImageView imageview = (ImageView) convertView.findViewById(R.id.id_img);
            imageview.setImageResource(R.drawable.loadlose);
            imageview.setOnClickListener(l);
            //加载图片，请看图片加载类ImageLoaderUtils
            //getItem(position)：获得相应数据集合中特定位置的数据项
            mImageLoader.loadImage(getItem(position), imageview, true);
            return convertView;
        }


        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"click",Toast.LENGTH_SHORT).show();
            }
        };
    }





}
