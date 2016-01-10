package nerdlauncher.android.bignerdranch.com.photogallery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Denis on 10.01.2016.
 */
public class PhotoPageFragment extends VisibleFragment {
    private String mUrl;
    private WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mUrl=getActivity().getIntent().getData().toString();
        }
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,
                             Bundle savedInstanceState){
        View vv=inflater.inflate(R.layout.fragment_photo_page,parent,false);

        final ProgressBar progressBar=(ProgressBar)vv.findViewById(R.id.progressBraMy);
        progressBar.setMax(100);
        final TextView titleTextView=(TextView)vv.findViewById(R.id.titleTextVIew);

        mWebView=(WebView)vv.findViewById(R.id.webView_my);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int progress) {
                if (progress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }
            }
            public void onReceivedTitle(WebView webView,String title){
                titleTextView.setText(title);
            }
        });
        mWebView.loadUrl(mUrl);

        return vv;
    }
}
