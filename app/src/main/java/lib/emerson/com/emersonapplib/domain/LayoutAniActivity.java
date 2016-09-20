package lib.emerson.com.emersonapplib.domain;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/7/11.
 */
public class LayoutAniActivity extends baseActivity implements CompoundButton.OnCheckedChangeListener {

    private ViewGroup viewGroup;
    private GridLayout mGridLayout;
    private int mVal;
    private LayoutTransition mTransition;
    private CheckBox mAppear, mChangeAppear, mDisAppear, mChangeDisAppear;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_animation);
        viewGroup = (ViewGroup) findViewById(R.id.id_container);
        mAppear = (CheckBox) findViewById(R.id.id_appear);
        mChangeAppear = (CheckBox) findViewById(R.id.id_change_appear);
        mDisAppear = (CheckBox) findViewById(R.id.id_disappear);
        mChangeDisAppear = (CheckBox) findViewById(R.id.id_change_disappear);

        mAppear.setOnCheckedChangeListener(this);
        mChangeAppear.setOnCheckedChangeListener(this);
        mDisAppear.setOnCheckedChangeListener(this);
        mChangeDisAppear.setOnCheckedChangeListener(this);

        // 创建一个GridLayout
        mGridLayout = new GridLayout(this);
        // 设置每列5个按钮
        mGridLayout.setColumnCount(5);
        // 添加到布局中
        viewGroup.addView(mGridLayout);
        //默认动画全部开启
        mTransition = new LayoutTransition();
        mGridLayout.setLayoutTransition(mTransition);

    }


    /*
        过渡的类型一共有四种：
        LayoutTransition.APPEARING              当一个View在ViewGroup中出现时，对此View设置的动画
        LayoutTransition.CHANGE_APPEARING       当一个View在ViewGroup中出现时，对此View对其他View位置造成影响，对其他View设置的动画
        LayoutTransition.DISAPPEARING           当一个View在ViewGroup中消失时，对此View设置的动画
        LayoutTransition.CHANGE_DISAPPEARING    当一个View在ViewGroup中消失时，对此View对其他View位置造成影响，对其他View设置的动画
    */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mTransition = new LayoutTransition();
        mTransition.setAnimator( LayoutTransition.APPEARING,
                (mAppear.isChecked() ? mTransition.getAnimator(LayoutTransition.APPEARING) : null));
        mTransition.setAnimator(LayoutTransition.CHANGE_APPEARING,
                (mChangeAppear.isChecked() ? mTransition.getAnimator(LayoutTransition.CHANGE_APPEARING): null));
        mTransition.setAnimator( LayoutTransition.DISAPPEARING,
                (mDisAppear.isChecked() ? mTransition.getAnimator(LayoutTransition.DISAPPEARING) : null));
        mTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING,
                (mChangeDisAppear.isChecked() ? mTransition.getAnimator(LayoutTransition.CHANGE_DISAPPEARING): null));

        mGridLayout.setLayoutTransition(mTransition);
    }


    /**
     * 添加按钮
     *
     * @param view
     */
    public void addBtn(View view){
        final Button button = new Button(this);
        button.setText((++mVal) + "");
        mGridLayout.addView(button, Math.min(1, mGridLayout.getChildCount()));
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mGridLayout.removeView(button);
            }
        });
    }

}
