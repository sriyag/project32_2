package com.example.hp1.exam_management;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by HP1 on 12-03-2016.
 */
public class Fragment_ExplainText extends Fragment {
    TextView explainQuestion ;
    EditText explainAnswer ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explain_text,container,false) ;
        explainQuestion = (TextView)view.findViewById(R.id.explain_question) ;
        explainAnswer = (EditText)view.findViewById(R.id.multilineAnswer) ;
        String f = getArguments().getString("f") ;
        explainQuestion.setText(f);
        return view ;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    public void setExplainTextData(String a)
    {
        explainQuestion.setText(a);
    }
}
