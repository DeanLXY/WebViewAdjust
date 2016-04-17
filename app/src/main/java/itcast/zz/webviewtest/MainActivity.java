package itcast.zz.webviewtest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private int sceenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         WindowManager wm = this.getWindowManager();

        sceenWidth = wm.getDefaultDisplay().getWidth();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
//        int sceenWidth = metric.widthPixels;  // 屏幕宽度（像素）
//        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        sceenWidth /=density;
        sceenWidth-= 16;// 减去10px  做内边距
        mWebView = new WebView(this);
        setContentView(mWebView);
        mWebView.loadUrl("file:///android_asset/data.html");
        mWebView.addJavascriptInterface(new JsInterface(), "java");
        webviewSetting(mWebView.getSettings());


        mWebView.setWebViewClient(new WebViewClient() {
            // 其他的可以不写 这个方法一定要写
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
//                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (isRealod)
                    autoFitWebView();
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
        });

        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });


    }

    boolean isRealod = true;

    private void autoFitWebView() {
//        mWebView.loadUrl("javascript: window.java.showToast('xxxxx')");
//        mWebView.loadUrl("javascript: var test = window.getElementsById('test');window.java.showToast(test);");

//        mWebView.loadUrl("javascript: window.java.showToast('1');var imgs=document.getElementsByTagName('img');  for(var i=0;i<imgs.length;i++) window.java.showToast(imgs[i].src);");
        //ok
//        mWebView.loadUrl("javascript: var imgs=document.getElementsByTagName('img');window.java.showToast(imgs.length+'') ; for(var i=0;i<imgs.length;i++){imgs[i].width='100%'; window.java.showToast(imgs[i].sceenWidth+'')};");
        //ok
//        mWebView.loadUrl("javascript: var imgs=document.getElementsByTagName('img');window.java.showToast(imgs.length+'') ; for(var i=0;i<imgs.length;i++) {imgs[i].width=320;}");
        // 320-----屏幕宽度  像素不一样  效果差
        mWebView.loadUrl("javascript: var imgs=document.getElementsByTagName('img');window.java.showToast(imgs.length+'张图片') ; for(var i=0;i<imgs.length;i++) {imgs[i].width="+sceenWidth+";}");
    // 320----100% error
//        mWebView.loadUrl("javascript: var imgs=document.getElementsByTagName('img');window.java.showToast(imgs.length+'') ; for(var i=0;i<imgs.length;i++) {imgs[i].width=100%;}");
//        mWebView.loadUrl("javascript: window.java.showToast('1');var imgs=document.getElementsByTagName('img');  for(var i=0;i<imgs.length;i++) imgs[i].sceenWidth=100%;for(var i=0;i<imgs.length;i++) window.java.showToast(imgs[i].src);");
//        mWebView.reload();
    isRealod = false;
}


    class JsInterface {
        @JavascriptInterface
        public void showToast(String text) {
            Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
        }
    }

    private void webviewSetting(WebSettings settings) {
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);  //就是这句
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setLightTouchEnabled(true);
        // requestFocus();
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setAllowFileAccess(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        settings.setLoadWithOverviewMode(true);

        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

//        settings.setSupportZoom(false);
        // Technical settings
        settings.setSupportMultipleWindows(true);

        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        String dirapp = getDir("cache", Context.MODE_PRIVATE).getPath();
        settings.setAppCacheMaxSize(Long.MAX_VALUE);
        settings.setAppCachePath(dirapp);
        String dir = getDir("databases", Context.MODE_PRIVATE).getPath();
        settings.setDatabasePath(dir);
        dir = getDir("geolocation", Context.MODE_PRIVATE).getPath();
        settings.setGeolocationDatabasePath(dir);
    }
}
