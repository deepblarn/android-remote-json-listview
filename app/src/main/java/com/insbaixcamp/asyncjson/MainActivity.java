package com.insbaixcamp.asyncjson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    private final String URL_json = "https://api.learn2crack.com/android/jsonos";
    private final String CATEGORIA = new String("android");
    private final String SUBCATEGORIA1 = new String("ver");
    private final String SUBCATEGORIA2 = new String("name");
    private final String SUBCATEGORIA3 = new String("api");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listView=(ListView)findViewById(R.id.lvAndroid);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Testing", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        new getJSON().execute(URL_json);
    }


    private class getJSON extends AsyncTask<String, Void, String>{



        @Override
        protected String doInBackground(String... strings) {

            String url_p = strings[0];

            JSONObject jsonObject = new JSONParser().getJsonFromUrl(URL_json);

            try {
                JSONArray android = jsonObject.getJSONArray(CATEGORIA);
                for (int i = 0; i < android.length(); i++) {
                    JSONObject jsonObjectLine = android.getJSONObject(i);
                    // Desem l'item JSON en una variable
                    String versio = jsonObjectLine.getString(SUBCATEGORIA1);
                    String nom = jsonObjectLine.getString(SUBCATEGORIA2);
                    String api = jsonObjectLine.getString(SUBCATEGORIA3);
                    // Afegim la clau-valor a un objecte HashMap
                    dataModels.add(new DataModel(nom, api, versio, ""));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dataModels= new ArrayList<>();
            dataModels.clear();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter= new CustomAdapter(dataModels,getApplicationContext());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    DataModel dataModel = dataModels.get(position);

                    Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }
            });
        }



    }
}
