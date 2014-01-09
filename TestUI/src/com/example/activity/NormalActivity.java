package com.example.activity;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.testui.R;

/**
Normal mode
@param None
@author B06 Pham Binh
*/
public class NormalActivity extends Activity {

	private Button btTakePic;
	private Button btShare;
	private ImageView ivShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.normal_mode);
		initVew();
	}

	/**
	Normal Mode
	@param Creat action for button
	@author B06 Pham Binh
	*/
	public void initVew() {
		btTakePic = (Button) findViewById(R.id.button_take_picture);
		
		btShare = (Button) findViewById(R.id.btShareInAnination);
		
		ivShow = (ImageView) findViewById(R.id.ivShow);

		btShare.setEnabled(false);

		

		// Take picture Button
		btTakePic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openCameraToTakePicture();
			}
		});

		// Switch camera Button
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

	public void deleteTempFile() {
		File rootFolder = Environment.getExternalStorageDirectory();
		File temFile = new File(rootFolder.getAbsolutePath() + "/tmp.jpg");
		if (temFile.exists()) {
			temFile.delete();
		}
	}

	private void openCameraToTakePicture() {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		this.deleteTempFile();
		intent.putExtra(MediaStore.EXTRA_OUTPUT, this.getPhotoUri());
		startActivityForResult(intent, 0);
	}

	/**
	Index
	@param After take a picture, share it!
	@author B06 Pham Binh
	*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != RESULT_OK) {
			return;
		}

		String imaPath = Environment.getExternalStorageDirectory()
				+ File.separator + "tmp.jpg";
		Bitmap bmp = BitmapFactory.decodeFile(imaPath);
		if (bmp != null) {
			btShare.setEnabled(true);
			ivShow.setImageBitmap(bmp);


			btShare.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					File file = new File(Environment.getExternalStorageDirectory().
				            getPath() + "/tmp.jpg");
					Intent i = new Intent(android.content.Intent.ACTION_SEND);
					i.setType("image/*");
					i.setComponent(new ComponentName("com.android.bluetooth",
							"com.android.bluetooth.opp.BluetoothOppLauncherActivity"));
					i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
					startActivity(i);
				}
			});
		}		
	}
	
	public Uri getPhotoUri() {
		File rootFolder = Environment.getExternalStorageDirectory();
		File tempPhoto = new File(rootFolder.getAbsolutePath() + "/tmp.jpg");
		try {
			if (!tempPhoto.exists()) {
				tempPhoto.createNewFile();
			}
			Uri tempPhotoUri = Uri.fromFile(tempPhoto);
			return tempPhotoUri;
		} catch (IOException e) {
			e.printStackTrace();
			return Uri.EMPTY;
		}
	}
}
