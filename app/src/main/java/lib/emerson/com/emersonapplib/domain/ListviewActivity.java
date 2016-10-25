package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.Adapter.ListTestAdapter;

/**
 * Created by YangJianCong on 2016/8/31.
 */
public class ListviewActivity extends baseActivity implements View.OnClickListener {
    private Button btOne;
    private ListView lv;
    private ListTestAdapter adapter;
    private List<String> data;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        btOne = (Button) findViewById(R.id.activity_list_bt_1);
        lv = (ListView) findViewById(R.id.activity_listview);
        btOne.setOnClickListener(this);
        data = new ArrayList<>();
        for(int i = 0 ; i < 15; i++){
            data.add(getRandomString("本文为博主原创文章.未经博主允许不得转载",8));
        }
        adapter = new ListTestAdapter(this,data,null);
    }

    @Override
    public void onClick(View v) {
        lv.setAdapter(adapter);
        lv.setVisibility(View.VISIBLE);
    }
}
