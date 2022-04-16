package com.android.attendance.activity;

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

import com.android.attendance.bean.FacultyBean;
import com.android.attendance.bean.StudentBean;
import com.android.attendance.context.ApplicationContext;
import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;

public class LoginActivity extends Activity {

	Button login,back;
	EditText username,password;
	Spinner spinnerloginas;
	String userrole;
	private String[] userRoleString = new String[] { "admin", "faculty","student"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		login =(Button)findViewById(R.id.buttonlogin);
		back=(Button)findViewById(R.id.buttonback);
		username=(EditText)findViewById(R.id.editTextusername);
		password=(EditText)findViewById(R.id.editTextpassword);
		spinnerloginas=(Spinner)findViewById(R.id.spinnerloginas);

		spinnerloginas.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				((TextView) arg0.getChildAt(0)).setTextColor(Color.RED);
				userrole =(String) spinnerloginas.getSelectedItem();


			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		//simple_spinner_item
		ArrayAdapter<String> adapter_role = new ArrayAdapter<String>(this,
				R.layout.color_spinner_layout, userRoleString);
		adapter_role
		.setDropDownViewResource(R.layout.spinner_dropdown_layout);
		spinnerloginas.setAdapter(adapter_role);

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(userrole.equals("admin"))
				{

					String user_name = username.getText().toString();
					String pass_word = password.getText().toString();

					if (TextUtils.isEmpty(user_name)) 
					{
						username.setError("Invalid User Name");
					}
					else if(TextUtils.isEmpty(pass_word))
					{
						password.setError("enter password");
					}
					else
					{
						if(user_name.equals("Ritadmin") & pass_word.equals("rit22")){


						/*Toast.makeText(getApplicationContext(),"Successfull Login", Toast.LENGTH_SHORT).show();*/
						   toastmethod("Successfull Login");

							Intent intent =new Intent(LoginActivity.this,MenuActivity.class);
							startActivity(intent);
							clear();
						}else{
							/*Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();*/
							toastmethod("Login Failed");
						}
					}
				}
				
				else if(userrole.equals("faculty"))
				{
					String user_name = username.getText().toString();
					String pass_word = password.getText().toString();

					if (TextUtils.isEmpty(user_name)) 
					{
						username.setError("Invalid User Name");
					}
					else if(TextUtils.isEmpty(pass_word))
					{
						password.setError("enter password");
					}
					DBAdapter dbAdapter = new DBAdapter(LoginActivity.this);
					FacultyBean facultyBean = dbAdapter.validateFaculty(user_name, pass_word);
					
					if(facultyBean!=null)
					{

						toastmethod("Successfull Login");
						Intent intent = new Intent(LoginActivity.this,AddAttandanceSessionActivity.class);
						startActivity(intent);
						clear();
						((ApplicationContext)LoginActivity.this.getApplicationContext()).setFacultyBean(facultyBean);
				  	 /*Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();*/
					}
					else
					{
						toastmethod("Login Failed");
						/*Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();*/
					}
				}
				else
				{
					String user_name = username.getText().toString();
					String pass_word = password.getText().toString();

					if (TextUtils.isEmpty(user_name))
					{
						username.setError("Invalid User Name");
					}
					else if(TextUtils.isEmpty(pass_word))
					{
						password.setError("enter password");
					}
					DBAdapter dbAdapter = new DBAdapter(LoginActivity.this);
					StudentBean studentBean = dbAdapter.validateStudent(user_name, pass_word);

					if(studentBean !=null)
					{

						toastmethod("Successfull Login");
						Intent intent = new Intent(LoginActivity.this,StudentLoginActivity.class);
						startActivity(intent);
						clear();
						((ApplicationContext)LoginActivity.this.getApplicationContext()).setStudentBean(studentBean);
						/*Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();*/
					}
					else
					{
						toastmethod("Login Failed");
						/*Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();*/
					}
				}






			}
		});
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent =new Intent(LoginActivity.this,MainActivity.class);
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
	public void clear()
	{
		username.setText("");
		password.setText("");
	}

	public void toastmethod(String str)
	{
		Toast toast=new Toast(getApplicationContext());
		toast.setGravity(Gravity.BOTTOM,0,30);

		TextView tv=new TextView(LoginActivity.this);
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
