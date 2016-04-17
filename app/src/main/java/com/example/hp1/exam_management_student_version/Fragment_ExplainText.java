package com.example.hp1.exam_management_student_version;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by HP1 on 12-03-2016.
 */
public class Fragment_ExplainText extends Fragment implements View.OnClickListener {
    TextView explainQuestion;
    EditText explainAnswer;
    TextView question_number;
    Button saveButton;
    String qn;
    String typedAnswer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explain_text, container, false);
        explainQuestion = (TextView) view.findViewById(R.id.explain_question);
        explainAnswer = (EditText) view.findViewById(R.id.multilineAnswer);
        question_number = (TextView) view.findViewById(R.id.question_number);
        saveButton = (Button) view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
        String f = getArguments().getString("f");
        qn = getArguments().getString("questionnumber");
        explainQuestion.setText(f);
        question_number.setText(qn);
        return view;
    }


    public void setExplainTextData(String a) {
        explainQuestion.setText(a);
    }

    @Override
    public void onClick(View v) {
        typedAnswer = explainAnswer.getText().toString();
        String filepath = Environment.getExternalStorageDirectory() + "/" + "datastorage_t1_questionpaper";
        Toast.makeText(getActivity(), filepath.toString(), Toast.LENGTH_LONG).show();
        File file = new File(filepath);
        DocumentBuilder dbuilder = null;
        try {
            DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
            dbuilder = dbfactory.newDocumentBuilder();
            Document document = dbuilder.parse(file);
            Element element = document.getDocumentElement();
            NodeList questions_list = document.getElementsByTagName("question");   //tags with question
            Node particular_question = questions_list.item(Integer.parseInt(qn) - 1);
            Toast.makeText(getActivity(), qn, Toast.LENGTH_LONG).show();
            NodeList attributes = particular_question.getChildNodes();
            for (int i = 0; i < attributes.getLength(); i++) {
                Node temp = attributes.item(i);
                if (temp.getNodeName().equalsIgnoreCase("answer")) {
                    if (typedAnswer != null && typedAnswer.length() > 0) {
                        temp.setTextContent(typedAnswer);
                        Toast.makeText(getActivity(), "writing" + typedAnswer, Toast.LENGTH_LONG).show();
                    }
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}


