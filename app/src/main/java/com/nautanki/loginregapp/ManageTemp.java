package com.nautanki.loginregapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ManageTemp extends AppCompatActivity {

    private static WebView browser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_manage_temp);
        getSupportActionBar().hide();
        browser=findViewById(R.id.webview1);
        String url="https://untruthful-oscillat.000webhostapp.com/home.php";
        browser.setWebViewClient(new WebViewClient());
        browser.getSettings().setLoadsImagesAutomatically(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        browser.loadUrl(url);



    }

    @Override
    public void onBackPressed() {
        if(browser.canGoBack()){
            browser.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}
