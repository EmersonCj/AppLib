package lib.emerson.com.emersonapplib.domain;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.wheel.widget.OnWheelChangedListener;
import lib.emerson.com.emersonapplib.wheel.widget.WheelView;
import lib.emerson.com.emersonapplib.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * Created by Administrator on 2016/7/6.
 */
public class WheelViewActivity extends baseActivity implements OnWheelChangedListener, View.OnClickListener {
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private Button mBtnConfirm;
    private Button bt;


    private View contentView;
    private PopupWindow mPopWindow;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheelview);
        bt =(Button)  findViewById(R.id.wheel_view_bt);
        bt.setOnClickListener(l);
    }


    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupWindow();
            initWheelView();
        }
    };

    private void initWheelView(){
        setUpViews();		//1,创建 WheelView 组件
        setUpListener();	//2,设置条目改变监听器
        setUpData();		//3,设置适配器
    }

    private void showPopupWindow() {
        contentView = LayoutInflater.from(WheelViewActivity.this).inflate(R.layout.activity_address_popup, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.FILL_PARENT);

        //是否响应touch事件
//        mPopWindow.setTouchable(false);
        //是否具有获取焦点的能力
//      mPopWindow.setFocusable(true);

        //外部是否可以点击
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);
        //设置动画
        mPopWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        mPopWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
        mPopWindow.showAsDropDown(bt);

    }

    private void setUpViews() {
        mViewProvince = (WheelView) contentView.findViewById(R.id.id_province);
        mViewCity = (WheelView) contentView.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) contentView.findViewById(R.id.id_district);
        mBtnConfirm = (Button) contentView.findViewById(R.id.btn_confirm);
    }

    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        // 添加onclick事件
        mBtnConfirm.setOnClickListener(this);
    }

    private void setUpData() {
        //初始化数据源，并设置adapter

        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(WheelViewActivity.this, mProvinceDatas));

        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);

        //设置 默认的省市区
        updateCities();
        updateAreas();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
        if (wheel == mViewProvince) {
            updateCities();
            mCurrentDistrictName= mDistrictDatasMap.get(mCurrentCityName)[0];
        } else if (wheel == mViewCity) {
            updateAreas();
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);
        if (areas == null) {
            areas = new String[] { "" };
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[] { "" };
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                showSelectedResult();
                break;
            default:
                break;
        }
    }

    private void showSelectedResult() {
        Toast.makeText(WheelViewActivity.this, "当前选中:"+mCurrentProviceName+","+mCurrentCityName+","
                +mCurrentDistrictName+","+mCurrentZipCode, Toast.LENGTH_SHORT).show();
    }

}
