package com.example.activity;


import com.example.support.GifWebView;
import com.example.testui.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.FrameLayout;

public class DisplayGifActivity extends Activity {

	private GifWebView mGifWebView;
	static String gif_path = HomeActivity.gif_path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_gif);
		initView();
	}

	/**
	 * @author 6-A Nguyen Tuan Hai
	 * @param 
	 * @description
	 */
	private void initView() {
		gif_path = HomeActivity.gif_path;
		mGifWebView = new GifWebView(this, gif_path);
		FrameLayout previewLayout = (FrameLayout) findViewById(R.id.preview);
		previewLayout.addView(mGifWebView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
