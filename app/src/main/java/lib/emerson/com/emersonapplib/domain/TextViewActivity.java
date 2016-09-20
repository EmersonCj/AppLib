package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.view.MultipleTextViewGroup;

/**
 * Created by YangJianCong on 2016/8/31.
 */
public class TextViewActivity extends baseActivity{


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_group);


        List<String> dataList = new ArrayList<String>();

        dataList.add("Mason Liu");
        dataList.add("Mason Liu");

        dataList.add("天盟天盟天盟天盟");
        dataList.add("Mason Mason Mason");

        dataList.add("Mason Liu");
        dataList.add("天盟");
        dataList.add("天盟天盟天盟");
        dataList.add("Mason Mason");

        dataList.add("Mason");
        dataList.add("天");
        dataList.add("天");
        dataList.add("Ma");


        MultipleTextViewGroup rl = (MultipleTextViewGroup) findViewById(R.id.main_rl);
        rl.setTextViews(dataList);
        rl.setOnMultipleTVItemClickListener(new MultipleTextViewGroup.OnMultipleTVItemClickListener() {
            @Override
            public void onMultipleTVItemClick(View view, int position) {
                Toast.makeText(TextViewActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
