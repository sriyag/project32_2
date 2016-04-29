package com.example.hp1.exam_management_student_version;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sriyag on 19/04/16.
 */
public class AvailableExams extends Activity implements AdapterView.OnItemClickListener {

    private ListView lvAvlbExams;
    private ArrayList<String> arrList;

    private Button btnChooseExam, btnPopulateLv;
    private TextView tvChosenExam;
    private static final int FILE_SELECT_CODE = 0;
    private String path;
    String course, exam;
    String student_name, student_id;
    String examName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avlb_exams);

        //Retrieve data from the previous activity
        Intent iGet = getIntent();
        student_name = iGet.getExtras().getString("student_name");
        student_id = iGet.getExtras().getString("student_id");

        lvAvlbExams = (ListView) findViewById(R.id.lvAvlbExams);
        lvAvlbExams.setOnItemClickListener(this);
        lvAvlbExams.setBackgroundColor(Color.DKGRAY);

        arrList=new ArrayList<String>();

        btnChooseExam = (Button) findViewById(R.id.btnChooseExam);
        btnPopulateLv = (Button) findViewById(R.id.btnPopulateLv);
        tvChosenExam = (TextView) findViewById(R.id.tvChosenExam);

        course = "datastorage";
        exam = "t1";

        btnChooseExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        btnPopulateLv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String path = Environment.getExternalStorageDirectory() + "/Exams/";

                //Getting list of files from a directory stored in the internal storage
                try {
                    File f = new File(path);
                    String[] files = f.list();


                    for (int i = 0; i < files.length; i++) {
                        arrList.add(files[i]);
                    }

                    Collections.sort(arrList);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "catch: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                lvAvlbExams.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout
                        .simple_list_item_1, arrList));


            }
        });

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            Toast.makeText(getApplicationContext(),
                    "Chosen Exam: " + lvAvlbExams.getItemAtPosition(position), Toast.LENGTH_SHORT)
                    .show();

            Intent i = new Intent(AvailableExams.this, StartExam.class);
            i.putExtra("nameOfCourseSelected", course);
            i.putExtra("examNameSelected", exam);
            i.putExtra("name_of_candidate", student_name);
            i.putExtra("student_id", student_id);
            startActivity(i);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select an exam file"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    tvChosenExam.setText(uri.toString());
                    examName = tvChosenExam.getText().toString();


                    // Get the file path
                    try {
                        path = getPath(this, uri);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getApplicationContext(), "File Path: " + path, Toast
                            .LENGTH_SHORT).show();
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
}
