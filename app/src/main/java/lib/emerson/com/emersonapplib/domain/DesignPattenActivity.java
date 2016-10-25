package lib.emerson.com.emersonapplib.domain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by YangJianCong on 2016/9/27.
 */
public class DesignPattenActivity extends baseActivity {
    private ListView lv = null;
    private String[] listdata = new String[]{"观察者模式"};
    private ArrayAdapter<String> adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_patten);
        lv = (ListView) findViewById(R.id.design_patten_lv);
        adapter = new ArrayAdapter<String>(DesignPattenActivity.this, R.layout.item_show, R.id.tv_item, listdata);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(l);
    }


    AdapterView.OnItemClickListener l = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            switch (position) {
                case 0: {
                    intent.setClass(DesignPattenActivity.this, DesignPattenOneActivity.class);
                    jump(DesignPattenActivity.this, intent, false);
                }
                break;


            }
        }
    };


}
