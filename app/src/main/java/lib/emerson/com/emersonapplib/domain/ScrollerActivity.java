package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by YangJianCong on 2016/9/27.
 */
/*
    * 这两个滑动都是用于滑动View中的内容，而不是改变View的位置。
    * 第一个参数x表示相对于当前位置横向移动的距离，正值向左移动，负值向右移动，单位是像素。
    * 第二个参数y表示相对于当前位置纵向移动的距离，正值向上移动，负值向下移动，单位是像素。
    *
    * layout.scrollTo(-60, -100);     //View内容相对于初始的位置滚动某段距离（只能滚动一次，因为初始位置是固定的）
    * layout.scrollBy(-60, -100);     //View内容相对于当前的位置滚动某段距离(可连续滚动)
    * */

public class ScrollerActivity extends baseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);



    }



}
