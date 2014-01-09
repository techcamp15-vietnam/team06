package com.example.support;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.WebView;

@SuppressLint("ViewConstructor")
public class GifWebView extends WebView {

	public GifWebView(Context context, String path) {
		super(context);
		loadUrl(path);
	}
}
