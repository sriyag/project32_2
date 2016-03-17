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
 * Created by HP1 on 04-02-2016.
 */
public class Exams extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    String courseName ;
    TextView courseNameDisplay ;
    Button toAddExam,addExam,back ;
    EditText addExamBox ;
    ListView listOfExams ;
    private ArrayList<String> examsList = new ArrayList<>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exams_layout);
        Intent intent = getIntent() ;
        courseName = intent.getExtras().getString("nameOfCourseSelected") ;

        listOfExams = (ListView) findViewById(R.id.listOfExams) ;
        courseNameDisplay = (TextView) findViewById(R.id.courseNameDisplay) ;
        toAddExam = (Button)findViewById(R.id.toAddExam) ;
        addExam = (Button)findViewById(R.id.addExam) ;
        back = (Button)findViewById(R.id.back) ;
        addExamBox = (EditText)findViewById(R.id.addExamBox) ;
        toAddExam.setOnClickListener(this);
        addExam.setOnClickListener(this);
        back.setOnClickListener(this);
        listOfExams.setOnItemClickListener(this);

        courseNameDisplay.setText(courseName);

        FileInputStream fileInputStream = null ;
        StringBuffer bufferReadFromFile = new StringBuffer() ;
        int read = -1 ;
        try {
            fileInputStream = openFileInput(courseName.concat("_").concat("examslist.txt")) ;
            while((read=fileInputStream.read())!=-1)
            {
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
            examsList.add(stringTokenizer.nextToken()) ;
        }

        if(!examsList.isEmpty()){
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, examsList);
            listOfExams.setAdapter(arrayAdapter);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                Intent intent = new Intent(this,DisplayCourses.class) ;
                startActivity(intent);
                break;
            case R.id.toAddExam:
                addExamBox.setVisibility(View.VISIBLE);
                addExam.setVisibility(View.VISIBLE);
                break;
            case R.id.addExam:
                String newExamToAdd = addExamBox.getText().toString() ;
                if(newExamToAdd.equals("")){
                    Toast.makeText(this,"Type Exam Name",Toast.LENGTH_LONG).show();
                }
                else{
                    String finalEntry = newExamToAdd.concat("\n") ;
                    FileOutputStream fileOutputStream = null ;
                    try {
                        fileOutputStream = openFileOutput(courseName.concat("_").concat("examslist.txt"), Context.MODE_APPEND) ;
                        fileOutputStream.write(finalEntry.getBytes());
                        fileOutputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent refresh = new Intent(this,Exams.class) ;
                    refresh.putExtra("nameOfCourseSelected",courseName) ;
                    startActivity(refresh);
                }
                break;

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //We have to take course name and exam name to next screen i.e. ExamManagerGateway
        TextView selectedView = (TextView) view;
        String examNameSelected = selectedView.getText().toString() ;
        Intent intent = new Intent(this,ExamManagerGateway.class) ;
        intent.putExtra("nameOfCourseSelected",courseName) ;
        intent.putExtra("examNameSelected",examNameSelected) ;
        startActivity(intent);
    }
}
