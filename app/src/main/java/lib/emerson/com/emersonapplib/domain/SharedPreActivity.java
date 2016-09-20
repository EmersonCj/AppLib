package lib.emerson.com.emersonapplib.domain;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.utils.Product;
import lib.emerson.com.emersonapplib.utils.SharedConfig;

/**
 * Created by Administrator on 2016/7/18.
 */
public class SharedPreActivity extends baseActivity implements View.OnClickListener {
    private Button btSave,btGet;
    private EditText et;
    private SharedConfig mSharedConfig;
    private Product product;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        btSave = (Button)findViewById(R.id.action_share_bt_save);
        btGet = (Button)findViewById(R.id.action_share_bt_get);
        et = (EditText) findViewById(R.id.action_share_et);
        btSave.setOnClickListener(this);
        btGet.setOnClickListener(this);

        try {
            mSharedConfig = SharedConfig.getInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        product = new Product(100,"TEST");
    }


    @Override
    public void onClick(View v) {
        if(R.id.action_share_bt_save == v.getId()){
            String s = et.getText().toString().trim();
            //mSharedConfig.setStringValue("test",s);
            mSharedConfig.saveObject(SharedPreActivity.this,"obj",product,"sharedconfig");
        }else {
            Product testproduct = (Product) mSharedConfig.readObject(SharedPreActivity.this,"obj","sharedconfig");

            //toast(mSharedConfig.getStringValue("test","null"));
        }
    }
}
