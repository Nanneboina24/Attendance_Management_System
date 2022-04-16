package com.android.attendance.activity;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import java.util.ArrayList;
import com.android.attendance.bean.AttendanceBean;
import com.android.attendance.context.ApplicationContext;
import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AnyAttendanceActivity extends Activity {
    Spinner spinnerbranch,spinneryear,spinnerSubject,spinnersem;
    Button submit,cancel;

    String branch = "cse";
    String year = "IY";
    String sem="sem1";
    String subject = "A1";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_any_attendance);

        spinnerbranch=(Spinner)findViewById(R.id.spinnerbranch);
        spinneryear=(Spinner)findViewById(R.id.spinneryear);
        spinnerSubject=(Spinner)findViewById(R.id.spinnersubject);
        spinnersem=(Spinner)findViewById(R.id.spinnersem);

        //android.R.layout.simple_spinner_item
        //android.R.layout.simple_spinner_dropdown_item

        ArrayAdapter<String> adapter_branch = new ArrayAdapter<String>(this,R.layout.color_spinner_layout, branchString);
        adapter_branch.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerbranch.setAdapter(adapter_branch);
        spinnerbranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        spinneryear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.RED);
                year =(String) spinneryear.getSelectedItem();
              /*  Toast.makeText(getApplicationContext(), "year: "+year, Toast.LENGTH_SHORT).show();*/
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

        cancel=(Button)findViewById(R.id.CancelButton);
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent =new Intent(AnyAttendanceActivity.this,MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });



        submit=(Button)findViewById(R.id.SubmitButton);
        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                DBAdapter dbAdapter = new DBAdapter(AnyAttendanceActivity.this);
                ArrayList<AttendanceBean> attendanceBeanList=dbAdapter.getAttendanceByBYSCount(branch,year,subject);
                if(attendanceBeanList.size()==0)
                {
                    toastmethod("Record Not Found");
                }
                else
                {
                   toastmethod("Record Found");
                }
                ((ApplicationContext)AnyAttendanceActivity.this.getApplicationContext()).setAttendanceBeanList(attendanceBeanList);

                Intent intent = new Intent(AnyAttendanceActivity.this,ViewAttendancePerStudentActivity.class);
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


    public void toastmethod(String str)
    {
        Toast toast=new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,30);

        TextView tv=new TextView(AnyAttendanceActivity.this);
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