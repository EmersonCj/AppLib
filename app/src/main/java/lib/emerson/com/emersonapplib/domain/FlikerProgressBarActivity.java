package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.view.FlikerProgressBar;

/**
 * Created by YangJianCong on 2016/10/12.
 */
public class FlikerProgressBarActivity extends baseActivity{

    FlikerProgressBar flikerProgressBar;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            flikerProgressBar.setProgress(msg.arg1);
            if(msg.arg1 == 100){
                flikerProgressBar.finishLoad();
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flike_progress_bar);

        flikerProgressBar = (FlikerProgressBar) findViewById(R.id.flikerbar);

        flikerProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flikerProgressBar.isFinish()){
                    flikerProgressBar.toggle();
                }
            }
        });

        /*
        * 开启下载
        * */
        downLoad();
    }

    private void downLoad() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        float progress = flikerProgressBar.getProgress();
                        progress++;
                        Thread.sleep(200);
                        Message message = handler.obtainMessage();
                        message.arg1 = (int) progress;
                        handler.sendMessage(message);
                        if(progress == 100){
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
