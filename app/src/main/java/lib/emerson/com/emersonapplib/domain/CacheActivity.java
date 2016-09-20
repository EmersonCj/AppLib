package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.utils.ACache;

/**
 * Created by Administrator on 2016/7/5.
 */
public class CacheActivity extends baseActivity {
    @ViewInject(R.id.cache_bt_save)
    private Button btSave;
    @ViewInject(R.id.cache_bt_show)
    private Button btshow;
    @ViewInject(R.id.cache_et)
    private EditText CacheEt;
    @ViewInject(R.id.cache_tv)
    private TextView CacheTv;

    private ACache mCache = null;
    private String saveStr,showStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        x.view().inject(this);

         mCache =  ACache.get(this);
//        mCache.put("test_key1", "test value");
//        mCache.put("test_key2", "test value", 10);//保存10秒，如果超过10秒去获取这个key，将为null
//        mCache.put("test_key3", "test value", 2 * ACache.TIME_DAY);//保存两天，如果超过两天去获取这个key，将为null


    }


    @Event(value = R.id.cache_bt_save,type = View.OnClickListener.class)
    private void click_1(View view){
        saveStr = CacheEt.getText().toString();
        mCache.put("Test",saveStr,10);
        CacheEt.setText("");
    }

    @Event(value = R.id.cache_bt_show,type = View.OnClickListener.class)
    private void click_2(View view){
        showStr = mCache.getAsString("Test");
        CacheTv.setText(showStr);
        if(showStr != null){
            Log.e("Show = ",showStr);
        }else {
            Log.e("Show = ","NULL");
        }
    }





}
