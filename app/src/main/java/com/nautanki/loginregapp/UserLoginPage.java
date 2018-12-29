package com.nautanki.loginregapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class UserLoginPage extends AppCompatActivity {
    private static WebView browser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_login_page);
        getSupportActionBar().hide();
        browser=findViewById(R.id.webview);
        String url="http://kniceassist.com/";
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

    public void manage(View view) {
        Toast.makeText(this, "Loding...", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,ManageTemp.class));
    }
}