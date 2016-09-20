package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;

import android.os.Handler;
import android.os.Vibrator;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

import lib.emerson.com.emersonapplib.App.MyApplication;
import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.service.LocationService;

/**
 * Created by Administrator on 2016/8/16.
 */
public class BaiduLocationActivity extends baseActivity {
    private Button startLocation;
    private TextView LocationResult;
    private MyApplication myApplication;
    private NotifyLister mNotifyer;
    private Vibrator vibrator ;

    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private BitmapDescriptor mCurrentMarker;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_location_map);
        myApplication = (MyApplication)getApplication();
        LocationResult = (TextView) findViewById(R.id.textView1);
        startLocation = (Button) findViewById(R.id.addfence);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView_2);
        mBaiduMap = mMapView.getMap();

        //位置提醒相关代码
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        mNotifyer = new NotifyLister();
        mNotifyer.SetNotifyLocation(23.130323,113.34679,3000,"bd09ll");//4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
        myApplication.mLocationClient.registerNotify(mNotifyer);

        //开始定位
        startLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始定位
                myApplication.mLocationClient.start();
                //因为定位需要时间，这里延时1s再去获取数据（经纬度）
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        if(myApplication.lastPoint != null){
                            LocationResult.setText(myApplication.lastPoint.toString());
                            showInBaiduMap(myApplication.mlocation);
                        }
                    }
                }, 1000);

            }
        });
    }

    private void showInBaiduMap(BDLocation location) {
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(0).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();

        // 为Map设置定位数据
        mBaiduMap.setMyLocationData(locData);

        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
         mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfigeration(config);

        // 当不需要定位图层时关闭定位图层
        //mBaiduMap.setMyLocationEnabled(false);
    }


    //注册位置提醒监听事件后，可以通过SetNotifyLocation 来修改位置提醒设置，修改后立刻生效。
    //BDNotifyListner实现
    public class NotifyLister extends BDNotifyListener {
        public void onNotify(BDLocation mlocation, float distance){
            toast("到了");
            //vibrator.vibrate(1000);//振动提醒已到设定位置附近
        }
    }





}







