package lib.emerson.com.emersonapplib.domain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.phonegap.DroidGap;

import lib.emerson.com.emersonapplib.R;

/**
 * Created by YangJianCong on 2016/10/6.
 */
public class Html5Activity extends DroidGap {
    private WebView webView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        webView = (WebView) findViewById(R.id.activity_html_webview);
        webView.setWebViewClient(new MyWebViewClient());
    }


    class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl("file:///android_asset/www/index.html");
            return true;
        }
    }


}
