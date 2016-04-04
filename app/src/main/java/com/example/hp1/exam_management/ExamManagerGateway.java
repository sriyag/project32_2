package com.example.hp1.exam_management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by HP1 on 04-02-2016.
 */
public class ExamManagerGateway extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    String courseName ;
    String examNameSelected ;
    Button back ;
    ListView listOptions ;
    TextView headerText ;
    String options[] = {"Student List","Take Attendance","See Question-Paper","Add Students"} ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_manager_gateway);
        courseName = getIntent().getExtras().getString("nameOfCourseSelected") ;
        examNameSelected = getIntent().getExtras().getString("examNameSelected") ;

        headerText = (TextView) findViewById(R.id.headerText) ;
        listOptions = (ListView) findViewById(R.id.listViewOptions) ;
        back = (Button) findViewById(R.id.back) ;
        back.setOnClickListener(this);

        headerText.setText(courseName + "-" + examNameSelected);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
        listOptions.setAdapter(arrayAdapter);
        listOptions.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                Intent intent = new Intent(this,Exams.class) ;
                intent.putExtra("nameOfCourseSelected",courseName) ;
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position){
            case 0:break ;
            case 1:break ;
            case 2:
                //We have the course name and exam name . So we need to parse the question-paper and display it in the next class.
                Intent intent = new Intent(this,SeeQuestionPaper.class) ;
                intent.putExtra("nameOfCourseSelected",courseName) ;
                intent.putExtra("examNameSelected",examNameSelected) ;
                startActivity(intent);
                break ;
            case 3:break ;
        }
    }
}
