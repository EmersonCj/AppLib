package lib.emerson.com.emersonapplib.domain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.view.CheckSwitchButton;
import lib.emerson.com.emersonapplib.view.CustomProgressBar;
import lib.emerson.com.emersonapplib.view.CustomTitleView;
import lib.emerson.com.emersonapplib.view.CustomVolumControlBar;
import lib.emerson.com.emersonapplib.view.FlikerProgressBar;
import lib.emerson.com.emersonapplib.view.MyView;

/**
 * Created by YangJianCong on 2016/9/7.
 */
public class MyCustomViewActivity extends baseActivity{
    @ViewInject(R.id.id_my_custom_bt_one)
    private Button btOne;
    @ViewInject(R.id.id_my_custom_bt_two)
    private Button btTwo;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_custom);
        x.view().inject(this);
        btOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCustomViewActivity.this,CustomViewActivity.class));
            }
        });

        btTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCustomViewActivity.this,CustomViewGroupActivity.class));
            }
        });

    }




}
