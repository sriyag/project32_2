package com.example.hp1.exam_management_student_version;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    EditText name, id;
    Button next ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText)findViewById(R.id.etStudentName) ;
        id = (EditText)findViewById(R.id.etStudentID) ;
        next = (Button)findViewById(R.id.next_button) ;
        next.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String name_extracted = name.getText().toString() ;
        String id_extracted = id.getText().toString() ;
        if(name_extracted.length()==0)
            Toast.makeText(this,"Enter student name",Toast.LENGTH_SHORT).show();

        else if(id_extracted.length()==0)
            Toast.makeText(this,"Enter student ID",Toast.LENGTH_SHORT).show();

        if (name_extracted.length() > 0 && id_extracted.length() > 0) {
            Intent intent = new Intent(this, AvailableExams.class);
            intent.putExtra("student_name", name_extracted);
            intent.putExtra("student_id", id_extracted);
            startActivity(intent);
        }

    }
}
