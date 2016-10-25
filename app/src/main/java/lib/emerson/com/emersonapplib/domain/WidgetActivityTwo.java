package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.view.MyListview;

/**
 * Created by Administrator on 2016/6/2.
 */
public class WidgetActivityTwo extends baseActivity {
    private List<String> listData = null;
    //想要在scrollView中正常使用Listview，必须要重写Listview，并在onMeasure阶段中计算出Listview的高度
    private MyListview lv_show = null;
    private ArrayAdapter<String> adapter = null;
    private ScrollView scrollView;


    String dataArray[] = {"1","2","3","4","5","6","1","2","3","4","5","6"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrollview_test);
        scrollView = (ScrollView) findViewById(R.id.layout_scrollView);
        lv_show = (MyListview) this.findViewById(R.id.scrollView_lv);
        lv_show.setHaveScrollbar(false);     //false为正常显示
        listData = new ArrayList<String>();
        for(int i=0; i<dataArray.length; i++)
            listData.add(dataArray[i]);
        adapter = new ArrayAdapter<String>(WidgetActivityTwo.this, R.layout.item_show, R.id.tv_item, listData);
        lv_show.setAdapter(adapter);


        findViewById(R.id.scrollView_bt_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.scrollView_bt_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(WidgetActivityTwo.this, new Intent().setClass(WidgetActivityTwo.this, ScrollViewStickyActivity.class), false);
            }
        });




    }


    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


}
