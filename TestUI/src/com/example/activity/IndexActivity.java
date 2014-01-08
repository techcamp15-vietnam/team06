package com.example.activity;

import com.example.testui.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class IndexActivity extends Activity {

	private Button btNormal;
	private Button btGif;
	private Button btLiveShare;

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

	public void initView() {		
		btNormal = (Button) findViewById(R.id.btNormal);
		btGif = (Button) findViewById(R.id.btGif);
		btLiveShare = (Button) findViewById(R.id.btLiveShare);

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
					Log.i("Content ", "Animation Mode");
				}
				
				if (v == btLiveShare) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"Live Share Choosen", Toast.LENGTH_SHORT);
					toast.show();

//					Intent intentShare = new Intent(IndexActivity.this,AnimationActivity.class);
//					IndexActivity.this.startActivity(intentShare);
//					Log.i("Content ", "Share Mode");
				}
			}
		};

		btNormal.setOnClickListener(handler);
		btGif.setOnClickListener(handler);
		btLiveShare.setOnClickListener(handler);
	}
}
