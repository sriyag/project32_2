package com.example.hp1.exam_management_student_version;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by sriyag on 13/04/16.
 */
public class ZipAndEmail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zipandemail);

        Button btnZip = (Button) findViewById(R.id.btnZip);
        btnZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    AddFilesWithAESEncryption a = new AddFilesWithAESEncryption();
                    Toast.makeText(getApplicationContext(), "This! " + Environment
                            .getExternalStorageDirectory(), Toast.LENGTH_SHORT).show();


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        Button btnEmail = (Button) findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    //Email code - new activity!!
                    startActivity(new Intent(ZipAndEmail.this, Email.class));



                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
