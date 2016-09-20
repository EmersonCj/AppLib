package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.Test.ProgressBarAsyncTask;

/**
 * Created by Administrator on 2016/7/5.
 */
public class AsyncTaskActivity extends baseActivity {
    private Button button;
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);

        button = (Button)findViewById(R.id.button03);
        progressBar = (ProgressBar)findViewById(R.id.progressBar02);
        textView = (TextView)findViewById(R.id.textView01);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ProgressBarAsyncTask asyncTask = new ProgressBarAsyncTask(textView, progressBar);
                asyncTask.execute(1000);
            }
        });

    }

}
