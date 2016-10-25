package lib.emerson.com.emersonapplib.domain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hyphenate.cloud.CustomMultiPartEntity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.Adapter.Base.Listview.BaseLvAdapter;
import lib.emerson.com.emersonapplib.Adapter.Base.Listview.ViewHolder;
import lib.emerson.com.emersonapplib.R;

/**
 * Created by YangJianCong on 2016/10/20.
 */

public class CustomViewGroupActivity extends baseActivity {
    @ViewInject(R.id.id_custom_lv)
    private ListView listView;
    private List<String> listDatas;
    private String[] strings = {"初步认识ViewGroup"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customgroup);
        x.view().inject(this);

        initDatas();
        listView.setAdapter(new BaseLvAdapter<String>(this,R.layout.item,listDatas) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.tv_content,listDatas.get(position));
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(CustomViewGroupActivity.this,CustomVGTestActivity.class));
                        break;
                }
            }
        });

    }

    private void initDatas() {
        listDatas = new ArrayList<>();
        for(String s: strings){
            listDatas.add(s);
        }
    }


}
