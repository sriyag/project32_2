package com.example.hp1.exam_management;
/**
 * Created by HP1 on 04-04-2016.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by HP1 on 11-03-2016.
 */
public class Fragment_Draw extends Fragment {

    TextView questionForDraw ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_draw, container, false) ;
        String question = getArguments().getString("g") ;
        questionForDraw = (TextView) view.findViewById(R.id.tv_drawfrag);
        questionForDraw.setText(question);
        return view ;
    }

}
