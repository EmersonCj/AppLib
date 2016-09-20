package lib.emerson.com.emersonapplib.domain;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.view.snapscrollview.McoyProductContentPage;
import lib.emerson.com.emersonapplib.view.snapscrollview.McoyProductDetailInfoPage;
import lib.emerson.com.emersonapplib.view.snapscrollview.McoySnapPageLayout;

/**
 * Created by YangJianCong on 2016/9/13.
 * 获取View类界面控件的位置: https://my.oschina.net/u/1376187/blog/172792
 */
public class SnapPageActivity extends baseActivity{
    private McoySnapPageLayout mcoySnapPageLayout = null;

    private McoyProductContentPage bottomPage = null;
    private McoyProductDetailInfoPage topPage = null;


    private WebView webview;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snappage);

        mcoySnapPageLayout = (McoySnapPageLayout) findViewById(R.id.flipLayout);
        topPage = new McoyProductDetailInfoPage(SnapPageActivity.this, getLayoutInflater().inflate(R.layout.mcoy_produt_detail_layout, null));
        bottomPage = new McoyProductContentPage(SnapPageActivity.this, getLayoutInflater().inflate(R.layout.mcoy_product_content_page, null));
        mcoySnapPageLayout.setSnapPages(topPage, bottomPage);


    }

}
