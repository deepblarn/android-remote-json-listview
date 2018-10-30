package com.insbaixcamp.asyncjson;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

class JSONParser {
    // Constructor
    public JSONParser() {
    }

    // Mètode per retornar l'objecte JSONObject des de URL
    public JSONObject getJsonFromUrl(String urlString) {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String json = new String();
        JSONObject jsonObject = new JSONObject();

        // Fem una connexió a la URL (obligatori fer-ho amb un "try")
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            //Button bGet = (Button) findViewById(R.id.bGet);
            //bGet.setText(inputStream.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOException", "Error connexió URL" + e.toString());
        } finally {
            urlConnection.disconnect();
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            StringBuilder sb = new StringBuilder();
            String line = new String();
            //line = bufferedReader.toString();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            inputStream.close();
            json = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("Unsup.EncodingExep.", "Error llegint JSON" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOException", "Error llegint JSON" + e.toString());
        }
        // Parsegem (passem) l'String a un objecte JSONObject
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSONException", "Error creant objecte JSON" + e.toString());
        }

        return jsonObject;
    }
}