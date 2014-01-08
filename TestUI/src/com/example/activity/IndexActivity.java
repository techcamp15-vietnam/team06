package com.example.activity;

import com.example.control.ScreenCapture;
import com.example.testui.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
Index
@param MainTheme
@author B06 Pham Binh
*/
public class IndexActivity extends Activity {

	private Button btNormal;
	private Button btGif;
	private Button btLiveShare;
	private ImageView ivCapture;
	private RelativeLayout loIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);
		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	Index
	@param None
	@author B06 Pham Binh
	Use to Creat action for button.
	*/
	public void initView() {		
		btNormal = (Button) findViewById(R.id.btNormal);
		btGif = (Button) findViewById(R.id.btGif);
		btLiveShare = (Button) findViewById(R.id.btLiveShare);
		ivCapture = (ImageView) findViewById(R.id.ivCapture);
		loIndex = (RelativeLayout) findViewById(R.id.loIndex);

		View.OnClickListener handler = new View.OnClickListener() {

			public void onClick(View v) {

				// Normal mode
				if (v == btNormal) {
					// doStuff
					Intent intentNormal = new Intent(IndexActivity.this,NormalActivity.class);
					IndexActivity.this.startActivity(intentNormal);
					Log.i("Content ", "Normal Mode");
				}
				
				// Making animation pictures
				if (v == btGif) {
					// doStuff
					Intent intentGif = new Intent(IndexActivity.this,AnimationActivity.class);
					IndexActivity.this.startActivity(intentGif);
				}
				
				if (v == btLiveShare) {
					btLiveShare.setVisibility(View.INVISIBLE);
					Bitmap bitmap;
					ScreenCapture screenCapture = new ScreenCapture();
					bitmap = screenCapture.getBitmapOfView(ivCapture);
					bitmap = Bitmap.createBitmap(bitmap, 0, 130, loIndex.getWidth(), loIndex.getHeight()-30);
					screenCapture.createImageFromBitmap(bitmap);
					btLiveShare.setVisibility(View.VISIBLE);
				}
			}
		};

		btNormal.setOnClickListener(handler);
		btGif.setOnClickListener(handler);
		btLiveShare.setOnClickListener(handler);
	}
}
