package lib.emerson.com.emersonapplib.domain;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import de.greenrobot.event.EventBus;
import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.adapter.listviewAdapter;
import lib.emerson.com.emersonapplib.mvp.view.LoginActivity;
import lib.emerson.com.emersonapplib.utils.ShowGrounpUtil;
import lib.emerson.com.emersonapplib.utils.common.FirstEvent;
import lib.emerson.com.emersonapplib.view.CustomTitleView;

public class MainActivity extends baseActivity{

    /*
    * Git测试
    * */

    private ListView lv = null;
    private TextView tv = null;
    private List<String> listdata = null;
    String data[] = {"Dialog动画效果","HorizontalScrollView(interface接口写法)","Scrollview","RecyclerView","Photowallfalls(图片墙)",
                     "ViewPager & Fragment","LruCache算法（包含图片的压缩和缓存）","回调函数使用","EventBus","数据库操作",
                     "触摸传递","Http下载","JSON&GSON","自定义View","AsyncTask",
                     "缓存","各种辅助类","下拉刷新,上拉加载","三级联动",
                     "android三种动画","xUtils3数据库","SharedPreferences 进程间共享","轮播图","xUtils3框架使用",
                     "android加载图片(多种框架)","ContentProvider使用","文件的使用& GIF图","volley框架（网络通信库）",
                      "用户头像制作","各种加载效果","拍照并保存","二维码","Popupwindow",
                      "自定义索引器","IOS风格的底部对话框","引导页","侧滑动作","支付开发","百度地图服务","Bitmap图片压缩","自定义TextView(自动换行)",
                      "Listview应用","自定义控件（高级）","webview","Toolbar", "Drawerlayout","GestureDetector手势检测","MVP模式开发",
                      "仿X宝商品详情","TabLayout",">>HTML5","android设计模式"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
        listviewAdapter lvAdapter = new listviewAdapter(this,listdata);
        lv = (ListView) findViewById(R.id.list_view);
        lv.setAdapter(lvAdapter);
        lv.setOnItemClickListener(l);
        ShowGrounpUtil.autoSetListviewHeight(lv);
        EventBus.getDefault().register(this);
    }

    private void getData() {
        listdata = new ArrayList<String>();
        for(int i = 0;i < data.length;i++){
            listdata.add(data[i]);
        }
    }

    AdapterView.OnItemClickListener l = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            switch (position){
                case 0:{
                    intent.setClass(MainActivity.this,DialogActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 1:{
                    intent.setClass(MainActivity.this,WidgetActivityOne.class);
                    //intent.setClass(MainActivity.this,HorizontalActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 2:{
                    intent.setClass(MainActivity.this,WidgetActivityTwo.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 3:{
                    intent.setClass(MainActivity.this,WidgetActivityThree.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 4:{
                    intent.setClass(MainActivity.this,PhotoWallActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 5:{
                    intent.setClass(MainActivity.this,ViewPagerDemoActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 6:{
                    intent.setClass(MainActivity.this,LruCacheActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 7:{
                    intent.setClass(MainActivity.this,CallBackActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 8:{
                    intent.setClass(MainActivity.this,EventBusActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 9:{
                    intent.setClass(MainActivity.this,DatasActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 10:{
                    intent.setClass(MainActivity.this,ViewActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 11:{
                    intent.setClass(MainActivity.this,okHttpActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 12:{
                    intent.setClass(MainActivity.this,JSONActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 13:{
                    intent.setClass(MainActivity.this,CustomViewActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 14:{
                    intent.setClass(MainActivity.this,AsyncTaskActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 15:{
                    intent.setClass(MainActivity.this,CacheActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 16:{
                    intent.setClass(MainActivity.this,UtilsActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 17:{
                    intent.setClass(MainActivity.this,RefreshActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 18:{
                    intent.setClass(MainActivity.this,WheelViewActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 19:{
                    intent.setClass(MainActivity.this,AnimationActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 20:{
                    intent.setClass(MainActivity.this,XUtils3DbActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 21:{
                    intent.setClass(MainActivity.this,SharedPreActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 22:{
                    intent.setClass(MainActivity.this, CycleVpActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 23:{
                    intent.setClass(MainActivity.this, xUtilsActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 24:{
                    intent.setClass(MainActivity.this, LoadImageActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 25:{
                    intent.setClass(MainActivity.this, ContentProviderActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 26:{
                    intent.setClass(MainActivity.this, FileandGIFActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 27:{
                    intent.setClass(MainActivity.this, VolleyActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 28:{
                    intent.setClass(MainActivity.this, CircleimageviewActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 29:{
                    intent.setClass(MainActivity.this, LoadAnimationActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 30:{
                    intent.setClass(MainActivity.this, CameraActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 31:{
                    intent.setClass(MainActivity.this, QRcodeActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 32:{
                    intent.setClass(MainActivity.this, PopUpWindowActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 33:{
                    intent.setClass(MainActivity.this, IndexActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 34:{
                    intent.setClass(MainActivity.this, IOSDialogActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 35:{
                    intent.setClass(MainActivity.this, LeadActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 36:{
                    intent.setClass(MainActivity.this, SlideActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 37:{
                    intent.setClass(MainActivity.this, PayTestActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 38:{
                    intent.setClass(MainActivity.this, BaiduActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 39:{
                    intent.setClass(MainActivity.this, BitmapUtilsActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 40:{
                    intent.setClass(MainActivity.this, TextViewActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 41:{
                    intent.setClass(MainActivity.this, ListviewActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 42:{
                    intent.setClass(MainActivity.this, MyCustomViewActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 43:{
                    intent.setClass(MainActivity.this, WebViewActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 44:{
                    intent.setClass(MainActivity.this, ToolbarActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 45:{
                    intent.setClass(MainActivity.this, DrawerlayoutActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 46:{
                    intent.setClass(MainActivity.this, GestureDetectorActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 47:{
                    intent.setClass(MainActivity.this, LoginActivity.class);    //  MVP模式开发
                    jump(MainActivity.this,intent,false);
                }break;
                case 48:{
                    intent.setClass(MainActivity.this, SnapPageActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;
                case 49:{
                    intent.setClass(MainActivity.this, TabLayoutActivity.class);
                    jump(MainActivity.this,intent,false);
                }break;


                default:break;
            }
        }
    };

    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    public void onEventMainThread (FirstEvent event) {
        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Log.e("harvic", msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void showSnackbar(){
        Snackbar.make(getWindow().getDecorView(), "Hello SnackBar!", Snackbar.LENGTH_SHORT)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Perform anything for the action selected
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.skyblue))
                .setDuration(Snackbar.LENGTH_SHORT).show();
    }

    /*
    * interface接口写法
    * 请看为知笔记*/





}
