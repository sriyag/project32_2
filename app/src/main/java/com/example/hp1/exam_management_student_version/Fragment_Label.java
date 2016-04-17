package com.example.hp1.exam_management_student_version;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by sriyag on 16/04/16.
 */
public class Fragment_Label extends Fragment {

    private final String imageInSD = Environment.getExternalStorageDirectory()
            + "/Android/data/com.example.sriyag" +
            ".examapp_student/Files/Img_5041" + ".png";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_label, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String labQs = getArguments().getString("lab");

        //Code for loading an image from the internal storage on to the image view
        Bitmap bitmap = BitmapFactory.decodeFile(imageInSD);
        ImageView myImageView = (ImageView) getActivity().findViewById(R.id.iv_label);
        myImageView.setImageBitmap(bitmap);

        //Code for canvas overlay on to image view



    }
}
