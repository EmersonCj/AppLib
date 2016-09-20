package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.widget.FrameLayout;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.view.TestView;

/**
 * Created by YangJianCong on 2016/9/14.
 */
public class CustomViewTestActivity extends baseActivity{



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);

        FrameLayout root=(FrameLayout)findViewById(R.id.root);
        root.addView(new TestView(CustomViewTestActivity.this));
    }

}
