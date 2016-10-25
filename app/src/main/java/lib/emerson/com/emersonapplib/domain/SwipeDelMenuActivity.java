package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.Adapter.CommonAdapter;
import lib.emerson.com.emersonapplib.entity.SwipeBean;
import lib.emerson.com.emersonapplib.utils.ViewHolder;
import lib.emerson.com.emersonapplib.view.CstSwipeDelMenu;

/**
 * Created by YangJianCong on 2016/10/17.
 */
public class SwipeDelMenuActivity extends baseActivity{
    private static final String TAG = "yjc";
    private ListView mLv;
    private List<SwipeBean> mDatas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_del);
        mLv = (ListView) findViewById(R.id.test);
        initDatas();
        mLv.setAdapter(new CommonAdapter<SwipeBean>(this, mDatas, R.layout.item_cst_swipe) {

            @Override
            public void convert(final ViewHolder holder, SwipeBean swipeBean, final int position, View convertView) {
                ((CstSwipeDelMenu)holder.getConvertView()).setIos(false);   //这句话关掉IOS阻塞式交互效果
                holder.setText(R.id.content, swipeBean.name);
                holder.setOnClickListener(R.id.content, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SwipeDelMenuActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                    }
                });

                holder.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SwipeDelMenuActivity.this, "删除:" + position, Toast.LENGTH_SHORT).show();
                        //在ListView里，点击侧滑菜单上的选项时，如果想让擦花菜单同时关闭，调用这句话
                        ((CstSwipeDelMenu) holder.getConvertView()).quickClose();
                        mDatas.remove(position);
                        notifyDataSetChanged();
                    }
                });

            }
        });
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDatas.add(new SwipeBean("" + i));
        }
    }



}
