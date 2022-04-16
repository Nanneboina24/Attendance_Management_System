package com.android.attendance.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.*;
import java.lang.*;

import com.android.attendance.bean.AttendanceBean;
import com.android.attendance.bean.AttendanceSessionBean;
import com.android.attendance.bean.FacultyBean;
import com.android.attendance.bean.StudentBean;
import com.android.attendance.context.ApplicationContext;
import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddAttandanceSessionActivity<AddAttandanceActivity> extends Activity {

	private ImageButton date;
	private Calendar cal;
	private int day;
	private int month;
	private int dyear;
	private EditText dateEditText;
	Button submit;
	Button viewAttendance;
	Button viewTotalAttendance;
	Button logout;
	Button anyAttendance;
	Button range;
	Spinner spinnerbranch,spinneryear,spinnerSubject,spinnersem;

	String branch = "cse";
	String year = "IY";
	String sem="sem1";
	String subject = "A1";
	String ymd="00/00/0000";

	private final String[] branchString = new String[] { "cse"};
	private final String[] yearString = new String[] {"IY","IIY","IIIY","IVY"};

	private final String[] sem1String = new String[] {"sem1","sem2"};
	private final String[] sem2String = new String[] {"sem3","sem4"};
	private final String[] sem3String = new String[] {"sem5","sem6"};
	private final String[] sem4String = new String[] {"sem7","sem8"};

	private final String[] subjectIYS1String = new String[] {"A1","B1","C1","D1","E1","F1"};
	private final String[] subjectIYS2String = new String[] {"A2","B2","C2","D2","E2","F2"};
	private final String[] subjectIIYS3String = new String[] {"A3","B3","C3","D3","E3","F3"};
	private final String[] subjectIIYS4String = new String[] {"A4","B4","C4","D4","E4","F4"};
	private final String[] subjectIIIYS5String = new String[] {"A5","B5","C5","D5","E5","F5"};
	private final String[] subjectIIIYS6String = new String[] {"A6","B6","C6","D6","E6","F6"};
	private final String[] subjectIVYS7String = new String[] {"A7","B7","C7","D7","E7","F7"};
	private final String[] subjectIVYS8String = new String[] {"A8","B8","C8","D8","E8","F8"};

	private String[] subjectFinal = new String[] {"A1","B1","C1","D1","E1","F1"};
	private String[] semFinal = new String[] {"sem1","sem2"};
	AttendanceSessionBean attendanceSessionBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_attandance);

		//Assume subject will be SE
		//subjectFinal = subjectSEString;

		spinnerbranch=(Spinner)findViewById(R.id.spinnerbranch);
		spinneryear=(Spinner)findViewById(R.id.spinneryear);
		spinnerSubject=(Spinner)findViewById(R.id.spinnersubject);
		spinnersem=(Spinner)findViewById(R.id.spinnersem);

		ArrayAdapter<String> adapter_branch = new ArrayAdapter<String>(this,R.layout.color_spinner_layout, branchString);
		adapter_branch.setDropDownViewResource(R.layout.spinner_dropdown_layout);
		spinnerbranch.setAdapter(adapter_branch);
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

		///......................spinner2
		ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this, R.layout.color_spinner_layout, yearString);
		adapter_year.setDropDownViewResource(R.layout.spinner_dropdown_layout);
		spinneryear.setAdapter(adapter_year);
		spinneryear.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				((TextView) arg0.getChildAt(0)).setTextColor(Color.RED);
				year =(String) spinneryear.getSelectedItem();
				/*Toast.makeText(getApplicationContext(), "year: "+year, Toast.LENGTH_SHORT).show();*/
                 toastmethod("Year : "+year);
				if(year.equalsIgnoreCase("IY"))
				{
					semFinal = sem1String;
					fillspinner1();
				}
				else if(year.equalsIgnoreCase("IIY"))
				{
					semFinal = sem2String;
					/*for(String a:subjectFinal) {
						System.out.println(a);
					}*/
					fillspinner1();
				}
				else if(year.equalsIgnoreCase("IIIY"))
				{
					semFinal = sem3String;
					fillspinner1();
				}
				else if(year.equalsIgnoreCase("IVY"))
				{
					semFinal = sem4String;
					fillspinner1();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		/*System.out.println("second attempt");
		for(String a:subjectFinal) {
			System.out.println(a);
		}*/




		date = (ImageButton) findViewById(R.id.DateImageButton);
		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		dyear = cal.get(Calendar.YEAR);
		dateEditText = (EditText) findViewById(R.id.DateEditText);
		date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDialog(0);

			}
		});

		submit=(Button)findViewById(R.id.buttonsubmit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();
				FacultyBean bean=((ApplicationContext)AddAttandanceSessionActivity.this.getApplicationContext()).getFacultyBean();

				attendanceSessionBean.setAttendance_session_faculty_id(bean.getFaculty_id());
				attendanceSessionBean.setAttendance_session_department(branch);
				attendanceSessionBean.setAttendance_session_class(year);
				attendanceSessionBean.setAttendance_session_sem(sem);
				attendanceSessionBean.setAttendance_session_date(dateEditText.getText().toString());
				attendanceSessionBean.setAttendance_session_subject(subject);

				DBAdapter dbAdapter = new DBAdapter(AddAttandanceSessionActivity.this);
				int sessionId=	dbAdapter.addAttendanceSession(attendanceSessionBean);

				ArrayList<StudentBean> studentBeanList=dbAdapter.getAllStudentByBranchYear(branch, year); 
				((ApplicationContext)AddAttandanceSessionActivity.this.getApplicationContext()).setStudentBeanList(studentBeanList);


				Intent intent = new Intent(AddAttandanceSessionActivity.this,AddAttendanceActivity.class);
				intent.putExtra("sessionId", sessionId);
				startActivity(intent);
			}
		});
		
		viewAttendance=(Button)findViewById(R.id.viewAttendancebutton);
		viewAttendance.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();
				FacultyBean bean=((ApplicationContext)AddAttandanceSessionActivity.this.getApplicationContext()).getFacultyBean();

				attendanceSessionBean.setAttendance_session_faculty_id(bean.getFaculty_id());
				attendanceSessionBean.setAttendance_session_department(branch);
				attendanceSessionBean.setAttendance_session_class(year);
				attendanceSessionBean.setAttendance_session_sem(sem);
				attendanceSessionBean.setAttendance_session_date(dateEditText.getText().toString());
				attendanceSessionBean.setAttendance_session_subject(subject);


				DBAdapter dbAdapter = new DBAdapter(AddAttandanceSessionActivity.this);
				
				ArrayList<AttendanceBean> attendanceBeanList = dbAdapter.getAttendanceBySessionID(attendanceSessionBean);
				((ApplicationContext)AddAttandanceSessionActivity.this.getApplicationContext()).setAttendanceBeanList(attendanceBeanList);
				
				Intent intent = new Intent(AddAttandanceSessionActivity.this,ViewAttendanceByFacultyActivity.class);
				startActivity(intent);
				
			}
		});
		
		viewTotalAttendance=(Button)findViewById(R.id.viewTotalAttendanceButton);
		viewTotalAttendance.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();
				FacultyBean bean=((ApplicationContext)AddAttandanceSessionActivity.this.getApplicationContext()).getFacultyBean();

				attendanceSessionBean.setAttendance_session_faculty_id(bean.getFaculty_id());
				attendanceSessionBean.setAttendance_session_department(branch);
				attendanceSessionBean.setAttendance_session_class(year);
				attendanceSessionBean.setAttendance_session_sem(sem);
				attendanceSessionBean.setAttendance_session_subject(subject);

				DBAdapter dbAdapter = new DBAdapter(AddAttandanceSessionActivity.this);
				
				ArrayList<AttendanceBean> attendanceBeanList = dbAdapter.getTotalAttendanceBySessionID(attendanceSessionBean);
				((ApplicationContext)AddAttandanceSessionActivity.this.getApplicationContext()).setAttendanceBeanList(attendanceBeanList);
				
				Intent intent = new Intent(AddAttandanceSessionActivity.this,ViewAttendanceByFacultyActivity.class);
				startActivity(intent);
				
			}
		});

		anyAttendance=(Button)findViewById(R.id.anyAttendanceButton);
		anyAttendance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();
				FacultyBean bean=((ApplicationContext)AddAttandanceSessionActivity.this.getApplicationContext()).getFacultyBean();

				ymd = dateEditText.getText().toString();
				/*System.out.println(branch);
				System.out.println(year);
				System.out.println(subject);
				System.out.println(ymd);*/

				DBAdapter dbAdapter = new DBAdapter(AddAttandanceSessionActivity.this);

				ArrayList<AttendanceBean> attendanceBeanList = dbAdapter.getAnyAttendance(branch,year,subject,ymd);
				((ApplicationContext)AddAttandanceSessionActivity.this.getApplicationContext()).setAttendanceBeanList(attendanceBeanList);

				Intent intent = new Intent(AddAttandanceSessionActivity.this,ViewAttendanceByFacultyActivity.class);
				startActivity(intent);

			}
		});

		range=(Button)findViewById(R.id.rangeButton);
		range.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent =new Intent(AddAttandanceSessionActivity.this,RangeActivity.class);
				intent.putExtra("branch", branch);
				intent.putExtra("year", year);
				intent.putExtra("subject", subject);

				startActivity(intent);
			}
		});




		logout=(Button)findViewById(R.id.logoutButton);
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                toastmethod("Logout Successfully");
				Intent intent =new Intent(AddAttandanceSessionActivity.this,LoginActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}

	public void fillspinner1() {
		ArrayAdapter<String> adapter_branch = new ArrayAdapter<String>(this,R.layout.color_spinner_layout, semFinal);
		adapter_branch.setDropDownViewResource(R.layout.spinner_dropdown_layout);
		spinnersem.setAdapter(adapter_branch);
		spinnersem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
									   int arg2, long arg3) {
				// TODO Auto-generated method stub
				((TextView) arg0.getChildAt(0)).setTextColor(Color.RED);
				sem = (String) spinnersem.getSelectedItem();
				toastmethod("Sem : "+sem);

				if(sem.equalsIgnoreCase("sem1"))
				{
					subjectFinal = subjectIYS1String;
					fillspinner2();
				}

				else if(sem.equalsIgnoreCase("sem2"))
				{
					subjectFinal  =  subjectIYS2String;
					fillspinner2();
				}
				else if(sem.equalsIgnoreCase("sem3"))
				{
					subjectFinal  =  subjectIIYS3String;
					fillspinner2();
				}
				else if(sem.equalsIgnoreCase("sem4"))
				{
					subjectFinal  =  subjectIIYS4String;
					fillspinner2();
				}
				else if(sem.equalsIgnoreCase("sem5"))
				{
					subjectFinal  =  subjectIIIYS5String;
					fillspinner2();
				}
				else if(sem.equalsIgnoreCase("sem6"))
				{
					subjectFinal =  subjectIIIYS6String;
					fillspinner2();
				}
				else if(sem.equalsIgnoreCase("sem7"))
				{
					subjectFinal = subjectIVYS7String;
					fillspinner2();
				}
				else if(sem.equalsIgnoreCase("sem8"))
				{
					subjectFinal = subjectIVYS8String;
					fillspinner2();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

	}

	public void fillspinner2() {
		ArrayAdapter<String> adapter_branch = new ArrayAdapter<String>(this,R.layout.color_spinner_layout, subjectFinal);
		adapter_branch.setDropDownViewResource(R.layout.spinner_dropdown_layout);
		spinnerSubject.setAdapter(adapter_branch);
		spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
									   int arg2, long arg3) {
				// TODO Auto-generated method stub
				((TextView) arg0.getChildAt(0)).setTextColor(Color.RED);
				subject = (String) spinnerSubject.getSelectedItem();
				toastmethod("Subject : "+subject);



			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

	}









	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		return new DatePickerDialog(this, datePickerListener, dyear, month, day);
	}
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			dateEditText.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
					+ selectedYear);
		}
	};

	public void toastmethod(String str)
	{
		Toast toast=new Toast(getApplicationContext());
		toast.setGravity(Gravity.BOTTOM,0,30);

		TextView tv=new TextView(AddAttandanceSessionActivity.this);
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
