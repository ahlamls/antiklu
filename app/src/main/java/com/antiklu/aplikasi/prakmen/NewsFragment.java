package com.antiklu.aplikasi.prakmen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.antiklu.aplikasi.R;
import com.antiklu.aplikasi.SpawnActivity;
import com.antiklu.aplikasi.settings.Server;

import static com.antiklu.aplikasi.SpawnActivity.my_shared_preferences;

public class NewsFragment extends Fragment {
WebView webview;
TextView title_tv;
    SharedPreferences sharedpreferences;
    Boolean session = false;
    String fuid;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        webview = getView().findViewById(R.id.webview);
        title_tv = getView().findViewById(R.id.textView5);

        ImageView backImage = getView().findViewById(R.id.backImage);

        backImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                HomeFragment fragment2 = new HomeFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, fragment2,"home")
                        .commit();
            }


        });

        sharedpreferences = this.getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean("login", false);
        fuid = sharedpreferences.getString("fuid", null);
        if (!session) {
            Intent intent = new Intent(getActivity(), SpawnActivity.class);
            this.getActivity().finish();
            startActivity(intent);
        }

        initWebView();

    }

    void initWebView() {
        webview.setWebViewClient(new myWebclient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(Server.URL + "berita.php");

    }

    public class myWebclient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            title_tv.setText(view.getTitle());
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }


}
