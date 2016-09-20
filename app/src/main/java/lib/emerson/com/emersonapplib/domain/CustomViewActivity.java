package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.view.RoundProgressBar;

/**
 * Created by Administrator on 2016/7/5.
 */
public class CustomViewActivity extends baseActivity{
    private RoundProgressBar mRoundProgressBar1;
    private int progress = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customview);
//
//        mRoundProgressBar1 = (RoundProgressBar) findViewById(R.id.roundProgressBar1);
//
//        ((Button)findViewById(R.id.button1)).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        while(progress <= 100){
//                            progress += 1;
//
//                            Log.e("TAG",progress+"");
//                            mRoundProgressBar1.setProgress(progress);
//                            try {
//                                Thread.sleep(100);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }).start();
//            }
//        });
    }


}
