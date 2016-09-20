package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.adapter.HorizontalScrollViewAdapter2;
import lib.emerson.com.emersonapplib.view.MyHorizontalScrollView2;

/**
 * Created by Administrator on 2016/7/4.
 */
public class HorizontalActivity extends Activity {
    @ViewInject(R.id.main_middle_layout)
    private MyHorizontalScrollView2 mHorizontalScrollView;

    private HorizontalScrollViewAdapter2 mAdapter;
    private List<String> mDatas = new ArrayList<String>(Arrays.asList("蔬菜","水果","海鲜","肉类","粮油","干货","调味品","零食日用"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabview);
        x.view().inject(this);
        mAdapter = new HorizontalScrollViewAdapter2(HorizontalActivity.this, mDatas);
        //添加点击回调
        mHorizontalScrollView.setOnItemClickListener(new MyHorizontalScrollView2.OnItemClickListener(){
            @Override
            public void onClick(View view, int position)
            {
                view.setBackgroundColor(Color.parseColor("#AA024DA4"));
            }
        });
        //设置适配器
        mHorizontalScrollView.initDatas(mAdapter);
    }

}
