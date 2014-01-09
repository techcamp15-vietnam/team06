package com.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.control.ScreenCapture;
import com.example.testui.R;

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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		btGif.setBackgroundResource(R.drawable.button_shape);
		btNormal.setBackgroundResource(R.drawable.button_shape);
		btLiveShare.setBackgroundResource(R.drawable.button_shape);
	}

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
					btNormal.setBackgroundResource(R.drawable.button_shape_after);
					Intent intentNormal = new Intent(IndexActivity.this,NormalActivity.class);
					IndexActivity.this.startActivity(intentNormal);
				}
				
				// Animation Mode
				if (v == btGif) {
					btGif.setBackgroundResource(R.drawable.button_shape_after);
					Intent intentGif = new Intent(IndexActivity.this,HomeActivity.class);
					IndexActivity.this.startActivity(intentGif);
				}
				
				// Share Mode 
				if (v == btLiveShare) {
					btLiveShare.setVisibility(View.INVISIBLE);
					
					Bitmap bitmap;
					ScreenCapture screenCapture = new ScreenCapture();
					bitmap = screenCapture.getBitmapOfView(ivCapture);
					bitmap = Bitmap.createBitmap(bitmap, 0, 150, loIndex.getWidth(), loIndex.getHeight()-50);
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
