package lib.emerson.com.emersonapplib.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/8/15.
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
    private IWXAPI api;
    private static final String APP_ID = "your app id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    /**
     * 得到支付结果回调
     *  0 支付成功
     *  -1 发生错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
     *  -2 用户取消 发生场景：用户不支付了，点击取消，返回APP。
     */
    @Override
    public void onResp(BaseResp resp) {
        Log.d("WXPAY", "errCode = " + resp.errCode);// 支付结果码
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode){
                case 0:
                    break;
                case -1:
                case -2:
                    finish();
                    break;
            }
        }
    }
}
