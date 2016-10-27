package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by YangJianCong on 2016/10/25.
 */
public class CustomVGTestActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customgroup_test);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("Activity", "dispatchTouchEvent-- action=" + event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("Activity", "onTouchEvent-- action="+event.getAction());
        return super.onTouchEvent(event);
    }


}
