package com.example.control;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;

/**
 * Index
 * 
 * @param None
 * @author B06 Pham Binh
 */

public class ScreenCapture {

	/*
	 * @author B06 Pham Binh 
	 * This method is used to create the bitmap of the
	 * current activity This method accepts any child view of the current view
	 * You can even pass the parent container like RelativeLayout or
	 * LinearLayout as a param
	 * 
	 * @param : View v
	 */
	public Bitmap getBitmapOfView(View v) {
		View rootview = v.getRootView();
		rootview.setDrawingCacheEnabled(true);
		Bitmap bmp = rootview.getDrawingCache();
		return bmp;
	}

	/*
     * @author B06 Pham Binh 
     * This method is used to create an image file using the bitmap
     * This method accepts an object of Bitmap class
     * Currently we are passing the bitmap of the root view of current activity
     * The image file will be created by the name capturedscreen.jpg
     * @param : Bitmap bmp
     */
	public void createImageFromBitmap(Bitmap bmp) {		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/capturedscreen.jpg");
		try {
			file.createNewFile();
			FileOutputStream ostream = new FileOutputStream(file);
			ostream.write(bytes.toByteArray());
			ostream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}