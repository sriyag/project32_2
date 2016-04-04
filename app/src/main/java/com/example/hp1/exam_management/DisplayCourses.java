package com.example.hp1.exam_management;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by HP1 on 10-01-2016.
 */
public class DisplayCourses extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    ListView courses ;
    Button toAddACourse,addCourse, back ;
    EditText courseNameToAdd ;
    String courseNameTyped ;
    // Button viewStudentList ;
    private ArrayList<String> allCourses = new ArrayList<>()  ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_courses);

        courses = (ListView)findViewById(R.id.courseListView) ;
        toAddACourse = (Button)findViewById(R.id.addACourse) ;
        addCourse = (Button)findViewById(R.id.addCourse) ;
        back = (Button)findViewById(R.id.back) ;
         //viewStudentList = (Button) findViewById(R.id.viewStudentList) ;
        courseNameToAdd = (EditText)findViewById(R.id.addingCourseEditText) ;
        toAddACourse.setOnClickListener(this);
        addCourse.setOnClickListener(this);
        back.setOnClickListener(this);
        courses.setOnItemClickListener(this) ;

        StringBuffer bufferReadFromFile = new StringBuffer() ;
        int read = -1 ;
        FileInputStream fileInputStream = null ;
        try {
            fileInputStream = openFileInput("courses.txt") ;
            while((read=fileInputStream.read())!=-1){
                bufferReadFromFile.append((char)read) ;
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String stringReadFromFile = bufferReadFromFile.toString() ;
        StringTokenizer stringTokenizer = new StringTokenizer(stringReadFromFile,"\n") ;
        while(stringTokenizer.hasMoreTokens()){
            allCourses.add(stringTokenizer.nextToken()) ;
        }

        if(!allCourses.isEmpty()) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allCourses);
            courses.setAdapter(arrayAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addACourse:
                 courseNameToAdd.setVisibility(View.VISIBLE);
                 addCourse.setVisibility(View.VISIBLE);
                 break;
            case R.id.addCourse:
                 courseNameTyped = courseNameToAdd.getText().toString() ;
                 if(courseNameTyped.equals("")){
                     Toast.makeText(this,"Type the Course Name",Toast.LENGTH_LONG).show();
                 }
                 else {
                     String concatString = courseNameTyped.concat("\n") ;
                     FileOutputStream fileOutputStream = null ;
                     try {
                         fileOutputStream = openFileOutput("courses.txt", Context.MODE_APPEND) ;
                         fileOutputStream.write(concatString.getBytes());
                         fileOutputStream.close();
                     } catch (FileNotFoundException e) {
                         e.printStackTrace();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                     startActivity(new Intent(this,DisplayCourses.class));
                 }
                 break;
            case R.id.back:
                 Intent intent = new Intent(this,MainActivity.class) ;
                 startActivity(intent);
                 break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView clickedView = (TextView)view ;
        String nameOfCourseSelected = clickedView.getText().toString() ;
        Intent intent = new Intent(this,Exams.class) ;
        intent.putExtra("nameOfCourseSelected", nameOfCourseSelected) ;
        startActivity(intent);
    }




}
