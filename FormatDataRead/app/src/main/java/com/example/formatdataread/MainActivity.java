package com.example.formatdataread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.formatdataread.xml_read.JsonReader;
import com.example.formatdataread.xml_read.XmlReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XmlReader.parseXMLWithPull("<apps>\n" +
                "    <app>\n" +
                "        <id>1</id>\n" +
                "        <name>tom</name>\n" +
                "        <version>1.0</version>\n" +
                "    </app>\n" +
                "</apps>");

        JsonReader.parseJSONEithJSONObject("[{\"id\":\"5\", \"version\":\"2.2\", \"name\":\"hhhh\"},\n" +
                " {\"id\":\"6\", \"version\":\"2.4\", \"name\":\"heihei\"}]");
    }
}
