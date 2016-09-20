package lib.emerson.com.emersonapplib.domain;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.Arrays;
import java.util.LinkedList;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/8/23.
 */
public class PullToRefreshLvActivity extends baseActivity {

    //上垃加载和下拉加载

    private String[] mStrings = { "Abbaye de Belloc", "Aisy Cendre","Allgauer Emmentaler" };        //显示的列表对应原字符串
    private LinkedList<String> mListItems;
    private PullToRefreshListView mPullRefreshListView;
    private ArrayAdapter<String> mAdapter;
    private boolean isRefreshing;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_one);
        init();
    }

    private void init() {
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);          //BOTH   就是上拉和下拉
        mPullRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载更多");
        mPullRefreshListView.getLoadingLayoutProxy(true,false).setPullLabel("下拉加载更多");
        mPullRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("正在加载..");
        mPullRefreshListView.getLoadingLayoutProxy().setReleaseLabel("释放马上加载");
        mPullRefreshListView.getLoadingLayoutProxy().setLoadingDrawable(getResources().getDrawable(R.drawable.icon_loadmore));  //设置加载图标


        //设置滑动监听器
        mPullRefreshListView.setOnRefreshListener(l);
        mPullRefreshListView.setOnItemClickListener(listen);
        //设置列表内容
        mListItems = new LinkedList<String>();
        mListItems.addAll(Arrays.asList(mStrings));
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);
        //这两个绑定方法用其一
        //方法一
        //mPullRefreshListView.setAdapter(mAdapter);
        //方法二
        ListView actualListView = mPullRefreshListView.getRefreshableView();
        actualListView.setAdapter(mAdapter);
    }

    private class GetDataTask extends AsyncTask<Void, Void, String> {

        //后台处理部分
        @Override
        protected String doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            String str="刷新加载的数据!!";
            return str;
        }

        //这里是对刷新的响应，可以利用addFirst（）和addLast()函数将新加的内容加到LISTView中
        //根据AsyncTask的原理，onPostExecute里的result的值就是doInBackground()的返回值
        @Override
        protected void onPostExecute(String result) {
            //在头部增加新添内容
            mListItems.addLast(result);

            //通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
            mAdapter.notifyDataSetChanged();
            mPullRefreshListView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }


    PullToRefreshBase.OnRefreshListener<ListView> l = new PullToRefreshBase.OnRefreshListener<ListView>() {
        @Override
        public void onRefresh(PullToRefreshBase<ListView> refreshView) {
            if (!isRefreshing) {
                isRefreshing = true;
                if (mPullRefreshListView.isHeaderShown()) {
                    Log.e("TAG","pull-to-refresh");
                    //refreshOnlineStatus(true);
                } else if (mPullRefreshListView.isFooterShown()) {
                    Log.e("TAG","pull-to-load-more");
                    //loadNextPage();
                }
                mPullRefreshListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshListView.onRefreshComplete();
                    }
                }, 1000);
            } else {
                mPullRefreshListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshListView.onRefreshComplete();
                    }
                }, 1000);
            }

            String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                    DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

            refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
            new GetDataTask().execute();
        }
    };

    AdapterView.OnItemClickListener listen = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(PullToRefreshLvActivity.this,position + "  is click",Toast.LENGTH_SHORT).show();
        }
    };

}
