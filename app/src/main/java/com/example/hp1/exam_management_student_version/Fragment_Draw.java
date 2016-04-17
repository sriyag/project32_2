package com.example.hp1.exam_management_student_version;
/**
 * Created by HP1 on 04-04-2016.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HP1 on 11-03-2016.
 */
public class Fragment_Draw extends Fragment implements View.OnClickListener {

    private DrawingView drawView; //canvas: DrawingView class extends View
    private LinearLayout ll;
    private ImageButton currPaint, drawBtn, eraseBtn, newBtn, saveBtn; //4 buttons
    private float smallPencil, mediumPencil, largePencil; //pencil size
    private Bitmap bitmap;
    private ImageButton c1, c2, c3;

    private final String TAG = "Fragment_Draw";


    //Working -
    private TextView questionForDraw ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Working
        return inflater.inflate(R.layout.fragment_draw, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String question = getArguments().getString("g") ;
        questionForDraw = (TextView) getActivity().findViewById(R.id.tv_drawfrag);
        questionForDraw.setText(question);

        //NEW STUFF ADDED -
        smallPencil = getResources().getInteger(R.integer.small_size);
        mediumPencil = getResources().getInteger(R.integer.medium_size);
        largePencil = getResources().getInteger(R.integer.large_size);

        drawBtn = (ImageButton) getActivity().findViewById(R.id.draw_btn);
        drawBtn.setOnClickListener(this);

        eraseBtn = (ImageButton) getActivity().findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(this);

        newBtn = (ImageButton) getActivity().findViewById(R.id.new_btn);
        newBtn.setOnClickListener(this);

        saveBtn = (ImageButton) getActivity().findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);

//        instantiate this variable by retrieving a reference to it from the layout:
        drawView = (DrawingView) getActivity().findViewById(R.id.drawing);


        LinearLayout paintLayout = (LinearLayout) getActivity().findViewById(R.id.paint_colors);
        currPaint = (ImageButton)paintLayout.getChildAt(0);
        c1 = (ImageButton) getActivity().findViewById(R.id.colour1);
        c2 = (ImageButton) getActivity().findViewById(R.id.colour2);
        c3 = (ImageButton) getActivity().findViewById(R.id.colour3);
        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);

//        use a different drawable image on the button to show that it is currently selected
        currPaint.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.paint_pressed));

    }



    @Override
    public void onClick(View view){

        if (view.getId()==R.id.colour1) {
            drawView.setErase(false);
            drawView.setBrushSize(drawView.getLastBrushSize());

            if(view!=currPaint){
                //update color, retrieve the tag we set for each button in the layout, representing the chosen color

                ImageButton imgView = (ImageButton) view;
                String color = view.getTag().toString();
                drawView.setColor(color);

//            update the UI to reflect the new chosen paint and set the previous one back to normal
                imgView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.paint_pressed));
                currPaint.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.paint));
                currPaint=(ImageButton)view;
            }
        }

        else if (view.getId()==R.id.colour2) {
            drawView.setErase(false);
            drawView.setBrushSize(drawView.getLastBrushSize());

            if(view!=currPaint){
                //update color, retrieve the tag we set for each button in the layout, representing the chosen color

                ImageButton imgView = (ImageButton) view;
                String color = view.getTag().toString();
                drawView.setColor(color);

//            update the UI to reflect the new chosen paint and set the previous one back to normal
                imgView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.paint_pressed));
                currPaint.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.paint));
                currPaint=(ImageButton)view;
            }
        }

        else if (view.getId()==R.id.colour3) {
            drawView.setErase(false);
            drawView.setBrushSize(drawView.getLastBrushSize());

            if(view!=currPaint){
                //update color, retrieve the tag we set for each button in the layout, representing the chosen color

                ImageButton imgView = (ImageButton) view;
                String color = view.getTag().toString();
                drawView.setColor(color);

//            update the UI to reflect the new chosen paint and set the previous one back to normal
                imgView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.paint_pressed));
                currPaint.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.paint));
                currPaint=(ImageButton)view;
            }
        }

        //respond to clicks
        else if(view.getId()==R.id.draw_btn){
            //draw button clicked
            final Dialog brushDialog = new Dialog(getActivity());
            brushDialog.setTitle("Pencil size:");
            brushDialog.setContentView(R.layout.pencil_size_chooser);

            ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_pencil);
            smallBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setBrushSize(smallPencil);
                    drawView.setLastBrushSize(smallPencil);
                    drawView.setErase(false);
                    brushDialog.dismiss();
                }
            });

            ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_pencil);
            mediumBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setBrushSize(mediumPencil);
                    drawView.setLastBrushSize(mediumPencil);
                    drawView.setErase(false);
                    brushDialog.dismiss();
                }
            });

            ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_pencil);
            largeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setBrushSize(largePencil);
                    drawView.setLastBrushSize(largePencil);
                    drawView.setErase(false);
                    brushDialog.dismiss();
                }
            });

            brushDialog.show();
        }

        else if(view.getId()==R.id.erase_btn){
            //switch to erase - choose size
            final Dialog brushDialog = new Dialog(getActivity());
            brushDialog.setTitle("Eraser size:");
            brushDialog.setContentView(R.layout.pencil_size_chooser);

            ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_pencil);
            smallBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(5);
                    brushDialog.dismiss();
                }
            });
            ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_pencil);
            mediumBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(10);
                    brushDialog.dismiss();
                }
            });
            ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_pencil);
            largeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(20);
                    brushDialog.dismiss();
                }
            });

            brushDialog.show();
        }

        else if(view.getId()==R.id.new_btn){
            //new button
            AlertDialog.Builder newDialog = new AlertDialog.Builder(getActivity());
            newDialog.setTitle("New drawing");
            newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
            newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    drawView.startNew();
                    drawView.buildDrawingCache();
                    drawView.bringToFront();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            newDialog.show();
        }

        else if (view.getId() == R.id.save_btn) {

            //save drawing
            AlertDialog.Builder saveDialog = new AlertDialog.Builder(getActivity());
            saveDialog.setTitle("Save drawing");
            saveDialog.setMessage("Save drawing to device local file?");

            saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //save drawing
//                enable the drawing cache on the custom View
                    drawView.setDrawingCacheEnabled(true);

                    try {
                        String imgSaved = MediaStore.Images.Media.insertImage(
                                getActivity().getContentResolver(), drawView.getDrawingCache(),
                                "Img" + ".png", "drawing");


//                    bitmap = getBitmapOfView(drawView);
                        bitmap = drawView.getDrawingCache();
                        Toast.makeText(getActivity(), "Drawing saved locally with name: " +
                                imgSaved, Toast
                                .LENGTH_LONG).show();
                        //NOW SAVE THIS IMAGE IN FILE STORAGE
                        storeImage(bitmap);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "catch msg: " + e.getMessage(), Toast
                                .LENGTH_LONG)
                                .show();
                    }

                    //ERROR MSG (TOAST) - UNABLE TO CREATE NEW FILE -
                    // /storage/emulated/0/DCIM/Camera/14607031106167.jpg

                    drawView.destroyDrawingCache();
                }
            });
            saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            saveDialog.show();
        }

    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Toast.makeText(getActivity(), "Error creating media file, check storage permissions: ", Toast
                    .LENGTH_SHORT).show();

            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(getActivity(), "File not found " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Error accessing file: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    //    * Create a File for saving an image or video
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getActivity().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Toast.makeText(getActivity(), "NOT SAVED!!", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("mmss").format(new Date());
        File mediaFile;
        String mImageName="Img_"+ timeStamp + ".png";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        Toast.makeText(getActivity(), mediaStorageDir.getPath() + File.separator + mImageName,
                Toast.LENGTH_LONG).show();
        return mediaFile;
    }
}
