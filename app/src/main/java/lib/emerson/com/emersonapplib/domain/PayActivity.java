package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.utils.Constant;

/**
 * Created by Administrator on 2016/8/15.
 */
public class PayActivity extends Activity {
    private String WEI_XIN_APP_ID = "wxf8b4f85f3a794e77";
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);
        api = WXAPIFactory.createWXAPI(PayActivity.this, WEI_XIN_APP_ID);

        final Button appayBtn = (Button) findViewById(R.id.appay_btn);
        appayBtn.setOnClickListener(new View.OnClickListener() {

            /* 用户选择好商品后，点击付款*/
            @Override
            public void onClick(View v) {
                /**
                 * 获取预支付订单,这一步由服务器完成！！！
                 * 注意：如果服务端开发文档跟客户端demo里的参数不一样，以demo里的参数为准，
                 * 否则服务器传过来的参数无法调起微信支付！！！
                 * */

                 String result = "";        //模拟由后台获取到的数据
                try {
                    JSONObject json = new JSONObject(result);
                    if(null != json && !json.has("retcode") ){
                        PayReq req = new PayReq();
                        //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                        try {
                            req.appId			= json.getString("appid");
                            req.partnerId		= json.getString("partnerid");
                            req.prepayId		= json.getString("prepayid");
                            req.nonceStr		= json.getString("noncestr");
                            req.timeStamp		= json.getString("timestamp");
                            req.packageValue	= json.getString("package");
                            req.sign			= json.getString("sign");
//                            req.extData			= "app data"; // optional
                            Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                            api.registerApp(WEI_XIN_APP_ID);
                            api.sendReq(req);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        Button checkPayBtn = (Button) findViewById(R.id.check_pay_btn);
        checkPayBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                Toast.makeText(PayActivity.this, String.valueOf(isPaySupported), Toast.LENGTH_SHORT).show();
            }
        });
    }


}