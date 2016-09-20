package lib.emerson.com.emersonapplib.domain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import lib.emerson.com.emersonapplib.R;


/**
 * Created by Administrator on 2016/6/2.
 */
public class WidgetActivityThree extends baseActivity implements View.OnClickListener {
    private Button btOne;
    private Button btTwo;
    private Button btThree;
    private TextView tv;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        btOne = (Button) findViewById(R.id.activity_recyclerview_bt_1);
        btTwo = (Button) findViewById(R.id.activity_recyclerview_bt_2);
        btThree = (Button) findViewById(R.id.activity_recyclerview_bt_3);
        tv = (TextView) findViewById(R.id.activity_recyclerview_tv);
        btOne.setOnClickListener(this);
        btTwo.setOnClickListener(this);
        btThree.setOnClickListener(this);
        tv.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if(v.getId() == R.id.activity_recyclerview_bt_1){
            intent.setClass(WidgetActivityThree.this,RecyclerViewOne.class);
            jump(WidgetActivityThree.this,intent,false);
        }else if(v.getId() == R.id.activity_recyclerview_bt_2){
            intent.setClass(WidgetActivityThree.this,RecyclerViewTwo.class);
            jump(WidgetActivityThree.this,intent,false);
        }else if(v.getId() == R.id.activity_recyclerview_bt_3){
            intent.setClass(WidgetActivityThree.this,RecyclerViewThree.class);
            jump(WidgetActivityThree.this,intent,false);
        }else {
            intent.setClass(WidgetActivityThree.this,RecyclerViewFour.class);
            jump(WidgetActivityThree.this,intent,false);
        }
    }
}
