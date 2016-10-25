package lib.emerson.com.emersonapplib.domain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.pullRecycleview.RecycleViewGridAdapter;
import lib.emerson.com.emersonapplib.pullRecycleview.RecycleViewStageredAdapter;
import lib.emerson.com.emersonapplib.pullRecycleview.RecycleviewLinearAdapter;


/**
 * Created by Administrator on 2016/7/27.
 */
public class RecyclerViewThree extends baseActivity{
        private TextView tv;
        private RecyclerView rcv;
        private LinearLayoutManager linearLayoutManager;
        private List<String> lists=new ArrayList<String>();
        private RecycleviewLinearAdapter recycleviewLinearAdapter;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recyclerview_test);
            this.rcv = (RecyclerView) findViewById(R.id.rcv);
            init();
        }

    private void init() {
        linearLayoutManager=new LinearLayoutManager(RecyclerViewThree.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lists.clear();
        for(int i=0 ; i<20 ; i++){
            lists.add("123"+i);
        }
        recycleviewLinearAdapter = new RecycleviewLinearAdapter(lists);

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_top, null);
        recycleviewLinearAdapter.addHeadView(view);

        View view1 = layoutInflater.inflate(R.layout.item_foot, null);
        recycleviewLinearAdapter.addFootView(view1);

        tv =(TextView) view1.findViewById(R.id.item_foot_tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("click");
                for(int i=0 ; i < 20 ; i++){
                    lists.add("123"+i);
                }
                recycleviewLinearAdapter.notifyDataSetChanged();
            }
        });
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(recycleviewLinearAdapter);
        recycleviewLinearAdapter.setOnItemClickLitener(new RecycleviewLinearAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                toast(position + "");
            }
        });


    }

}
