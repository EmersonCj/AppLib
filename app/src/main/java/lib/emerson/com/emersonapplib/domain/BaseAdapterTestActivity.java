package lib.emerson.com.emersonapplib.domain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.Adapter.Base.Listview.BaseLvAdapter;
import lib.emerson.com.emersonapplib.Adapter.Base.Listview.ViewHolder;
import lib.emerson.com.emersonapplib.R;

/**
 * Created by YangJianCong on 2016/10/19.
 */
public class BaseAdapterTestActivity extends ListviewActivity {
    private ListView listView;
    private List<String> data = new ArrayList<String>();
    String datas[] = {"Listview加载不同布局","Recyclerview正常使用"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_adapter_test);
        listView = (ListView) findViewById(R.id.id_base_adapter_lv);

        initData();

        listView.setAdapter(new BaseLvAdapter(this, R.layout.item, data) {

            @Override
            protected void convert(ViewHolder viewHolder, Object item, int position) {
                viewHolder.setText(R.id.tv_content, data.get(position));
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(BaseAdapterTestActivity.this, LvAdapterTestActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(BaseAdapterTestActivity.this, RvAdapterTestActivity.class));
                        break;

                }
            }
        });


    }

    private void initData() {
        for (int i = 0; i < datas.length; i++) {
            data.add(datas[i]);
        }

    }


}
