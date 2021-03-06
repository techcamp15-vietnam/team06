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
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {

	private Button mBtnShow, mBtnCreate, mBtnPlay;
	private TextView mTextView;
	private ImageView mImageView;
	static String gif_path = "file:/"
			+ Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/gif_test/tmp6.gif";

	int numberOfShots = 5;
	int shot = 0;
	int heightOfGif = 0;
	int widthOfGif = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
	}

	/**
	 * @author myname2
	 */
	private void initView() {
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
				String temp_path = createGif();
				if (temp_path != null) {
					gif_path = "file:/" + temp_path;
					// initView();
					Intent intentDisplayGif = new Intent(HomeActivity.this,
							DisplayGifActivity.class);
					HomeActivity.this.startActivity(intentDisplayGif);
				} else {
					Toast toast = Toast
							.makeText(
									getApplicationContext(),
									"A photo has different size with others. All photo must have same size",
									Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});
		mBtnPlay = (Button) findViewById(R.id.button_play);
		mBtnPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentDisplayGif = new Intent(HomeActivity.this,
						DisplayGifActivity.class);
				HomeActivity.this.startActivity(intentDisplayGif);
			}
		});
	}

	/**
	 * @author myname2
	 */
	public byte[] generateGIF(int number) {
		// ImageAdapter adapter;
		// ArrayList<Bitmap> bitmaps = adapter.getBitmapArray();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		AnimatedGifEncoder encoder = new AnimatedGifEncoder();
		Options option = new Options();
		option.inSampleSize = 8;
		encoder.setDelay(500);
		encoder.setRepeat(0);
		encoder.start(bos);
		for (int i = 0; i < number; i++) {
			String imgPath = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/gif_tmp/tmp" + i + ".jpg";
			Bitmap bmp = BitmapFactory.decodeFile(imgPath, option);
			if (i == 0) {
				heightOfGif = bmp.getHeight();
				widthOfGif = bmp.getWidth();
				Log.i("info", "height = " + heightOfGif + ", width = "
						+ widthOfGif);
			} else if ((bmp.getHeight() != heightOfGif)
					|| (bmp.getWidth() != widthOfGif)) {
				Log.i("info", "wrong size");
				Log.i("info", "restart activity");
				// restartActivity();
				return null;
			}
			encoder.addFrame(bmp);
			Log.i("generate", "added frame " + imgPath);
			File temFile = new File(imgPath);
			if (temFile.exists()) {
				Log.i("delete", "deleted " + imgPath);
				temFile.delete();
			}
		}
		encoder.finish();
		return bos.toByteArray();
	}

	/**
	 * @author 6-A Nguyen Tuan Hai
	 * @param
	 * @description
	 */
	void restartActivity() {
		finish();
		Intent mIntent = new Intent(HomeActivity.this, HomeActivity.class);
		startActivity(mIntent);
	}

	/**
	 * @author myname2
	 */
	private String createGif() {
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
			}
			tempPhoto.delete();
			outStream = new FileOutputStream(Environment
					.getExternalStorageDirectory().getAbsolutePath()
					+ "/gif_test/tmp" + number + ".gif");
			Log.i("info", "prepare to generate");
			byte[] temp = generateGIF(numberOfShots);
			if (temp != null) {
				outStream.write(temp);
				outStream.close();
				deleteTempFile(numberOfShots);
				return Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/gif_test/tmp" + number + ".gif";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @author myname2
	 */
	private void openCameraToTakePicture() {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		// deleteTempFile();
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoUri());
		startActivityForResult(intent, 0);
	}

	/**
	 * @author myname2
	 * @param numberOfFiles
	 *            number of files need to be deleted
	 */
	public void deleteTempFile(int numberOfFiles) {
		for (int i = 0; i < numberOfFiles; i++) {
			String imgPath = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/gif_tmp/tmp" + i + ".jpg";
			File temFile = new File(imgPath);
			if (temFile.exists()) {
				temFile.delete();
			}
		}
	}

	/**
	 * @author 6-A Nguyen Tuan Hai
	 * @param
	 * @description
	 */
	public Uri getPhotoUri() {
		int number = 0;
		File rootFolder = Environment.getExternalStorageDirectory();
		File tempPhoto = new File(rootFolder.getAbsolutePath() + "/gif_tmp/tmp"
				+ number + ".jpg");
		try {
			while (tempPhoto.exists()) {
				tempPhoto.delete();
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
		if (resultCode != RESULT_OK) {
			openCameraToTakePicture();
		} else {
			shot++;
			if (shot < numberOfShots)
				openCameraToTakePicture();
			else {
				String temp_path = createGif();
				if (temp_path != null) {
					gif_path = "file:/" + temp_path;
					// initView();
					Intent intentDisplayGif = new Intent(HomeActivity.this,
							DisplayGifActivity.class);
					HomeActivity.this.startActivity(intentDisplayGif);
				} else {
					Toast toast = Toast
							.makeText(
									getApplicationContext(),
									"A photo has different size with others. All photo must have same size",
									Toast.LENGTH_LONG);
					toast.show();
				}
				shot = 0;
			}
		}
		//
		// // get Image path
		// String imgPath = Environment.getExternalStorageDirectory()
		// + File.separator + "tmp.jpg";
		// Bitmap bmp = BitmapFactory.decodeFile(imgPath);
		// if (bmp != null) {
		// mImageView.setImageBitmap(bmp);
		// }
	}
}
