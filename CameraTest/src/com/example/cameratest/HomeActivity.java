package com.example.cameratest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {

	private Button mBtnShow, mBtnCreate, mBtnPlay;
	private TextView mTextView;
	private ImageView mImageView;
	int numberOfShots = 2;
	int shot = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
	}

	private void initView() {
		numberOfShots = 2;
		shot = 0;
		mBtnShow = (Button) findViewById(R.id.button_show);
		mImageView = (ImageView) findViewById(R.id.image_view);
		mBtnShow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openCameraToTakePicture();
			}
		});

		mBtnCreate = (Button) findViewById(R.id.button_create);
		mBtnCreate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				createGif();
			}
		});
		mBtnPlay = (Button) findViewById(R.id.button_play);
		mBtnPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playGif();
			}
		});

	}

	public byte[] generateGIF() {
		// ImageAdapter adapter;
		// ArrayList<Bitmap> bitmaps = adapter.getBitmapArray();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		AnimatedGifEncoder encoder = new AnimatedGifEncoder();
		Options option = new Options();
		option.inSampleSize = 8;
		encoder.setDelay(500);
		encoder.setRepeat(0);
		encoder.start(bos);
		for (int i = 0; i < 5; i++) {
			String imgPath = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/gif_tmp/tmp" + i + ".jpg";
			Bitmap bmp = BitmapFactory.decodeFile(imgPath, option);
			encoder.addFrame(bmp);
			Log.i("generate", "add Frame" + imgPath);
		}

		// for (Bitmap bitmap : bitmaps) {
		// encoder.addFrame(bitmap);
		// }
		encoder.finish();
		return bos.toByteArray();
	}

	private void createGif() {
		FileOutputStream outStream = null;
		File tempPhoto = null;
		int number = 0;
		try {
			tempPhoto = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/gif_test/tmp" + number + ".gif");
			while (tempPhoto.exists()) {
				number++;
				tempPhoto = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/gif_test/tmp" + number + ".gif");
				System.out.println(number);
			}
			tempPhoto.delete();
			outStream = new FileOutputStream(Environment
					.getExternalStorageDirectory().getAbsolutePath()
					+ "/gif_test/tmp" + number + ".gif");
			Log.i("info", "prepare to generate");
			outStream.write(generateGIF());
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void playGif() {

	}

	private void openCameraToTakePicture() {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		// deleteTempFile();
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoUri());
		startActivityForResult(intent, 0);
	}

	public void deleteTempFile() {
		File rootFolder = Environment.getExternalStorageDirectory();
		File temFile = new File(rootFolder.getAbsolutePath() + "/tmp.jpg");
		if (temFile.exists()) {
			temFile.delete();
		}
	}

	public Uri getPhotoUri() {
		int number = 0;
		File rootFolder = Environment.getExternalStorageDirectory();
		File tempPhoto = new File(rootFolder.getAbsolutePath() + "/gif_tmp/tmp"
				+ number + ".jpg");
		try {
			while (tempPhoto.exists()) {
				number++;
				tempPhoto = new File(rootFolder.getAbsolutePath()
						+ "/gif_tmp/tmp" + number + ".jpg");
			}
			tempPhoto.createNewFile();
			Uri tempPhotoUri = Uri.fromFile(tempPhoto);
			return tempPhotoUri;
		} catch (IOException e) {
			e.printStackTrace();
			return Uri.EMPTY;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if (resultCode != RESULT_OK) {
		// return;
		// }
		//
		// // get Image path
		// String imgPath = Environment.getExternalStorageDirectory()
		// + File.separator + "tmp.jpg";
		// Bitmap bmp = BitmapFactory.decodeFile(imgPath);
		// if (bmp != null) {
		// mImageView.setImageBitmap(bmp);
		// }
		shot++;
		if (shot < numberOfShots)
			openCameraToTakePicture();
	}

}
