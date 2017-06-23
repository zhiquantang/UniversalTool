package test.ruili.com.universaltool.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import test.ruili.com.universaltool.R;

public class BaiduActivity extends AppCompatActivity {

    private static final String URL_BAIDU = "https://www.baidu.com";

    private WebView web_baidu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu);

        initView();
    }

    private void initView(){
        web_baidu = (WebView)findViewById(R.id.web_baidu);
        web_baidu.setWebViewClient(my_WebViewClient);
        web_baidu.loadUrl(URL_BAIDU);
    }

    private WebViewClient my_WebViewClient = new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };
}
