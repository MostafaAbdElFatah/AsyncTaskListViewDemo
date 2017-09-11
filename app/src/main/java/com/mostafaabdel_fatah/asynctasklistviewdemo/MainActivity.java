package com.mostafaabdel_fatah.asynctasklistviewdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String[] langauges;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        langauges = getResources().getStringArray(R.array.languages);
        listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new ArrayList<String>());
        listView.setAdapter(arrayAdapter);
        new MyTask().execute();
    }

    public class MyTask extends AsyncTask<Void,String,String>{

        ArrayAdapter<String> arrayAdapter;
        ProgressBar progressBar;
        int count=0;

        @Override
        protected void onPreExecute() {
            this.arrayAdapter = (ArrayAdapter<String>) listView.getAdapter();
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setMax(langauges.length);
            progressBar.setProgress(0);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {

            for (String language : langauges){
                publishProgress(language);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "All Languages Added to List Successfully...";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            arrayAdapter.add(values[0]);
            progressBar.setProgress(++count);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }
}
