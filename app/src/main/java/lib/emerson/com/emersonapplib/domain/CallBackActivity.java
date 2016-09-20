package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/6/20.
 */
public  class CallBackActivity extends Activity {
    private TextView textView;
    private Button bt;
    private classA classa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_callback);
        textView = (TextView) findViewById(R.id.callback_tv);
        bt = (Button) findViewById(R.id.callback_bt);

        classa = new classA();            //实例化A类
        classa.setOnClick(new classA.MyOnClickListener() {      //实例化接口，并实现接口的方法。然后传到A类中里面了。
            @Override
            public void CallckClick() {
                textView.setText("回调成功");
            }
        });


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classa.doClick();       //在B类中，用户调用A类中的doClick，而doClick会回来调用B类中的onClick，onClick被叫做回调函数
            }
        });
    }



}

