package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/7/22.
 */
public class VolleyActivity extends baseActivity {
    String URL = "http://test.wxw7z.com/index.php/Api/Goods/getFirstClassList";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        mQueue.add(new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("TAG", "response : " + jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }));
        mQueue.start();

    }


}
