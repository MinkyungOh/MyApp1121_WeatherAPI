package com.example.edu.myapp1121_weatherapi;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.londonButton)).setOnClickListener(this);
        ((Button)findViewById(R.id.seoulButton)).setOnClickListener(this);
    }

    String resultWeather = "";
    @Override
    public void onClick(View v) {
        OpenWeatherAPITask task = new OpenWeatherAPITask();
        try {
            switch (v.getId()) {
                case R.id.londonButton:
                    resultWeather = (String)task.execute("London").get();
                    break;
                case R.id.seoulButton:
                    resultWeather = (String)task.execute("Seoul").get();
                    break;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        ((TextView)findViewById(R.id.textView)).setText(resultWeather);
    }

    class OpenWeatherAPITask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            String weather = null;
            String city = (String)objects[0];
            String urlString = "http://api.openweathermap.org/data/2.5/weather"
                    + "?q="+city+"&appid=d3c2808867edb5c332a32b8b7e052c41";
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                byte[] buffer = new byte[1000];
                in.read(buffer);
                weather = new String(buffer);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return weather;
        }
    }


}
