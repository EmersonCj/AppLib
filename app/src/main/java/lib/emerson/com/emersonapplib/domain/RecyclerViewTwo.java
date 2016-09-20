package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.Test.GoodsBean;
import lib.emerson.com.emersonapplib.adapter.GoodsAdapter;

/**
 * Created by Administrator on 2016/7/8.
 */
public class RecyclerViewTwo extends baseActivity implements View.OnClickListener {
    public Button bt_1;
    public Button bt_2;
    public RecyclerView mRecyclerView;
    private List<GoodsBean> Datas;
    private GoodsAdapter Adapter;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_2);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerView_2);
        bt_1 = (Button) findViewById(R.id.recyclerview_bt_1);
        bt_2 = (Button) findViewById(R.id.recyclerview_bt_2);
        bt_1.setOnClickListener(this);
        bt_2.setOnClickListener(this);
        initRecycler();
    }

    private void initRecycler() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(RecyclerViewTwo.this));           //布局管理器,第二个参数决定横向，纵向。第一个决定行数或列数
        createlistData();
        Adapter = new GoodsAdapter(RecyclerViewTwo.this, Datas);
        mRecyclerView.setAdapter(Adapter);          //设置adapter

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /*RecyclerView状态改变时被调用，用来获取当前RecyclerView的状态：
                空闲SCROLL_STATE_IDLE 、滑动SCROLL_STATE_TOUCH_SCROLL和惯性滑动SCROLL_STATE_FLING*/
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Log.e(TAG,newState + "");
                super.onScrollStateChanged(recyclerView, newState);
            }

            /*onSCroll在滑动过程中被调用，可以获取到RecyclerView有多少条item以及现在显示到了第几条等等一些信息*/
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.e(TAG,dx + " ---- " +dy);
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.recyclerview_bt_1){
            Adapter.setItmeType(0);
            Adapter.notifyDataSetChanged();
        }else {
            Adapter.setItmeType(1);
            Adapter.notifyDataSetChanged();
        }

    }

    private void createlistData() {
        Datas = new ArrayList<GoodsBean>();
        for(int i = 0; i < 8;i++){
            GoodsBean bean = new GoodsBean();
            bean.setAllPrice("100");
            bean.setBuyNumber("0");
            bean.setGoodsName("洛阳苹果");
            bean.setSupermarketPrice("100");
            bean.setPrice("39.8");
            Datas.add(bean);
        }
    }

}
