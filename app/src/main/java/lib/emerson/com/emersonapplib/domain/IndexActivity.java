package lib.emerson.com.emersonapplib.domain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/8/9.
 */
public class IndexActivity extends baseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_main);
        findViewById(R.id.bt_index_lv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(IndexActivity.this,new Intent(IndexActivity.this, IndexLvActivity.class),false);
            }
        });
        findViewById(R.id.bt_index_rv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(IndexActivity.this,new Intent(IndexActivity.this, IndexRvActivity.class),false);
            }
        });
    }




}
