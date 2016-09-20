package lib.emerson.com.emersonapplib.domain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import lib.emerson.com.emersonapplib.PayClass.PayDemoActivity;
import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.entity.OrderResultClass;

/**
 * Created by Administrator on 2016/8/12.
 */
public class PayTestActivity extends baseActivity {
    @ViewInject(R.id.pay_demo_zhifubao)
    private Button zhifubao;
    @ViewInject(R.id.pay_demo_wxpay)
    private Button wxpay;

    private OrderResultClass orderresultclass ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_demo);
        x.view().inject(this);

        /*构造数据，在现实中应该由后台获取*/
        //initData();

        zhifubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayTestActivity.this, PayDemoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",orderresultclass);
                intent.putExtra("COMMIT_RESULT_TAG",bundle);
                startActivity(intent);
            }
        });

        wxpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PayTestActivity.this, PayActivity.class));
                finish();
            }
        });
    }

    private void initData() {
        orderresultclass.setNotify_url("http://test.wxw7z.com/index.php/Api/PayCallBack/alipayCallback");
        orderresultclass.set_input_charset("utf-8");
        orderresultclass.setAppid("2016040501266434");
        orderresultclass.setBody("七子优生活商品:0.02");
        orderresultclass.setOut_trade_no("850524584093227008");
        orderresultclass.setPartner("2088711057779790");
        orderresultclass.setPayment_type("1");
        orderresultclass.setSeller("2723942596@qq.com");
        orderresultclass.setService("mobile.securitypay.pay");
        orderresultclass.setSign("oi0R/xJgL8CW26NofQR6vrh/4Dp9AWWyq7WiaF5cM8f03tQvnPm/FfI51SqXPKTBRXdC+fitcEHAP/SnhwcyV5f8O7DDSGFYlwbE9h/OEQLSnRD2Dp98F7VQ7CVKxZCobMWHesdmM/eX7/K8QlYRU16VheWEqbPv5v3+WK+dZzk=");
        orderresultclass.setSubject("七子优生活商品");
        orderresultclass.setTimeout_express("1d");
        orderresultclass.setTimestamp("2016-08-15 13:48:13");
        orderresultclass.setTotal_fee("0.01");

    }


}
