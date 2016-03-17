package com.example.hp1.exam_management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by HP1 on 10-01-2016.
 */
public class DataStorageT1Class extends Activity implements View.OnClickListener {
    Button students_list,take_attendance,add_students,see_paper,back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_storage_t1);
        students_list=(Button)findViewById(R.id.studentslist) ;
        add_students=(Button)findViewById(R.id.addstudents) ;
        take_attendance=(Button)findViewById(R.id.attendancetake) ;
        see_paper=(Button)findViewById(R.id.seepaper) ;
        back=(Button)findViewById(R.id.back) ;
        students_list.setOnClickListener(this);
        add_students.setOnClickListener(this);
        take_attendance.setOnClickListener(this);
        see_paper.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.studentslist){

        }
        else if(v.getId()==R.id.addstudents){

        }
        else if(v.getId()==R.id.attendancetake){

        }
        else if(v.getId()==R.id.seepaper){
            Intent intent = new Intent(this,DataStorageT1Paper.class) ;
            startActivity(intent);
        }
        else if(v.getId()==R.id.back){
            Intent intent = new Intent(this,DataStorageClass.class) ;
            startActivity(intent) ;
        }
    }
}
