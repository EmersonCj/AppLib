package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by YangJianCong on 2016/10/7.
 */
public class ScrollViewStickyActivity extends baseActivity {
    private Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scrollview_sticky);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(ScrollViewStickyActivity.this, "clicked !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
