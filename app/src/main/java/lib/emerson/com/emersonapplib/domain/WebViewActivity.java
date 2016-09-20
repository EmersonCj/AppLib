package lib.emerson.com.emersonapplib.domain;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by YangJianCong on 2016/9/7.
 */
public class WebViewActivity extends baseActivity {
    private WebView webview;
    private String url = "http://test.wxw7z.com/index.php/Api/Index/getUseTerms";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webview = (WebView) findViewById(R.id.webview);

        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);

        //加载需要显示的网页
        webview.loadUrl(url);
        // 触摸焦点起作用
        webview.requestFocus();

        //取消滚动条
        webview.setHorizontalScrollBarEnabled(false);//水平不显示
        webview.setVerticalScrollBarEnabled(false); //垂直不显示

        //设置WebView可触摸放大缩小
        webview.getSettings().setBuiltInZoomControls(true);
        webview.setInitialScale(50);
        webview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        //使用我们自己定义的浏览器打开
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.e("onPageStarted","onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("onPageFinished","onPageFinished");
            }

            //....


        });
    }



    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(webview.canGoBack()){
            if ((keyCode == KeyEvent.KEYCODE_BACK)){
                webview.goBack(); //goBack()表示返回WebView的上一页面
                return true;
            }
        }else {
            finish();
        }
        return false;
    }


    public void webView(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View popView = inflater.inflate(R.layout.activity_webview, null);

        webview = (WebView) popView.findViewById(R.id.webview);
        //设置WebView的一些缩放功能点
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webview.setHorizontalScrollBarEnabled(false);
        webview.getSettings().setSupportZoom(true);
        //设置WebView可触摸放大缩小
        webview.getSettings().setBuiltInZoomControls(true);
        webview.setInitialScale(70);
        webview.setHorizontalScrollbarOverlay(true);
        //WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小
        //webView.getSettings().setUseWideViewPort(true);
        //提高渲染的优先级
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        //允许JS执行
        webview.getSettings().setJavaScriptEnabled(true);
        //把图片加载放在最后来加载渲染
        //webView.getSettings().setBlockNetworkImage(true);
        //用WebView将字符串以HTML的形式显示出来
        //webView.loadDataWithBaseURL("fake://not/needed", <p>zzz</p>, "text/html", "utf-8", "");
        //在同种分辨率的情况下,屏幕密度不一样的情况下,自动适配页面:
        DisplayMetrics dm = getResources().getDisplayMetrics();
        // 获取当前界面的高度
        //int width = dm.widthPixels;
        //int height = dm.heightPixels;
        int scale = dm.densityDpi;
        if (scale == 240) {
            webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (scale == 160) {
            webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else {
            webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }
        webview.setWebViewClient(new WebViewClient() {
            // 点击超链接的时候重新在原来进程上加载URL
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            // webview加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
               // progressBar.setVisibility(View.GONE);
                // progressBar.setVisibility(View.VISIBLE);
            }
        });
        //listview,webview中滚动拖动到顶部或者底部时的阴影
        webview.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//使用缓存
        //WebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); //默认不使用缓存！
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //取消滚动条白边效果
        webview.loadUrl("http://www.baidu.com/");

       /* dialog.setContentView(popView);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        */
    }

}
