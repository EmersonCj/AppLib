package lib.emerson.com.emersonapplib.domain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.view.CustomProgressBar;
import lib.emerson.com.emersonapplib.view.CustomTitleView;
import lib.emerson.com.emersonapplib.view.CustomVolumControlBar;

/**
 * Created by YangJianCong on 2016/9/7.
 */
public class MyCustomViewActivity extends baseActivity{
    @ViewInject(value = R.id.layout_my_custom_tv_test)
    private Button btTest;
    @ViewInject(value = R.id.layout_my_custom_tv_one)
    private Button btOne;
    @ViewInject(value = R.id.layout_my_custom_tv_two)
    private Button btTwo;
    @ViewInject(value = R.id.layout_my_custom_tv_three)
    private Button btThree;


    @ViewInject(value = R.id.layout_my_custom_view_one)
    private CustomTitleView customTitleView;
    @ViewInject(value = R.id.layout_my_custom_view_two)
    private CustomProgressBar customProgressBar;
    @ViewInject(value = R.id.layout_my_custom_view_three)
    private CustomVolumControlBar customVolumControlBar;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customview);
        x.view().inject(this);

        btTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCustomViewActivity.this,CustomViewTestActivity.class));
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

    }


    private void setViewGone(){
        customVolumControlBar.setVisibility(View.GONE);
        customTitleView.setVisibility(View.GONE);
        customProgressBar.setVisibility(View.GONE);
    }

}
