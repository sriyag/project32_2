package com.example.hp1.exam_management;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by HP1 on 11-03-2016.
 */
public class Fragment_MCQ extends Fragment {
    TextView question,optionA,optionB,optionC,optionD ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mcq,container,false) ;
        String a = getArguments().getString("a") ;
        String b = getArguments().getString("b") ;
        String c = getArguments().getString("c") ;
        String d = getArguments().getString("d") ;
        String e = getArguments().getString("e") ;
        question = (TextView) view.findViewById(R.id.textView2);
        optionA = (TextView) view.findViewById(R.id.textView3);
        optionB = (TextView) view.findViewById(R.id.textView4);
        optionC = (TextView) view.findViewById(R.id.textView5);
        optionD = (TextView) view.findViewById(R.id.textView6);
        question.setText(a);
        optionA.setText(b);
        optionB.setText(c);
        optionC.setText(d);
        optionD.setText(e);
        Toast.makeText(getActivity(),"hi",Toast.LENGTH_LONG).show();
        return view ;
    }


    public void mcqData(String a,String b,String c,String d,String e)
    {

    }
}
