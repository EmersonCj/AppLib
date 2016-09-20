package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;


import lib.emerson.com.emersonapplib.R;

import static lib.emerson.com.emersonapplib.utils.CommonUtils.checkSdCard;
import static lib.emerson.com.emersonapplib.utils.CommonUtils.isNetworkAvailable;
import static lib.emerson.com.emersonapplib.utils.CommonUtils.isWifi;

import static lib.emerson.com.emersonapplib.utils.common.KeyBoardUtils.openKeybord;
import static lib.emerson.com.emersonapplib.utils.common.PinYin.getPinYin;
import static lib.emerson.com.emersonapplib.utils.common.PinYin.getPinYinHeadChar;

/**
 * Created by Administrator on 2016/7/5.
 */
public class UtilsActivity extends baseActivity {
    private EditText tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        tv = (EditText) findViewById(R.id.conversion_tv);

        Log.e("TAG",getPinYin("中国人china"));
        Log.e("TAG",getPinYinHeadChar("中国人"));

        openKeybord(tv,UtilsActivity.this);

        Log.e("TAG", String.valueOf(isNetworkAvailable(UtilsActivity.this)));
        Log.e("TAG", String.valueOf(isWifi(UtilsActivity.this)));
        Log.e("TAG", String.valueOf(checkSdCard()));

    }

}
