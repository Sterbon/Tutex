package com.sterbon.tutex;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.webkit.DownloadListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import static com.sterbon.tutex.R.id.web;


/**
 * Created by Utsav on 7/30/2017.
 */

public class GDrive extends AppCompatActivity implements RewardedVideoAdListener{

    private static final String TAG = "GDrive";

        private WebView view;
        TextView murl;
        AdView mAdView;
        RewardedVideoAd mRewardedVideoAd;
        private DownloadManager mDownloadManger;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        view = (WebView) findViewById(web);
        Bundle message=getIntent().getExtras();
        String get_url = (String) message.get("message");
        startWebView(get_url);

        mAdView = (AdView) findViewById(R.id.ad1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(GDrive.this);
        loadRewardedVideoAd();

        view.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri durl = Uri.parse(url);
                i.setData(durl);
                startActivity(i);
            }
        });


        WebSettings webSettings = view.getSettings();
        view.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        view.getSettings().setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        webSettings.setUseWideViewPort(true);
        webSettings.setSaveFormData(true);


    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-9908917955723743/1329998085",
                new AdRequest.Builder().build());
    }

    private void startWebView(final String url) {

        view.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;


            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                else
                    view.loadUrl(String.valueOf(url));
                return true;
            }

            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(GDrive.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                try{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }

        });

        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(String.valueOf(url));
        //return true;
    }

    public void onBackPressed() {
        if (mRewardedVideoAd.isLoaded())
            mRewardedVideoAd.show();
        else {
            Intent intent = new Intent(GDrive.this, Main.class);
            startActivity(intent);
        }
    }


    @SuppressLint("ShowToast")
    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(GDrive.this,"Ad Loaded",Toast.LENGTH_LONG);

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        Intent intent = new Intent(GDrive.this, Main.class);
        startActivity(intent);

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }
}