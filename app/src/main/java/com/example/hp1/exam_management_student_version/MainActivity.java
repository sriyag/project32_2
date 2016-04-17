package com.example.hp1.exam_management_student_version;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    EditText name ,password ;
    Button next ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText)findViewById(R.id.name) ;
        password = (EditText)findViewById(R.id.password) ;
        next = (Button)findViewById(R.id.next_button) ;
        next.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String name_extracted = name.getText().toString() ;
        String password_extracted = password.getText().toString() ;
        if(name_extracted.length()==0)
            Toast.makeText(this,"Type name",Toast.LENGTH_LONG).show();
        if(password_extracted.length()==0)
            Toast.makeText(this,"Type password",Toast.LENGTH_LONG).show();
        if( password_extracted.equals(new String("0000")) && name_extracted.length()>0)
        {
            Intent intent = new Intent(this,DisplayCourses.class) ;
            intent.putExtra("name_of_candidate",name_extracted) ;
            startActivity(intent);
        }
        else
            Toast.makeText(this,"Password is wrong",Toast.LENGTH_LONG).show();
    }
}
