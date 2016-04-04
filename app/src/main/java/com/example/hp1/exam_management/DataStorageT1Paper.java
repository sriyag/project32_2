package com.example.hp1.exam_management;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by HP1 on 24-01-2016.
 */
public class DataStorageT1Paper extends Activity {
    String[] questions = new String[10] ;
    String[] options_a = new String[10] ;
    String[] options_b = new String[10] ;
    String[] options_c = new String[10] ;
    String[] options_d = new String[10] ;
    String[] marks = new String[10] ;
    String date_of_paper = new String() ;
    ListView listview ;
    TextView display_date ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_storage_t1_paper);
        listview=(ListView)findViewById(R.id.listofquestions) ;
        display_date=(TextView)findViewById(R.id.date) ;
        parse_question_paper() ;
        display_date.setText(date_of_paper);
        listview.setAdapter(new CustomAdapter(this));
    }

    public void parse_question_paper() {
        try {
            InputStream inputStream = getAssets().open("data storage_t1_questionpaper.xml") ;
            DocumentBuilderFactory dbfactory  = DocumentBuilderFactory.newInstance() ;
            DocumentBuilder dbuilder = dbfactory.newDocumentBuilder() ;
            Document document = dbuilder.parse(inputStream) ;
            Element element = document.getDocumentElement() ;
            //Log.e("checking",element.getNodeName()) ;
            NodeList questions_list = document.getElementsByTagName("question") ;
            Node current_question ;
            Node current_item = null;
            NodeList current_children_childnodes ;
            int i,j ;
            for(i=0;i<questions_list.getLength();i++)
            {
                current_question = questions_list.item(i) ;
                current_children_childnodes =  current_question.getChildNodes();
                //Log.e("checking",current_question.getNodeName().toString()) ;
                for(j=0;j<current_children_childnodes.getLength();j++)
                {
                   current_item = current_children_childnodes.item(j) ;
                    if(current_item.getNodeName().equalsIgnoreCase("text")){
                        //Log.e("checking",current_item.getNodeName().toString()) ;
                        questions[i] = current_item.getTextContent().toString();
                   }
                    if(current_item.getNodeName().equalsIgnoreCase("optiona")){
                        options_a[i] = current_item.getTextContent().toString();
                    }
                    if(current_item.getNodeName().equalsIgnoreCase("optionb")){
                        options_b[i] = current_item.getTextContent().toString();
                    }
                    if(current_item.getNodeName().equalsIgnoreCase("optionc")){
                        options_c[i] = current_item.getTextContent().toString();
                    }
                    if(current_item.getNodeName().equalsIgnoreCase("optiond")){
                        options_d[i] = current_item.getTextContent().toString();
                    }
                    if(current_item.getNodeName().equalsIgnoreCase("marks")){
                        marks[i] = current_item.getTextContent().toString();
                    }
                }

            }
            NodeList date_node = document.getElementsByTagName("date") ;
            date_of_paper = date_node.item(0).getTextContent() ;
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
    class CustomAdapter extends BaseAdapter{
        Context context ;
        public CustomAdapter(Context context){
            this.context = context ;
        }
        @Override
        public int getCount() {
            return questions.length ;
        }

        @Override
        public Object getItem(int position) {
            return questions[position] ;
        }

        @Override
        public long getItemId(int position) {
            return position ;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null ;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.data_storage_t1_custom_row,null) ;
            TextView questionnumber_textview = (TextView) view.findViewById(R.id.textView);
            TextView question_textview = (TextView) view.findViewById(R.id.textView2);
            TextView option_a_textview = (TextView) view.findViewById(R.id.textView3);
            TextView option_b_textview = (TextView) view.findViewById(R.id.textView4);
            TextView option_c_textview = (TextView) view.findViewById(R.id.textView5);
            TextView option_d_textview = (TextView) view.findViewById(R.id.textView6);
            questionnumber_textview.setText((position+1) + ".");
            question_textview.setText(questions[position]);
            option_a_textview.setText(options_a[position]);
            option_b_textview.setText(options_b[position]);
            option_c_textview.setText(options_c[position]);
            option_d_textview.setText(options_d[position]);
            return view ;
        }
    }
}

