package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.HashMap;

import lib.emerson.com.emersonapplib.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/7/5.
 */
public class okHttpActivity extends baseActivity implements View.OnClickListener {
    @ViewInject(R.id.okhttp_bt_get)
    private Button btGet;
    @ViewInject(R.id.okhttp_bt_post)
    private Button btPost;
    @ViewInject(R.id.okhttp_tv_show)
    private TextView tv;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        x.view().inject(this);
        btGet.setOnClickListener(this);
        btPost.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.okhttp_bt_get){
            HashMap<String,String> map = new HashMap<>();
            map.put("page", "1");
            map.put("epage", "10");
            map.put("is_all", "1");
            okHttpConnectionGetMethod("http://test.wxw7z.com/index.php/Api/Store/getCityList", map, new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    toast("onError");
                }

                @Override
                public void onResponse(String response, int id) {
                    Log.e("response",response);
                    toast("成功");
                }


            });

            /*try {
                OkHttpUtils
                        .get()
                        .url("http://test.wxw7z.com/index.php/Api/Store/getCityList")
                        .addParams("username", "hyman")
                        .addParams("password", "123")
                        .build()
                        .execute();         //这样是同步，会阻塞。如果是.execute(Callback)是异步。
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }else {
            HashMap<String, String> map = new HashMap<>();
            map.put("xxxx","mPhone" );
            okHttpConnectionPostMethod("http://test.wxw7z.com/index.php/Api/Store/getCityList", map, new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {

                }
            });


            OkHttpUtils
                    .post()
                    .url("http://test.wxw7z.com/index.php/Api/Store/getCityList")
                    .addParams("username", "hyman")
                    .addParams("password", "123")
                    .build()
                    .execute(new StringCallback() {

                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {

                        }
                    });
        }
    }
}
