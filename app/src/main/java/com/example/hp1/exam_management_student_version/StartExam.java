package com.example.hp1.exam_management_student_version;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by HP1 on 15-04-2016.
 */
public class StartExam extends Activity implements View.OnClickListener {
    String courseName ;
    String exam_Name_Selected ;
    String name_of_student ;
    Button startTest ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_exam);
        Intent intent = getIntent() ;
        courseName = intent.getExtras().getString("nameOfCourseSelected") ;
        name_of_student = intent.getExtras().getString("name_of_candidate") ;
        exam_Name_Selected = intent.getExtras().getString("examNameSelected") ;
        startTest = (Button)findViewById(R.id.start_test) ;
        startTest.setOnClickListener(this);
        Toast.makeText(this,courseName.concat(name_of_student).concat(exam_Name_Selected),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to start the test ??");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(StartExam.this,SeeQuestionPaper.class) ;
                intent.putExtra("nameOfCourseSelected",courseName) ;
                intent.putExtra("examNameSelected",exam_Name_Selected) ;
                intent.putExtra("name_of_candidate",name_of_student) ;
                startActivity(intent);
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
