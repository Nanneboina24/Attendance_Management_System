package com.android.attendance.activity;


import android.app.Activity;
import android.os.Bundle;
import com.example.androidattendancesystem.R;
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

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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

public class StudentLoginActivity extends Activity {

    private ImageButton date;
    private Calendar cal;
    private int day;
    private int month;
    private int dyear;
    private EditText dateEditText;
    Button viewAttendance,logout,viewtotalAttendance;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

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

        viewAttendance=(Button)findViewById(R.id.viewButton);
        viewAttendance.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                StudentBean studentBean=((ApplicationContext)StudentLoginActivity.this.getApplicationContext()).getStudentBean();
                String dat=dateEditText.getText().toString();

                DBAdapter dbAdapter = new DBAdapter(StudentLoginActivity.this);
                ArrayList<AttendanceBean> attendanceBeanList=dbAdapter.getDayStatus(studentBean,dat);
                ((ApplicationContext)StudentLoginActivity.this.getApplicationContext()).setAttendanceBeanList(attendanceBeanList);

                Intent intent = new Intent(StudentLoginActivity.this,AttendanceStudentActivity.class);
                startActivity(intent);

            }
        });

        viewtotalAttendance=(Button)findViewById(R.id.viewtotalButton);
        viewtotalAttendance.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                StudentBean studentBean=((ApplicationContext)StudentLoginActivity.this.getApplicationContext()).getStudentBean();


                DBAdapter dbAdapter = new DBAdapter(StudentLoginActivity.this);
                ArrayList<AttendanceBean> attendanceBeanList=dbAdapter.getSubjectNames(studentBean);
                ((ApplicationContext)StudentLoginActivity.this.getApplicationContext()).setAttendanceBeanList(attendanceBeanList);

                Intent intent = new Intent(StudentLoginActivity.this,TotalAttendanceStudentActivity.class);
                startActivity(intent);

            }
        });








        logout=(Button)findViewById(R.id.logoutButton);
        logout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                toastmethod("Logout Successfully");
                Intent intent =new Intent(StudentLoginActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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

        TextView tv=new TextView(StudentLoginActivity.this);
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