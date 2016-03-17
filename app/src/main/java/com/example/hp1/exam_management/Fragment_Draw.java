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
public class Fragment_Draw extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_draw, container, false) ;

        DrawingView drawView;
        drawView = (DrawingView) getActivity().findViewById(R.id.drawing);


        String quest = getArguments().getString("question_draw") ;

        TextView question = (TextView) view.findViewById(R.id.tv_drawfrag);
        question.setText(quest);

        Toast.makeText(getActivity(), "In draw view fragment", Toast
                .LENGTH_SHORT).show();

        return view ;
    }


    public void mcqData(String a,String b,String c,String d,String e)
    {

    }
}
