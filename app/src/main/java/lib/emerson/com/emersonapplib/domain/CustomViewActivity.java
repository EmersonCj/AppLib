package lib.emerson.com.emersonapplib.domain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.view.CheckSwitchButton;
import lib.emerson.com.emersonapplib.view.CustomProgressBar;
import lib.emerson.com.emersonapplib.view.CustomTitleView;
import lib.emerson.com.emersonapplib.view.CustomVolumControlBar;

/**
 * Created by YangJianCong on 2016/10/20.
 */

public class CustomViewActivity extends baseActivity {
    @ViewInject(value = R.id.layout_my_custom_tv_test)
    private Button btTest;
    @ViewInject(value = R.id.layout_my_custom_tv_one)
    private Button btOne;
    @ViewInject(value = R.id.layout_my_custom_tv_two)
    private Button btTwo;
    @ViewInject(value = R.id.layout_my_custom_tv_three)
    private Button btThree;
    @ViewInject(value = R.id.layout_my_custom_tv_four)
    private Button btFour;
    @ViewInject(value = R.id.layout_my_custom_tv_five)
    private Button btFive;
    @ViewInject(value = R.id.layout_my_custom_tv_six)
    private Button btSix;

    @ViewInject(value = R.id.layout_my_custom_view_one)
    private CustomTitleView customTitleView;
    @ViewInject(value = R.id.layout_my_custom_view_two)
    private CustomProgressBar customProgressBar;
    @ViewInject(value = R.id.layout_my_custom_view_three)
    private CustomVolumControlBar customVolumControlBar;
    @ViewInject(value = R.id.mEnableCheckSwithcButton)
    private CheckSwitchButton checkSwitchButton;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customview);
        x.view().inject(this);

        btTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomViewActivity.this,CustomViewTestActivity.class));
            }
        });

        btOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewGone();
                customTitleView.setVisibility(View.VISIBLE);
            }
        });

        btTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewGone();
                customProgressBar.setVisibility(View.VISIBLE);
            }
        });

        btThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewGone();
                customVolumControlBar.setVisibility(View.VISIBLE);
            }
        });

        btFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewGone();
                checkSwitchButton.setVisibility(View.VISIBLE);
            }
        });

        btFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomViewActivity.this,MyViewTestActivity.class));
            }
        });

        btSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomViewActivity.this, FlikerProgressBarActivity.class));
            }
        });

        initCheackButton();

    }

    private void initCheackButton() {
        checkSwitchButton.setChecked(false);
        checkSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    Log.e("改变","true");
                }else{
                    Log.e("改变","false");
                }
            }
        });
    }


    private void setViewGone(){
        customVolumControlBar.setVisibility(View.GONE);
        customTitleView.setVisibility(View.GONE);
        customProgressBar.setVisibility(View.GONE);
        checkSwitchButton.setVisibility(View.GONE);

    }

}
