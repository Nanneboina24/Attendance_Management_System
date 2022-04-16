package com.android.attendance.activity;

import com.android.attendance.bean.StudentBean;
import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddStudentActivity extends Activity {

	Button registerButton;
	Button cancelButton;
	EditText textFirstName;
	EditText textLastName;

	EditText textcontact;
	EditText textaddress;
	Spinner spinnerbranch,spinneryear;
	String userrole,branch,year;
	private String[] branchString = new String[] { "cse"};
	private String[] yearString = new String[] {"IY","IIY","IIIY","IVY"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addstudent);

		spinnerbranch=(Spinner)findViewById(R.id.spinnerdept);
		spinneryear=(Spinner)findViewById(R.id.spinneryear);
		textFirstName=(EditText)findViewById(R.id.editTextFirstName);
		textLastName=(EditText)findViewById(R.id.editTextLastName);
		textcontact=(EditText)findViewById(R.id.editTextPhone);
		textaddress=(EditText)findViewById(R.id.editTextaddr);
		registerButton=(Button)findViewById(R.id.RegisterButton);
		cancelButton=(Button)findViewById(R.id.Cancel_Button);

		spinnerbranch.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				((TextView) arg0.getChildAt(0)).setTextColor(Color.RED);
				branch =(String) spinnerbranch.getSelectedItem();

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



		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//......................................validation
				String first_name = textFirstName.getText().toString();
				String last_name = textLastName.getText().toString();
				String phone_no = textcontact.getText().toString();
				String address = textaddress.getText().toString();

				if (TextUtils.isEmpty(first_name)) {
					textFirstName.setError("please enter firstname");
				}

				else if (TextUtils.isEmpty(last_name)) {
					textLastName.setError("please enter lastname");
				}
				else if (TextUtils.isEmpty(phone_no)) {
					textcontact.setError("please enter phoneno");
				}

				else if (TextUtils.isEmpty(address)) {
					textaddress.setError("enter address");
				}				
				else { 
					
					StudentBean studentBean = new StudentBean();
					
					studentBean.setStudent_firstname(first_name);
					studentBean.setStudent_lastname(last_name);
					studentBean.setStudent_mobilenumber(phone_no);
					studentBean.setStudent_address(address);
					studentBean.setStudent_department(branch);
					studentBean.setStudent_class(year);
					
					DBAdapter dbAdapter= new DBAdapter(AddStudentActivity.this);
					dbAdapter.addStudent(studentBean);
					toastmethod("student added successfully");
					Intent intent =new Intent(AddStudentActivity.this,MenuActivity.class);
					startActivity(intent);
					/*Toast.makeText(getApplicationContext(), "student added successfully", Toast.LENGTH_SHORT).show();*/

				}
			}
		});
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent =new Intent(AddStudentActivity.this,MenuActivity.class);
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

		TextView tv=new TextView(AddStudentActivity.this);
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
