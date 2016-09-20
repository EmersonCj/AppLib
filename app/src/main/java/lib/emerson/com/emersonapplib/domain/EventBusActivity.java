package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import de.greenrobot.event.EventBus;
import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.utils.common.FirstEvent;

/**
 * Created by Administrator on 2016/6/20.
 * http://blog.csdn.net/lmj623565791/article/details/40794879
 */
public class EventBusActivity extends Activity {
    private Button btn_FirstEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_eventbus);
        btn_FirstEvent = (Button) findViewById(R.id.btn_first_event);
        btn_FirstEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //发送消息
                EventBus.getDefault().post(
                        new FirstEvent("FirstEvent btn clicked"));
            }
        });
    }
}
