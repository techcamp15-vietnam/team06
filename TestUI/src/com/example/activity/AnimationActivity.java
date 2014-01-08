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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.testui.R;

public class AnimationActivity extends Activity {

	private Button btTakePic;
	private Button btSwitch;
	private Button btShare;
	private Spinner spinner;
	private String[] noOfPics;
	private int numberOfPics;
	private ImageView ivShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animation_mode);
		initVew();
	}

	public void initVew() {
		btTakePic = (Button) findViewById(R.id.button_take_picture);
		btSwitch = (Button) findViewById(R.id.button_switch_camera);
		btShare = (Button) findViewById(R.id.btShareInAnination);
		spinner = (Spinner) findViewById(R.id.spinShooseNumofPic);
		ivShow = (ImageView) findViewById(R.id.ivShow);

		btShare.setEnabled(false);

		noOfPics = getResources()
				.getStringArray(R.array.gif_number_of_pictures);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, noOfPics);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

		// Working with spinner to choose number of picture
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String choosen = parent.getItemAtPosition(position).toString();
				numberOfPics = Integer.parseInt(choosen);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		System.out.print(numberOfPics);

		// Take picture Button
		btTakePic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openCameraToTakePicture();
			}
		});

		// Switch camera Button
		btSwitch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

	// For Testing share Bluetooth
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
			ivShow.setImageBitmap(bmp);
			btShare.setEnabled(true);

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
	
	// End Testing Share vis Bluetooth
	
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
