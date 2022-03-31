package com.example.webbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Button refresh;
    WebView webView;
    EditText editUrl;
    Button shoutOut;
    String index = "index.html";
    String url;
    String prevUrl;
    String nextUrl;
    ArrayList<String> prevList = new ArrayList<String>();
    ArrayList<String> nextList = new ArrayList<String>();
    int iNext = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh = (Button) findViewById(R.id.button);
        shoutOut = (Button) findViewById(R.id.button2);
        webView = (WebView) findViewById(R.id.webView);
        editUrl = (EditText) findViewById(R.id.editTextUrl);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        editUrl.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(url != null) {
                        prevUrl = url;
                        prevList.add(prevUrl);
                    }
                    if(nextList != null) {
                        nextList.clear();
                    }
                    iNext = -1;
                    url = editUrl.getText().toString();
                    loadUrl(url);
                    return true;
                }
                return false;
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                webView.reload();
            }
        });

    }

    public void ShoutOut(View v) {
        webView.evaluateJavascript("javascript:shoutOut()", null);
    }

    public void Initialize(View v) {
        webView.evaluateJavascript("javascript:initialize()", null);
    }

    public void nextPage(View v) {
        if(iNext > -1) {
            prevUrl = url;
            prevList.add(prevUrl);
            nextUrl = nextList.get(iNext);
            url = nextUrl;
            loadUrl(url);
            iNext--;
        }
    }
    public void prevPage(View v) {
        int i = prevList.size()-1;
        if(i >= 0) {
            prevUrl = prevList.get(i);
            nextUrl = url;
            nextList.add(nextUrl);
            url = prevUrl;
            loadUrl(url);
            prevList.remove(i);
            iNext++;
        }
    }
    public void loadUrl(String URL) {
        if(URL.equals(index)) {
            webView.loadUrl("file:///android_asset/index.html");
        } else {
            webView.loadUrl("http://" + URL);
        }
    }

}



