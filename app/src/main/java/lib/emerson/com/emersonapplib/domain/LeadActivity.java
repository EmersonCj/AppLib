package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;


import org.xutils.x;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.entity.AppIntro;
import lib.emerson.com.emersonapplib.entity.FirstSlide;
import lib.emerson.com.emersonapplib.entity.FourthSlide;
import lib.emerson.com.emersonapplib.entity.SecondSlide;
import lib.emerson.com.emersonapplib.entity.ThirdSlide;

/**
 * Created by Administrator on 2016/8/10.
 * http://www.see-source.com/androidwidget/detail.html?wid=104
 */
public class LeadActivity extends AppIntro{


    @Override
    public void init(Bundle savedInstanceState) {
        //添加4个引导页页面
        addSlide(new FirstSlide(), getApplicationContext());
        addSlide(new SecondSlide(), getApplicationContext());
        addSlide(new ThirdSlide(), getApplicationContext());
        addSlide(new FourthSlide(), getApplicationContext());

        setVibrate(true);
        setVibrateIntensity(30);

    }

    @Override
    public void onDonePressed() {
        //引导页的最后一页，这里实现跳转到首页Activity
        Log.e("onDonePressed","onDonePressed");
        startActivity(new Intent(LeadActivity.this,MainActivity.class));
    }
}
