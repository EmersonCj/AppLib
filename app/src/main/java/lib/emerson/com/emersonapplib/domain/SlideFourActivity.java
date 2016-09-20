package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.adapter.SlideLvAdapter;

/**
 * Created by Administrator on 2016/8/19.
 */
public class SlideFourActivity extends baseActivity{
    private List<String> datas;
    private ListView lv;
    private SlideLvAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silde_four);
        datas = new ArrayList<>();
        for(int i = 0 ;i < 21;i++){
            datas.add(i + "");
        }
        Log.e("data :",datas.toString());
        lv = (ListView) findViewById(R.id.slide_lv);
        adapter = new SlideLvAdapter(this,datas);
        adapter.setGoLeftDeleted(new SlideLvAdapter.GoLeftDeleted() {
            @Override
            public void onItemDeleted(int position) {
                adapter.deleteGood(position);
                adapter.allClose();

            }
        });
        lv.setAdapter(adapter);


    }


}
