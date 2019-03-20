package com.example.formatdataread.xml_read;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonReader {

    public static void parseJSONEithJSONObject(String jsonData){
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            int len = jsonArray.length();
            for (int i = 0; i < len; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");

                Log.d("parseJSONEithJSONObject", "id " + id);
                Log.d("parseJSONEithJSONObject", "name " + name);
                Log.d("parseJSONEithJSONObject", "version " + version);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static  List<Object> parseJSONEithGson(String jsonData, Class<?> klazz){
        Gson gson = new Gson();
        List<Object> obs = gson.fromJson(jsonData, new TypeToken<List<klazz>>(){}.getType());
    }
}
