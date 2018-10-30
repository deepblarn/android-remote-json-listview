package com.insbaixcamp.asyncjson;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    private final String URL_json = "http://www.insbaixcamp.org/android/versions.json";
    private final String CATEGORIA = new String("android");
    private final String SUBCATEGORIA1 = new String("ver");
    private final String SUBCATEGORIA2 = new String("name");
    private final String SUBCATEGORIA3 = new String("api");
    public ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listView=(ListView)findViewById(R.id.lvAndroid);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_file_download_black_24dp);
        fab.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
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

            JSONObject jsonObject = new JSONParser().getJsonFromUrl(URL_json);

            try {
                JSONArray android = jsonObject.getJSONArray(CATEGORIA);
                for (int i = 0; i < android.length(); i++) {
                    JSONObject jsonObjectLine = android.getJSONObject(i);

                    String versio = jsonObjectLine.getString(SUBCATEGORIA1);
                    String nom = jsonObjectLine.getString(SUBCATEGORIA2);
                    String api = jsonObjectLine.getString(SUBCATEGORIA3);

                    dataModels.add(new DataModel(nom, api, versio));
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
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle(getString(R.string.loading_title));
            dialog.setMessage(getString(R.string.loading_text));
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            adapter= new CustomAdapter(dataModels,getApplicationContext());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    DataModel dataModel = dataModels.get(position);

                    Snackbar.make(view, getString(R.string.android_name) + " " + dataModel.getName()+"\n"+dataModel.getType()+ " " + getString(R.string.version_snack) + " " +dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }
            });
        }



    }
}
