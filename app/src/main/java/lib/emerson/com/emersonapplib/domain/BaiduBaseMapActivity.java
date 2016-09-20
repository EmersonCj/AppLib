package lib.emerson.com.emersonapplib.domain;

import android.os.Bundle;


import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by Administrator on 2016/8/16.
 */
public class BaiduBaseMapActivity extends baseActivity{
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*在使用百度SDK各组件之前，要初始化context信息，传入ApplicationContext
         *而且该方法要再setContentView方法之前实现
         *所以建议该方法放在Application的初始化方法中*/
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidu_base_map);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);

        /*选择地图类型*/
        chooseMapType();

        //关于百度基础地图的更多使用请看：http://lbsyun.baidu.com/index.php?title=androidsdk/guide/basicmap#OpenGL.E7.BB.98.E5.88.B6.E5.8A.9F.E8.83.BD*/




    }


    private void chooseMapType() {
       mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //卫星地图
        //mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        //空白地图, 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
        //mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);

        //开启实时交通图
        mBaiduMap.setTrafficEnabled(true);

        //开启城市热力图
        mBaiduMap.setBaiduHeatMapEnabled(true);
    }


    /*在activity的不同时期执行对应的方法，实现地图生命周期管理*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }



}
