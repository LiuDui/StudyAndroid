package com.example.formatdataread.xml_read;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

public class XmlReader {
    public static void main(String[] args) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File("D:\\Program\\WorkSpace\\AndroidStudioProjects\\FormatDataRead\\app\\src\\main\\java\\com\\example\\formatdataread\\xml_read\\data.xml"))));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
            XmlReader.parseXMLWithPull(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void parseXMLWithPull(String xmlData){

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));

            // Returns the type of the current event (START_TAG, END_TAG, TEXT, etc.)
            int eventType = xmlPullParser.getEventType();

            String id = "";
            String name = "";
            String version = "";

            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
              //  Log.d("parseXMLWithPull", "nodeName = " + nodeName);
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if ("id".equals(nodeName)){
                            id = xmlPullParser.nextText();
                        }else if("name".equals(nodeName)){
                            name = xmlPullParser.nextText();
                        }else if("version".equals(nodeName)){
                            version = xmlPullParser.nextText();
                        }else{
                            Log.d("parseXMLWithPull", "unknow tag");
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if("app".equals(nodeName)){
                            System.out.print("id :" + id);
                            System.out.print("name :" + name);
                            System.out.print("version :" + version);
                            Log.d("parseXMLWithPull", "id : " + id);
                            Log.d("parseXMLWithPull", "name :" + name);
                            Log.d("parseXMLWithPull", "version :" + version);
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
