package com.example.hp1.exam_management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by HP1 on 10-01-2016.
 */
public class DataStorageClass extends Activity implements View.OnClickListener {
    Button test1 , test2 , compre , back ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_storage);
        test1 = (Button)findViewById(R.id.test1) ;
        test2 = (Button)findViewById(R.id.test2) ;
        compre = (Button)findViewById(R.id.compre) ;
        back = (Button)findViewById(R.id.back) ;
        test1.setOnClickListener(this);
        test2.setOnClickListener(this);
        compre.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.test1){
            Intent intent = new Intent(this,DataStorageT1Class.class) ;
            startActivity(intent);
        }
        else if(v.getId()==R.id.back){
            Intent intent = new Intent(this,DisplayCourses.class) ;
            startActivity(intent);
        }
    }
}
