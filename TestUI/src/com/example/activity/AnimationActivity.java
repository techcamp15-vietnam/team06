package com.example.activity;

import com.example.testui.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * Animation Mode
 * 
 * @param None
 * @author B06 Pham Binh
 */
public class AnimationActivity extends Activity {

	private Button btSwitch;
	private Spinner spinner;
	private String[] noOfPics;
//	private int numberOfPics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animation_mode);
		initView();
	}

	private void initView() {
		btSwitch = (Button) findViewById(R.id.button_switch_camera);
		spinner = (Spinner) findViewById(R.id.spinShooseNumofPic);

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
//				String choosen = parent.getItemAtPosition(position).toString();
//				numberOfPics = Integer.parseInt(choosen);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		
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

}
