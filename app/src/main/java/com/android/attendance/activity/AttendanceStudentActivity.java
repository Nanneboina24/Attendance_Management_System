package com.android.attendance.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.attendance.bean.AttendanceBean;
import com.android.attendance.bean.FacultyBean;
import com.android.attendance.bean.StudentBean;
import com.android.attendance.context.ApplicationContext;
import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;

public class AttendanceStudentActivity extends Activity {

    ArrayList<AttendanceBean> attendanceBeanList;
    private ListView listView;
    private ArrayAdapter<String> listAdapter;

    int count=1;
    DBAdapter dbAdapter = new DBAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.__listview_main);

        listView = (ListView) findViewById(R.id.listview);
        final ArrayList<String> attendanceList = new ArrayList<String>();

        attendanceList.add("Session    | SubjectName      |  Status");

        attendanceBeanList = ((ApplicationContext) AttendanceStudentActivity.this.getApplicationContext()).getAttendanceBeanList();
        if (attendanceBeanList.size() == 0) {
            toastmethod("Record Not Found");
        } else {
            toastmethod("Record Found");
        }

        for (AttendanceBean attendanceBean : attendanceBeanList) {
            String users = "";


            if (attendanceBean.getAttendance_session_id() > 0) {
                DBAdapter dbAdapter = new DBAdapter(AttendanceStudentActivity.this);
                String subject = dbAdapter.getSubject(attendanceBean.getAttendance_session_id());
                users = count + ".                        " + subject + "                             " + attendanceBean.getAttendance_status();
            }
            count++;
            attendanceList.add(users);
            Log.d("users: ", users);

        }

        listAdapter = new ArrayAdapter<String>(this, R.layout.view_attendance_list, R.id.labelAttendance, attendanceList);
        listView.setAdapter(listAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void toastmethod(String str) {
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 30);

        TextView tv = new TextView(AttendanceStudentActivity.this);
        tv.setBackgroundColor(Color.BLACK);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(20);

        Typeface t = Typeface.create("bold", Typeface.BOLD_ITALIC);
        tv.setTypeface(t);
        tv.setPadding(10, 10, 10, 10); //LTRB
        tv.setText(str);
        toast.setView(tv);
        toast.show();
    }
}
