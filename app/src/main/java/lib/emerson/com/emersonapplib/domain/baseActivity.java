package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import lib.emerson.com.emersonapplib.App.MyApplication;
import lib.emerson.com.emersonapplib.wheel.widget.CityModel;
import lib.emerson.com.emersonapplib.wheel.widget.DistrictModel;
import lib.emerson.com.emersonapplib.wheel.widget.ProvinceModel;
import lib.emerson.com.emersonapplib.wheel.widget.XmlParserHandler;

/**
 * Created by Administrator on 2016/7/5.
 */
public class baseActivity extends FragmentActivity {
    public String TAG = "APP_TAG";
    public Context context;
    public MyApplication myapp;

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName ="";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode ="";

    /**
     * 解析省市区的XML数据
     */


    /**
     * 保存所有进入的代码
     */
    public static List<Activity> activityList = new ArrayList<Activity>();

    public void onCreate(Bundle savedInstanceState) {
        activityList.add(this);
        myapp= (MyApplication) getApplication();
        context=this;
        initStatusLan();
        super.onCreate(savedInstanceState);
    }

    /*
    * 将手机手机状态栏透明化
    * */
    private void initStatusLan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {    //5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {   //4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    /**
     * OkHttpUtils封装方式
     * @param url
     */
    public void okHttpConnectionGetMethod(String url, HashMap<String,String> map, StringCallback callback){

        GetBuilder builder = OkHttpUtils.get().url(url);
        Iterator it=map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,String> entry= (Map.Entry<String, String>) it.next();
            builder.addParams(entry.getKey(),entry.getValue());
        }
        builder.build().execute(callback);
    }


    /**
     * OkHttpUtils Post封装方式
     * @param url
     */
    public void okHttpConnectionPostMethod(String url, HashMap<String,String> map, StringCallback callback){
        PostFormBuilder builder=OkHttpUtils
                .post()
                .url(url);
        Iterator it=map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,String> entry= (Map.Entry<String, String>) it.next();
            builder.addParams(entry.getKey(),entry.getValue());
        }
        builder.build().execute(callback);
    }



    protected void initProvinceDatas(){
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList!= null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList!= null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i=0; i< provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j=0; j< cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k=0; k<districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 跳转页面  当isFinish的时候跳转关闭Activity
     */
    public void jump(Activity context, Intent in, boolean isFinish) {
        startActivity(in);
        if(isFinish){
            context.finish();
        }
    }




    private static Toast toast;
    /*
    * 避免多次弹出Toast   (Toast居中显示)
    * */
    public void toast(String content) {
        if (toast == null) {
            toast = Toast.makeText(this,content,Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast.show();
    }


    /*
    * 随机产生字符串
    * */
    public static String getRandomString(String baseString,int length) { //length表示生成字符串的长度
        String base = baseString;
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
}
