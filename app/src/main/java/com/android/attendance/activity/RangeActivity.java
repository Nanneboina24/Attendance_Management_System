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


public class RangeActivity extends Activity {

    private ImageButton fromdate;
    private ImageButton todate;
    private Calendar cal;
    private int day;
    private int month;
    private int dyear;
    private EditText dateEditText1;
    private EditText dateEditText2;
    Button submit,cancel;
    String branch,year,subject;
    String from,to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range);

        branch=getIntent().getExtras().getString("branch");
        year =getIntent().getExtras().getString("year");
        subject =getIntent().getExtras().getString("subject");

        fromdate = (ImageButton) findViewById(R.id.DateImageButton1);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        dyear = cal.get(Calendar.YEAR);
        dateEditText1 = (EditText) findViewById(R.id.DateEditText1);
        fromdate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showDialog(0);

            }
        });

        todate = (ImageButton) findViewById(R.id.DateImageButton2);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        dyear = cal.get(Calendar.YEAR);
        dateEditText2 = (EditText) findViewById(R.id.DateEditText2);
        todate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showDialog(1);

            }
        });

        submit=(Button)findViewById(R.id.submitButton);
        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                from=dateEditText1.getText().toString();
                to=dateEditText2.getText().toString();

                DBAdapter dbAdapter = new DBAdapter(RangeActivity.this);
                ArrayList<StudentBean> studentBeanList=dbAdapter.getAllStudentByBranchYear(branch, year);
                ((ApplicationContext)RangeActivity.this.getApplicationContext()).setStudentBeanList(studentBeanList);

                Intent intent =new Intent(RangeActivity.this,RangeDisplay.class);
                intent.putExtra("branch", branch);
                intent.putExtra("year", year);
                intent.putExtra("subject", subject);
                intent.putExtra("from",from);
                intent.putExtra("to",to);

                startActivity(intent);
            }
        });


        cancel=(Button)findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent =new Intent(RangeActivity.this,AddAttandanceSessionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }
    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        if(id==0)
             return new DatePickerDialog(this, datePickerListener1, dyear, month, day);
        else if(id==1)
            return new DatePickerDialog(this, datePickerListener2, dyear, month, day);
        return new DatePickerDialog(this, datePickerListener1, dyear, month, day);
    }
    private DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            dateEditText1.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListener2 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            dateEditText2.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };


}