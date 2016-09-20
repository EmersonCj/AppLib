package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.pullRecycleview.RecycleviewLinearAdapter;

/**
 * Created by YangJianCong on 2016/9/1.
 * XRecyclerView:   http://blog.csdn.net/xsf50717/article/details/51366922
 * SwipeMenuRecyclerView :http://blog.csdn.net/yanzhenjie1003/article/details/52115566
 */
public class RecyclerViewFour extends baseActivity implements View.OnClickListener {
    private TextView tv1,tv2;
    private XRecyclerView xRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<String> lists=new ArrayList<String>();
    private RecycleviewLinearAdapter recycleviewLinearAdapter;

    private SwipeMenuRecyclerView swipeMenuRecyclerView;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_four);
        xRecyclerView = (XRecyclerView) findViewById(R.id.xrecycleview);
        swipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycleviewTwo);
        tv1 = (TextView) findViewById(R.id.tv_1);
        tv2 = (TextView) findViewById(R.id.tv_2);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        lists.clear();
        for(int i=0 ; i < 20 ; i++){
            lists.add(getRandomString("欢迎您访问广东移动网站。中国移动通信集团公司,是中国规模最大的移动通信运营商",5));
        }

        LayoutInflater inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.head,null);
        view.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv_1){
            xRecyclerView.setVisibility(View.VISIBLE);
            swipeMenuRecyclerView.setVisibility(View.GONE);
            showOne();
        }else {
            xRecyclerView.setVisibility(View.GONE);
            swipeMenuRecyclerView.setVisibility(View.VISIBLE);
            showTwo();
        }
    }

    private void showOne() {
        //初始化xRecycleview
        linearLayoutManager = new LinearLayoutManager(RecyclerViewFour.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(linearLayoutManager);
        recycleviewLinearAdapter = new RecycleviewLinearAdapter(lists);
        xRecyclerView.addHeaderView(view);
        xRecyclerView.setAdapter(recycleviewLinearAdapter);

        //设置上拉/下拉 刷新（下面统称为 刷新）
        /*设置刷新动画*/
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallScale);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulseSync);

        /*是否开启刷新功能 */
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);

        /* 下拉刷新和加载更多需要实现其接口 */
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                lists.add("--下拉刷新 数据--");
                xRecyclerView.refreshComplete();     //加载完数据，调用该方法。
            }

            @Override
            public void onLoadMore() {
                lists.add("--上拉加载 数据--");
                xRecyclerView.loadMoreComplete();   //加载完数据，调用该方法。
            }
        });


        recycleviewLinearAdapter.setOnItemClickLitener(new RecycleviewLinearAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    private void showTwo() {
        //初始化swipeMenuRecyclerView
        linearLayoutManager = new LinearLayoutManager(RecyclerViewFour.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        swipeMenuRecyclerView.setLayoutManager(linearLayoutManager);
        recycleviewLinearAdapter = new RecycleviewLinearAdapter(lists);
        swipeMenuRecyclerView.setAdapter(recycleviewLinearAdapter);

        //启用SwipeReyclerView的长按Item拖拽功能和侧滑删除功能
        /*swipeMenuRecyclerView.setLongPressDragEnabled(true);// 开启长按拖拽
        swipeMenuRecyclerView.setItemViewSwipeEnabled(true);// 开启滑动删除。
        swipeMenuRecyclerView.setOnItemMoveListener(onItemMoveListener);// 监听拖拽和侧滑删除，更新UI和数据。*/

        /*具体功能请看DEMO
        * 下载地址：https://github.com/yanzhenjie/SwipeRecyclerView
        * */
    }

    OnItemMoveListener onItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            return false;
        }

        @Override
        public void onItemDismiss(int position) {
            lists.remove(position);
            recycleviewLinearAdapter.notifyDataSetChanged();
        }
    };




}
