package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.Adapter.RecyclerViewAdapter;

/**
 * Created by Administrator on 2016/7/8.
 */
public class RecyclerViewOne extends Activity {

    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_1);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        initData();

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));            //设置线性管理器，支持横向、纵向。
        //mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));            //GridLayoutManager 网格布局管理器
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager
                (4, StaggeredGridLayoutManager.VERTICAL));                          //瀑布式布局管理器,第二个参数决定横向，纵向。第一个决定行数或列数
        mAdapter = new RecyclerViewAdapter(this,mDatas);
        mAdapter.setOnItemClickLitener(l);
        mRecyclerView.setAdapter(mAdapter);     //设置adapter


    }

    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }


    RecyclerViewAdapter.OnItemClickLitener l = new RecyclerViewAdapter.OnItemClickLitener() {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(RecyclerViewOne.this, position + " click",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onItemLongClick(View view, int position) {
            Toast.makeText(RecyclerViewOne.this, position + " long click",
                    Toast.LENGTH_SHORT).show();
        }
    };



}
