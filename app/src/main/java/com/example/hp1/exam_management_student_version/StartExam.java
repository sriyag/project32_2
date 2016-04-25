package com.example.hp1.exam_management_student_version;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by HP1 on 15-04-2016.
 */
public class StartExam extends Activity implements View.OnClickListener {
    String courseName;
    String exam_Name_Selected;
    String name_of_student, student_id;
    Button startTest;
    String password;
    String pass = "a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_exam);
        Intent intent = getIntent();
        courseName = intent.getExtras().getString("nameOfCourseSelected");
        name_of_student = intent.getExtras().getString("name_of_candidate");
        student_id = intent.getExtras().getString("student_id");
        exam_Name_Selected = intent.getExtras().getString("examNameSelected");
        startTest = (Button) findViewById(R.id.start_test);
        startTest.setOnClickListener(this);
        Toast.makeText(this, courseName.concat(name_of_student).concat(exam_Name_Selected), Toast
                .LENGTH_SHORT).show();
    }

    @Override
    public void onClick(final View v) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(StartExam.this);
        alertDialog.setTitle("PASSWORD");
        alertDialog.setMessage("Enter Password");

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        final EditText input = new EditText(StartExam.this);
        input.setTransformationMethod(new PasswordTransformationMethod());
        input.requestFocus();

        InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(v.getWindowToken(), InputMethodManager
                .SHOW_FORCED, 0);


        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        password = input.getText().toString();
                        if (pass.equals(password)) {
                            Toast.makeText(getApplicationContext(),
                                    "Password Matched", Toast.LENGTH_SHORT).show();

                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                            );

                            InputMethodManager imm2 = (InputMethodManager) getSystemService
                                    (Activity.INPUT_METHOD_SERVICE);
                            imm2.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                            Intent intent = new Intent(StartExam.this, SeeQuestionPaper.class);
                            intent.putExtra("nameOfCourseSelected", courseName);
                            intent.putExtra("examNameSelected", exam_Name_Selected);
                            intent.putExtra("name_of_candidate", name_of_student);
                            intent.putExtra("student_id", student_id);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Wrong Password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
}
