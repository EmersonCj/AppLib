package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.Adapter.Base.Recycleview.BaseRvAdapter;
import lib.emerson.com.emersonapplib.Adapter.Base.Recycleview.RvViewHolder;
import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.entity.ChatMessage;
import lib.emerson.com.emersonapplib.utils.common.DividerItemDecoration;

/**
 * Created by YangJianCong on 2016/10/20.
 */
public class RvAdapterTestActivity extends baseActivity {
    private RecyclerView mRecyclerview;
    private BaseRvAdapter mAdapter;
    private List<String> mDatas ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_adapter_test);
        initDatas();
        mRecyclerview = (RecyclerView) findViewById(R.id.layout_id_recyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        mAdapter = new BaseRvAdapter(this,R.layout.item,mDatas) {
            @Override
            protected void convert(RvViewHolder holder, Object o, int position) {
                holder.setText(R.id.tv_content,mDatas.get(position) + "");
            }


        };

        mRecyclerview.setAdapter(mAdapter);
    }


    private void initDatas() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i <= 'z'; i++) {
            mDatas.add((char) i + "");
        }
    }

}
