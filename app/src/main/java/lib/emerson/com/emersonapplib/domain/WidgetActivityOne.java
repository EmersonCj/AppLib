package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.adapter.HorizontalScrollViewAdapter;
import lib.emerson.com.emersonapplib.view.MyHorizontalScrollView;

/**
 * Created by Administrator on 2016/6/2.
 */
public class WidgetActivityOne  extends Activity{
    private MyHorizontalScrollView mHorizontalScrollView;
    private HorizontalScrollViewAdapter mAdapter;
    private ImageView mImg;

    private List<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(R.drawable.a, R.drawable.b, R.drawable.c,
            R.drawable.d, R.drawable.e, R.drawable.f,
            R.drawable.h, R.drawable.i,R.drawable.j));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);
        mImg = (ImageView) findViewById(R.id.id_content);
        mHorizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.id_horizontalScrollView);
        mAdapter = new HorizontalScrollViewAdapter(this, mDatas);

        //添加滚动回调
        mHorizontalScrollView.setCurrentImageChangeListener(new MyHorizontalScrollView.CurrentImageChangeListener()
        {
            @Override
            public void onCurrentImgChanged(int position,View viewIndicator)
            {
                Log.e("1","1");
                mImg.setImageResource(mDatas.get(position));
                viewIndicator.setBackgroundColor(Color.parseColor("#AA024DA4"));
            }
        });

        //添加点击回调
        mHorizontalScrollView.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener()
        {

            @Override
            public void onClick(View view, int position)
            {
                mImg.setImageResource(mDatas.get(position));
                view.setBackgroundColor(Color.parseColor("#AA024DA4"));
            }
        });

        //设置适配器
        mHorizontalScrollView.initDatas(mAdapter);

    }


}
