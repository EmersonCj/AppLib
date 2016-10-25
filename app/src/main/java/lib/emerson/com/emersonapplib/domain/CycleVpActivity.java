package lib.emerson.com.emersonapplib.domain;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.view.ImageCycleView;


/**
 * Created by Administrator on 2016/7/18.
 */
public class CycleVpActivity extends baseActivity {
    private Button bt1,bt2;

    private ImageCycleView mImageCycleView;
    private List<ImageCycleView.ImageInfo> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle);
        bt1 = (Button) findViewById(R.id.cycle_bt_one);
        bt2 = (Button) findViewById(R.id.cycle_bt_two);
        mImageCycleView = (ImageCycleView) findViewById(R.id.icv_topView);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageCycleView.setVisibility(View.VISIBLE);
                initCycleView();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CycleVpActivity.this,BannerActivity.class));
            }
        });




    }

    private void initCycleView() {
        list = new ArrayList<ImageCycleView.ImageInfo>();
        initdata();

        mImageCycleView.setOnPageClickListener(new ImageCycleView.OnPageClickListener() {
            @Override
            public void onClick(View imageView, ImageCycleView.ImageInfo imageInfo) {
                Toast.makeText(CycleVpActivity.this, "你点击了" + imageInfo.value.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        mImageCycleView.loadData(list, new ImageCycleView.LoadImageCallBack() {
            @Override
            public ImageView loadAndDisplay(ImageCycleView.ImageInfo imageInfo) {

//                //本地图片
//                ImageView imageView=new ImageView(CycleVpActivity.this);
//                imageView.setImageResource(Integer.parseInt(imageInfo.image.toString()));
//                return imageView;


//				//使用SD卡图片
//				SmartImageView smartImageView=new SmartImageView(MainActivity.this);
//				smartImageView.setImageURI(Uri.fromFile((File)imageInfo.image));
//				return smartImageView;

//				//使用SmartImageView，既可以使用网络图片也可以使用本地资源
//				SmartImageView smartImageView=new SmartImageView(MainActivity.this);
//				smartImageView.setImageResource(Integer.parseInt(imageInfo.image.toString()));
//				return smartImageView;

                //使用BitmapUtils,只能使用网络图片
                ImageView imageView = new ImageView(CycleVpActivity.this);
                ImageLoader.getInstance().displayImage(imageInfo.image.toString(),imageView);
                return imageView;


            }
        });
    }

    private void initdata() {
        //res图片资源
        /*list.add(new ImageCycleView.ImageInfo(R.drawable.a1,"111111111111",""));
        list.add(new ImageCycleView.ImageInfo(R.drawable.a2,"222222222222222",""));*/

        //SD卡图片资源
//		list.add(new ImageCycleView.ImageInfo(new File(Environment.getExternalStorageDirectory(),"a1.jpg"),"11111",""));
//		list.add(new ImageCycleView.ImageInfo(new File(Environment.getExternalStorageDirectory(),"a2.jpg"),"22222",""));


        //使用网络加载图片
        list.add(new ImageCycleView.ImageInfo("http://img.lakalaec.com/ad/57ab6dc2-43f2-4087-81e2-b5ab5681642d.jpg","1",""));
        list.add(new ImageCycleView.ImageInfo("http://img.lakalaec.com/ad/cb56a1a6-6c33-41e4-9c3c-363f4ec6b728.jpg","2",""));
        list.add(new ImageCycleView.ImageInfo("http://img.lakalaec.com/ad/e4229e25-3906-4049-9fe8-e2b52a98f6d1.jpg","3",""));
    }


}

