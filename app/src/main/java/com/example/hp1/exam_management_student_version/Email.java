package com.example.hp1.exam_management_student_version;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

/**
 * Created by HP1 on 17-04-2016.
 */
public class Email extends Activity implements View.OnClickListener {

    String courseName;    //CourseName
    String examNameSelected;  //ExamNameOfCourse
    String nameOfCandidate, stuID;   //name of candidate
    String zipFileName;

    EditText editTextEmail, editTextSubject, editTextMessage;
    Button btnSend;
    String email, subject, message;
    Uri URI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);

        courseName = getIntent().getExtras().getString("nameOfCourseSelected");
        examNameSelected = getIntent().getExtras().getString("examNameSelected");
        nameOfCandidate = getIntent().getExtras().getString("name_of_candidate");
        stuID = getIntent().getExtras().getString("student_id");
        zipFileName = getIntent().getExtras().getString("name_of_zipfile");
        editTextEmail = (EditText) findViewById(R.id.emailAddress);
        editTextSubject = (EditText) findViewById(R.id.subject);
        editTextMessage = (EditText) findViewById(R.id.type_message);
        btnSend = (Button) findViewById(R.id.send);
        btnSend.setOnClickListener(this);
        URI = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/" + "datastorage_t1_questionpaper"));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.send:
                email = editTextEmail.getText().toString();
                subject = editTextSubject.getText().toString();
                message = editTextMessage.getText().toString();

                if (email.length() == 0 || subject.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter email id or subject!", Toast
                            .LENGTH_SHORT).show();
                }

                else {

                    try {
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                        if (URI != null) {
                            emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
                        }
                        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
                        this.startActivity(Intent.createChooser(emailIntent, "Sending email..."));
                    } catch (Throwable t) {
                        Toast.makeText(this, "Request failed try again: " + t.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                break;

        }
    }




}

