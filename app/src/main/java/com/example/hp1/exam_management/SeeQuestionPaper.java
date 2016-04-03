package com.example.hp1.exam_management;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by HP1 on 11-03-2016.
 */
public class SeeQuestionPaper extends Activity implements AdapterView.OnItemClickListener,Communicator_MCQ,Communicator_ExplainText {


    Fragment_MCQ fragment ;
    Fragment_ExplainText frag_explainText;
    Fragment_Draw frag_draw;

    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction ;

    String courseName ;    //CourseName
    String examNameSelected ;  //ExamNameOfCourse
    TextView title ;


    ListView questionNumberList ;
    private ArrayList<String> questionNumber = new ArrayList<>() ;

    int numberOfQuestions ;
    String date ;
    String questionPaperFileName ;
    String tagOfQuestion ;

    String questionFullIfMcq ;    //if tag is MCQ use these variables.
    String optionA ;
    String optionB ;
    String optionC ;
    String optionD ;

    String explainQuestion ;     //if tag is EXPLAIN_TEXT use these variables
    String drawQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_question_paper);
        courseName = getIntent().getExtras().getString("nameOfCourseSelected") ;
        examNameSelected = getIntent().getExtras().getString("examNameSelected") ;
        title = (TextView)findViewById(R.id.titleTop) ;
        title.setText(courseName.concat("-").concat(examNameSelected));

        questionPaperFileName = courseName.concat("_").concat(examNameSelected).concat("_questionpaper.xml") ;

        questionNumberList = (ListView)findViewById(R.id.questionNumberList) ;
        numberOfQuestions = findNumberOfQuestions(questionPaperFileName);   //to get number of questions
        int x,y ;
        for(x=0;x<numberOfQuestions;x++)
        {
            y = x+1 ;
            questionNumber.add("Question" + y) ;
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, questionNumber);
        questionNumberList.setAdapter(arrayAdapter);
        questionNumberList.setOnItemClickListener(this);    //displaying ListView done.

    }

    public  int findNumberOfQuestions(String questionPaperFileName) {
        try {
            InputStream inputStream = getAssets().open(questionPaperFileName) ;
            DocumentBuilderFactory dbfactory  = DocumentBuilderFactory.newInstance() ;
            DocumentBuilder dbuilder = dbfactory.newDocumentBuilder() ;
            Document document = dbuilder.parse(inputStream) ;
            Element element = document.getDocumentElement() ;
            NodeList questions_list = document.getElementsByTagName("question") ;
            numberOfQuestions = questions_list.getLength() ;   //number of questions
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return numberOfQuestions ;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int questionNumberSelected = position+1 ;
        displayQuestion(questionNumberSelected) ;

    }

    private void displayQuestion(int questionNumberSelected) {
        //We have to find the tag of the question number.
        InputStream inputStream = null;
        Node current_question = null  ;
        Node current_item = null;
        NodeList current_children_childnodes ;
        int i,j ;
        try {
            inputStream = getAssets().open(questionPaperFileName);
            DocumentBuilderFactory dbfactory  = DocumentBuilderFactory.newInstance() ;
            DocumentBuilder dbuilder = dbfactory.newDocumentBuilder() ;
            Document document = dbuilder.parse(inputStream) ;
            Element element = document.getDocumentElement() ;
            NodeList questions_list = document.getElementsByTagName("question") ;  //elements with tag question
            numberOfQuestions = questions_list.getLength() ;   //number of questions

            for(i=0;i<questions_list.getLength();i++)
            {
                if(i==(questionNumberSelected-1))
                {
                    current_question = questions_list.item(i) ;
                    current_children_childnodes =  current_question.getChildNodes();
                    for(j=0;j<current_children_childnodes.getLength();j++)
                    {
                        current_item = current_children_childnodes.item(j) ;
                        if(current_item.getNodeName().equalsIgnoreCase("tag"))
                        {
                            //Log.e("checking",current_item.getNodeName().toString()) ;
                            tagOfQuestion = current_item.getTextContent().toString();
                            break ;
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        switch(tagOfQuestion)
        {
            case "MCQ":
                current_children_childnodes = current_question.getChildNodes() ;
                for(j=0;j<current_children_childnodes.getLength();j++)
                {
                    current_item = current_children_childnodes.item(j) ;
                    if(current_item.getNodeName().equalsIgnoreCase("text"))
                    {
                        questionFullIfMcq = current_item.getTextContent().toString();
                    }
                    if(current_item.getNodeName().equalsIgnoreCase("optiona"))
                    {
                        optionA = current_item.getTextContent().toString();
                    }
                    if(current_item.getNodeName().equalsIgnoreCase("optionb"))
                    {
                        optionB = current_item.getTextContent().toString();
                    }
                    if(current_item.getNodeName().equalsIgnoreCase("optionc"))
                    {
                        optionC = current_item.getTextContent().toString();
                    }
                    if(current_item.getNodeName().equalsIgnoreCase("optiond"))
                    {
                        optionD = current_item.getTextContent().toString();
                    }
                }



                fragmentManager = getFragmentManager() ;
                fragmentTransaction = fragmentManager.beginTransaction() ;
                Bundle bundle = new Bundle();
                bundle.putString("a",questionFullIfMcq);
                bundle.putString("b",optionA);
                bundle.putString("c",optionB);
                bundle.putString("d",optionC);
                bundle.putString("e",optionD);
                fragment = new Fragment_MCQ() ;
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.linearLayout, fragment) ;
                //fragmentTransaction.addToBackStack(null) ;
                fragmentTransaction.commit() ;
                /*if(fragment==null)
                {
                    Toast.makeText(this, "null fragment", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(this, "non-null", Toast.LENGTH_LONG).show();
                } */
                //changeDataForMCQ(questionFullIfMcq, optionA, optionB, optionC, optionD);
                break ;

            case "EXPLAIN_TEXT":
                current_children_childnodes = current_question.getChildNodes() ;
                for(j=0;j<current_children_childnodes.getLength();j++)
                {
                    current_item = current_children_childnodes.item(j) ;
                    if(current_item.getNodeName().equalsIgnoreCase("text"))
                    {
                        explainQuestion = current_item.getTextContent().toString();
                    }
                }

                try {
                    FragmentManager mManager = getFragmentManager();
                    FragmentTransaction fTrans = mManager.beginTransaction();

                    Fragment f = new Fragment_ExplainText();
//                    Fragment_ExplainText f = new Fragment_ExplainText();
                    if(f != null && f instanceof Fragment_ExplainText) {
                        Fragment_ExplainText fra = (Fragment_ExplainText) f;
                        Bundle bundle_explain = new Bundle();
                        bundle_explain.putString("qs", explainQuestion);
                        frag_explainText = new Fragment_ExplainText();
                        frag_explainText.setArguments(bundle_explain);
                        fTrans.replace(R.id.linearLayout, frag_explainText);
                        //fragmentTransaction.addToBackStack(null) ;
                        fTrans.commit();
                    }

                    else {
                        Toast.makeText(getApplicationContext(), "Null frag", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
//                changeDataForExplainText(explainQuestion);
                break ;

            case "DRAW":
                current_children_childnodes = current_question.getChildNodes() ;
                for(j=0;j<current_children_childnodes.getLength();j++)
                {
                    current_item = current_children_childnodes.item(j) ;
                    if(current_item.getNodeName().equalsIgnoreCase("text"))
                    {
                        drawQuestion = current_item.getTextContent().toString();
                    }
                }

                try {
                    FragmentManager mManager = getFragmentManager();
                    FragmentTransaction fTrans = mManager.beginTransaction();

                    Fragment f = new Fragment_Draw();

                    if(f != null && f instanceof Fragment_Draw) {
                        Fragment_Draw fra = (Fragment_Draw) f;
                        Bundle bundle_explain = new Bundle();
                        bundle_explain.putString("question_draw", drawQuestion);
                        frag_draw = new Fragment_Draw();
                        frag_draw.setArguments(bundle_explain);
                        fTrans.replace(R.id.linearLayout, frag_draw);
                        fTrans.commit();
                    }

                    else {
                        Toast.makeText(getApplicationContext(), "Null frag", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
//                changeDataForExplainText(explainQuestion);
                break ;

        }


    }

    @Override
    public void changeDataForMCQ(String a, String b, String c, String d, String e) {


        // It is coming till this method. Now I have to set the data in the fragment
        //Toast.makeText(this,a,Toast.LENGTH_LONG).show();
        //Fragment_MCQ f = findViewById(R.)
        //TextView t = (TextView) v.findViewById(R.id.textView2) ;
        //t.setText(a);
        //fragment.mcqData(a,b,c,d,e);




    }

    @Override
    public void changeDataForExplainText(String a) {


        //Fragment_ExplainText f = (Fragment_ExplainText) getFragmentManager().findFragmentById(R.id.question_fragment);
        // Fragment_ExplainText f = (Fragment_ExplainText)fragment ;
        //f.setExplainTextData(a);

    }
}