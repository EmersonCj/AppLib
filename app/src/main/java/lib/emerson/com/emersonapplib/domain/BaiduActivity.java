package lib.emerson.com.emersonapplib.domain;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;

import java.util.List;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/8/16.
 */
public class BaiduActivity  extends baseActivity{
    private Button bt1;
    private Button bt2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_baidu_map);
        bt1 = (Button) findViewById(R.id.baidu_map_base);
        bt2 = (Button) findViewById(R.id.baidu_map_location);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaiduActivity.this,BaiduBaseMapActivity.class));
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaiduActivity.this,BaiduLocationActivity.class));
            }
        });

    }

}



