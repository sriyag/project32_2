package com.example.hp1.exam_management_student_version;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;

/**
 * Created by HP1 on 17-04-2016.
 */
public class FinishExam extends Activity implements View.OnClickListener {
    Button zipAndEmail ;

    String courseName;    //CourseName
    String examNameSelected;  //ExamNameOfCourse
    String nameOfCandidate;   //name of candidate
    String zipFileName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish_exam);

        courseName = getIntent().getExtras().getString("nameOfCourseSelected");
        examNameSelected = getIntent().getExtras().getString("examNameSelected");
        nameOfCandidate = getIntent().getExtras().getString("name_of_candidate");

        zipAndEmail = (Button)findViewById(R.id.zip_email) ;
        zipAndEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //creating a encrypted-zip of the xml file and going to the email-screen.
        AddFilesWithAESEncryption addFilesWithAESEncryption = new AddFilesWithAESEncryption() ;
        //Starting the email activity
        Intent emailIntent = new Intent(this,Email.class) ;
        emailIntent.putExtra("nameOfCourseSelected", courseName) ;
        emailIntent.putExtra("examNameSelected",examNameSelected) ;
        emailIntent.putExtra("name_of_candidate",nameOfCandidate) ;
        emailIntent.putExtra("name_of_zipfile",zipFileName) ;
        startActivity(emailIntent);

    }

    class AddFilesWithAESEncryption {

        public AddFilesWithAESEncryption()
        {
            String filePath = Environment.getExternalStorageDirectory() + "/" + courseName + "_" + examNameSelected + "_" + "questionpaper" ;
            zipFileName = Environment.getExternalStorageDirectory() + "/" + courseName + "_" + examNameSelected + "_" + "answer.zip" ;
            File file = new File(filePath) ;
            if(file.exists())
            {
                try {
                    ZipFile zipFile = new ZipFile(zipFileName);
                    ZipParameters parameters = new ZipParameters();
                    parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression
                    parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
                    parameters.setEncryptFiles(true);
                    parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
                    parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
                    parameters.setPassword("test123!");
                    zipFile.addFile(file, parameters);
                } catch (ZipException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
