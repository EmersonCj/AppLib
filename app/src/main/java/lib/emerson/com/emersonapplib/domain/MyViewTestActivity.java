package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.view.MyView;

/**
 * Created by YangJianCong on 2016/9/29.
 */
public class MyViewTestActivity extends baseActivity{


    @Override
    public void onCreate(Bundle savedInstanceState){
        Log.e("onCreate","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);
        final MyView myView = (MyView)findViewById(R.id.myview);
        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.reset();
            }
        });

    }


}
