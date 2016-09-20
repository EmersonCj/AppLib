package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.view.MyListview;

/**
 * Created by Administrator on 2016/6/2.
 */
public class WidgetActivityTwo extends Activity {
    private List<String> listData = null;
    //想要在scrollView中正常使用Listview，必须要重写Listview，并在onMeasure阶段中计算出Listview的高度
    private MyListview lv_show = null;
    private ArrayAdapter<String> adapter = null;

    String dataArray[] = {"1","2","3","4","5","6","1","2","3","4","5","6"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrollview_test);
        lv_show = (MyListview) this.findViewById(R.id.scrollView_lv);
        lv_show.setHaveScrollbar(false);     //false为正常显示
        listData = new ArrayList<String>();
        for(int i=0; i<dataArray.length; i++)
            listData.add(dataArray[i]);
        adapter = new ArrayAdapter<String>(WidgetActivityTwo.this, R.layout.item_show, R.id.tv_item, listData);
        lv_show.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lv_show);
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
