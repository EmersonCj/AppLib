package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.Adapter.CityAdapter;
import lib.emerson.com.emersonapplib.entity.CityBean;
import lib.emerson.com.emersonapplib.view.IndexBar.widget.IndexBar;
import lib.emerson.com.emersonapplib.view.IndexBar.widget.TitleItemDecoration;

/**
 * Created by YangJianCong on 2016/10/11.
 */
public class IndexRvActivity extends baseActivity {
    private RecyclerView mRv;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mManager;
    private List<CityBean> mDatas;
    private TitleItemDecoration mDecoration;
    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;

    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_rv);
        mRv = (RecyclerView) findViewById(R.id.layout_index_rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));
        initDatas(getResources().getStringArray(R.array.provinces));
        mRv.setAdapter(mAdapter = new CityAdapter(this, mDatas));
        mRv.setAdapter(mAdapter = new CityAdapter(this, mDatas));
        mRv.addItemDecoration(mDecoration = new TitleItemDecoration(this, mDatas));

        //使用indexBar
        mTvSideBarHint = (TextView) findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = (IndexBar) findViewById(R.id.indexBar);//IndexBar
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                .setmSourceDatas(mDatas);//设置数据源

    }


    private void initDatas(String[] data) {
        mDatas = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            CityBean cityBean = new CityBean();
            cityBean.setCity(data[i]);  //设置城市名称
            mDatas.add(cityBean);
        }
    }

}
