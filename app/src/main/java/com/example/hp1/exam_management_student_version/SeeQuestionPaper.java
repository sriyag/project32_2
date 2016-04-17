package com.example.hp1.exam_management_student_version;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by HP1 on 15-04-2016.
 */
public class SeeQuestionPaper extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    Fragment_MCQ fragment_mcq;
    Fragment_ExplainText fragment_explainText;
    Fragment_Draw fragment_draw;
    Fragment_Label fragment_label;

    String courseName;    //CourseName
    String examNameSelected;  //ExamNameOfCourse
    String nameOfCandidate;   //name of candidate

    Button nextQuestion;  //button for next question
    Button finishExam ;   //to finish the exam

    TextView title;       //title at top
    TextView timer;
    ListView questionNumberList;   //listview
    private ArrayList<String> questionNumber = new ArrayList<>();

    int numberOfQuestions;   //total no. of questions

    String questionPaperFileName;    //file name
    String tagOfQuestion;            //tag of question

    int questionNumberSelected = 1;      // selected question

    String questionFullIfMcq;    //if tag is MCQ use these variables.
    String optionA;
    String optionB;
    String optionC;
    String optionD;

    String explainQuestion;     //if tag is EXPLAIN_TEXT use these variables
    String drawQuestion ;      //if Draw tag is used
    String labelQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_question_paper);

        nextQuestion = (Button) findViewById(R.id.next_question);
        nextQuestion.setOnClickListener(this);

        finishExam = (Button) findViewById(R.id.finish_exam);
        finishExam.setOnClickListener(this);

        courseName = getIntent().getExtras().getString("nameOfCourseSelected");
        examNameSelected = getIntent().getExtras().getString("examNameSelected");
        nameOfCandidate = getIntent().getExtras().getString("name_of_candidate");

        questionPaperFileName = courseName.concat("_").concat(examNameSelected).concat("_questionpaper");

        numberOfQuestions = findNumberOfQuestions(questionPaperFileName);   //to get number of questions
        Toast.makeText(this, "" + numberOfQuestions, Toast.LENGTH_LONG).show();

        title = (TextView) findViewById(R.id.titleTop);
        title.setText(courseName.concat("-").concat(examNameSelected));
        questionNumberList = (ListView) findViewById(R.id.questionNumberList);

        //Timer code
        timer = (TextView) findViewById(R.id.tv_Timer);

        CountDownTimer waitTimer;
        waitTimer = new CountDownTimer(3600000, 1000) {
            boolean firstTime = true;
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished/1000;
                timer.setText("Time left: " + seconds/60 + " minutes");
                if (firstTime) {
                    firstTime = false;
                    return;
                }
            }

            public void onFinish() {
                //After 180000 milliseconds finish current
                //if you would like to execute something when time finishes
                Toast.makeText(getApplicationContext(), "Time up!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SeeQuestionPaper.this, StartExam.class));
            }
        }.start();

        int x;
        for (x = 1; x <= numberOfQuestions; x++) {
            questionNumber.add("Question" + x);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, questionNumber);
        questionNumberList.setAdapter(arrayAdapter);
        questionNumberList.setOnItemClickListener(this);    //displaying ListView done.

        displayQuestion(questionNumberSelected);   //displaying the first question by-default
    }

    private int findNumberOfQuestions(String questionPaperFileName) {

        String filepath = Environment.getExternalStorageDirectory() + "/" + questionPaperFileName;
        Toast.makeText(this, filepath.toString(), Toast.LENGTH_LONG).show();
        File file = new File(filepath);
        DocumentBuilder dbuilder = null;
        try {
            DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
            dbuilder = dbfactory.newDocumentBuilder();
            Document document = dbuilder.parse(file);
            Element element = document.getDocumentElement();
            NodeList questions_list = document.getElementsByTagName("question");
            numberOfQuestions = questions_list.getLength();   //number of questions
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numberOfQuestions;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        questionNumberSelected = position + 1;
        displayQuestion(questionNumberSelected);
    }

    private void displayQuestion(int questionNumberSelected) {
        Node current_question = null;
        Node current_item = null;
        NodeList current_children_childnodes;
        String filepath = Environment.getExternalStorageDirectory() + "/" + questionPaperFileName;
        File file = new File(filepath);
        DocumentBuilder dbuilder = null;
        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
        int i, j;
        try {
            dbuilder = dbfactory.newDocumentBuilder();
            Document document = dbuilder.parse(file);
            Element element = document.getDocumentElement();
            NodeList questions_list = document.getElementsByTagName("question");  //elements with tag question
            numberOfQuestions = questions_list.getLength();   //number of questions
            for (i = 0; i < questions_list.getLength(); i++) {
                if (i == (questionNumberSelected - 1)) {
                    current_question = questions_list.item(i);
                    current_children_childnodes = current_question.getChildNodes();
                    for (j = 0; j < current_children_childnodes.getLength(); j++) {
                        current_item = current_children_childnodes.item(j);
                        if (current_item.getNodeName().equalsIgnoreCase("tag")) {
                            //Log.e("checking",current_item.getNodeName().toString()) ;
                            tagOfQuestion = current_item.getTextContent().toString();
                            break;
                        }
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (tagOfQuestion) {
            case "mcq":
                current_children_childnodes = current_question.getChildNodes();
                for (j = 0; j < current_children_childnodes.getLength(); j++) {
                    current_item = current_children_childnodes.item(j);
                    if (current_item.getNodeName().equalsIgnoreCase("text")) {
                        questionFullIfMcq = current_item.getTextContent().toString();
                    }
                    if (current_item.getNodeName().equalsIgnoreCase("optiona")) {
                        optionA = current_item.getTextContent().toString();
                    }
                    if (current_item.getNodeName().equalsIgnoreCase("optionb")) {
                        optionB = current_item.getTextContent().toString();
                    }
                    if (current_item.getNodeName().equalsIgnoreCase("optionc")) {
                        optionC = current_item.getTextContent().toString();
                    }
                    if (current_item.getNodeName().equalsIgnoreCase("optiond")) {
                        optionD = current_item.getTextContent().toString();
                    }
                }


                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("a", questionFullIfMcq);
                bundle.putString("b", optionA);
                bundle.putString("c", optionB);
                bundle.putString("d", optionC);
                bundle.putString("e", optionD);
                bundle.putString("questionnumber",questionNumberSelected+"");

                if (fragmentManager.findFragmentByTag("" + questionNumberSelected) == null) {
                    fragment_mcq = new Fragment_MCQ();
                    fragment_mcq.setArguments(bundle);
                    fragmentTransaction.replace(R.id.linearLayout, fragment_mcq, "" + questionNumberSelected).addToBackStack(null).commit();
                }
                else
                {
                    fragment_mcq = (Fragment_MCQ) fragmentManager.findFragmentByTag("" + questionNumberSelected);
                    fragmentTransaction.replace(R.id.linearLayout, fragment_mcq, "" + questionNumberSelected).addToBackStack(null).commit();
                }

                break;

            case "explain_text":
                current_children_childnodes = current_question.getChildNodes();
                for (j = 0; j < current_children_childnodes.getLength(); j++) {
                    current_item = current_children_childnodes.item(j);
                    if (current_item.getNodeName().equalsIgnoreCase("text")) {
                        explainQuestion = current_item.getTextContent().toString();
                    }
                }

                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundleForExplainText = new Bundle();
                bundleForExplainText.putString("f", explainQuestion);
                bundleForExplainText.putString("questionnumber",questionNumberSelected+"");

                if (fragmentManager.findFragmentByTag("" + questionNumberSelected) == null) {
                    fragment_explainText = new Fragment_ExplainText();
                    fragment_explainText.setArguments(bundleForExplainText);
                    fragmentTransaction.replace(R.id.linearLayout, fragment_explainText, "" + questionNumberSelected).addToBackStack(null).commit();
                }
                else
                {
                    fragment_explainText = (Fragment_ExplainText) fragmentManager.findFragmentByTag("" + questionNumberSelected);
                    fragmentTransaction.replace(R.id.linearLayout, fragment_explainText, "" + questionNumberSelected).addToBackStack(null).commit();

                }
                break;

            case "draw":
                current_children_childnodes = current_question.getChildNodes() ;
                for(j=0;j<current_children_childnodes.getLength();j++)
                {
                    current_item = current_children_childnodes.item(j) ;
                    if(current_item.getNodeName().equalsIgnoreCase("text"))
                    {
                        drawQuestion = current_item.getTextContent().toString();
                    }
                }
                fragmentManager = getFragmentManager() ;
                fragmentTransaction = fragmentManager.beginTransaction() ;
                Bundle bundleForDraw = new Bundle() ;
                bundleForDraw.putString("g", drawQuestion);

                if (fragmentManager.findFragmentByTag("" + questionNumberSelected) == null) {
                    fragment_draw = new Fragment_Draw();
                    fragment_draw.setArguments(bundleForDraw);
                    fragmentTransaction.replace(R.id.linearLayout, fragment_draw, "" + questionNumberSelected).addToBackStack(null).commit();
                }
                else
                {
                    fragment_draw = (Fragment_Draw) fragmentManager.findFragmentByTag("" +
                            questionNumberSelected);
                    fragmentTransaction.replace(R.id.linearLayout, fragment_draw, "" + questionNumberSelected).addToBackStack(null).commit();

                }
                break ;

            case "label":
                current_children_childnodes = current_question.getChildNodes() ;
                for(j=0;j<current_children_childnodes.getLength();j++)
                {
                    current_item = current_children_childnodes.item(j) ;
                    if(current_item.getNodeName().equalsIgnoreCase("text"))
                    {
                        labelQuestion = current_item.getTextContent().toString();
                    }
                }

                fragmentManager = getFragmentManager() ;
                fragmentTransaction = fragmentManager.beginTransaction() ;
                Bundle bundleForLabel = new Bundle() ;
                bundleForLabel.putString("lab", labelQuestion);

                if (fragmentManager.findFragmentByTag("" + questionNumberSelected) == null) {
                    fragment_label = new Fragment_Label();
                    fragment_label.setArguments(bundleForLabel);
                    fragmentTransaction.replace(R.id.linearLayout, fragment_label, "" + questionNumberSelected).addToBackStack(null).commit();
                }
                else
                {
                    fragment_label = (Fragment_Label) fragmentManager.findFragmentByTag("" +
                            questionNumberSelected);
                    fragmentTransaction.replace(R.id.linearLayout, fragment_label, "" + questionNumberSelected).addToBackStack(null).commit();

                }

                break ;



        } //switch closed

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_question:
                questionNumberSelected = questionNumberSelected + 1;
                if (questionNumberSelected <= numberOfQuestions)
                    displayQuestion(questionNumberSelected);
                break;

            case R.id.finish_exam:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Are you sure you want to finish the test ??");
                alertDialogBuilder.setCancelable(true) ;
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(SeeQuestionPaper.this,FinishExam.class) ;
                        intent.putExtra("nameOfCourseSelected",courseName) ;
                        intent.putExtra("examNameSelected",examNameSelected) ;
                        intent.putExtra("name_of_candidate",nameOfCandidate) ;
                        startActivity(intent);
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;

        }
    }
}
