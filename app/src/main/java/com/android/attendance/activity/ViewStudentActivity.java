package com.android.attendance.activity;

import com.example.androidattendancesystem.R;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class ViewStudentActivity extends Activity {

	Spinner spinnerbranch,spinneryear;
	String userrole,branch,year;
	private String[] branchString = new String[] { "cse"};
	private String[] yearString = new String[] {"IY","IIY","IIIY","IVY"};
	
	Button submit ,cancel;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewstudent);
		
		spinnerbranch=(Spinner)findViewById(R.id.spinnerbranchView);
		spinneryear=(Spinner)findViewById(R.id.spinneryearView);
		
		
		spinnerbranch.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				((TextView) arg0.getChildAt(0)).setTextColor(Color.RED);
				branch =(String) spinnerbranch.getSelectedItem();
                toastmethod("Branch : "+branch);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		ArrayAdapter<String> adapter_branch = new ArrayAdapter<String>(this,
				R.layout.color_spinner_layout, branchString);
		adapter_branch
		.setDropDownViewResource(R.layout.spinner_dropdown_layout);
		spinnerbranch.setAdapter(adapter_branch);

		///......................spinner2

		spinneryear.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				((TextView) arg0.getChildAt(0)).setTextColor(Color.RED);

				year =(String) spinneryear.getSelectedItem();
                toastmethod("Year : "+year);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this,
				R.layout.color_spinner_layout, yearString);
		adapter_year
		.setDropDownViewResource(R.layout.spinner_dropdown_layout);
		spinneryear.setAdapter(adapter_year);
		
		submit=(Button)findViewById(R.id.submitButton);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				Intent intent = new Intent(ViewStudentActivity.this,ViewStudentByBranchYear.class);
				intent.putExtra("branch", branch);
				intent.putExtra("year", year);
				startActivity(intent);
				
			}
		});

		cancel=(Button)findViewById(R.id.cancelButton);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent =new Intent(ViewStudentActivity.this,MenuActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});



	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void toastmethod(String str)
	{
		Toast toast=new Toast(getApplicationContext());
		toast.setGravity(Gravity.BOTTOM,0,30);

		TextView tv=new TextView(ViewStudentActivity.this);
		tv.setBackgroundColor(Color.BLACK);
		tv.setTextColor(Color.WHITE);
		tv.setTextSize(20);

		Typeface t=Typeface.create("bold",Typeface.BOLD_ITALIC);
		tv.setTypeface(t);
		tv.setPadding(10,10,10,10); //LTRB
		tv.setText(str);
		toast.setView(tv);
		toast.show();
	}

}
