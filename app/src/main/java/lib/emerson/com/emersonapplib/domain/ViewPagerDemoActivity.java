package lib.emerson.com.emersonapplib.domain;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/7/27.
 */
public class ViewPagerDemoActivity extends baseActivity {
    private Button btOne;
    private Button btTwo;
    private Button btThree;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_demo);
        btOne = (Button) findViewById(R.id.viewPager_demo_bt_1);
        btTwo = (Button) findViewById(R.id.viewPager_demo_bt_2);
        btThree = (Button) findViewById(R.id.viewPager_demo_bt_3);

        final Intent intent = new Intent();
        btOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(ViewPagerDemoActivity.this,FragmentTestActivity.class);
                jump(ViewPagerDemoActivity.this,intent,false);
            }
        });

        btTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(ViewPagerDemoActivity.this,ViewPagerActivity.class);
                jump(ViewPagerDemoActivity.this,intent,false);
            }
        });

        btThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(ViewPagerDemoActivity.this,ViewPager2Activity.class);
                jump(ViewPagerDemoActivity.this,intent,false);
            }
        });

    }

}
