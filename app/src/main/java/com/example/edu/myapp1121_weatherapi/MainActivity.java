package com.example.edu.myapp1121_weatherapi;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    public void onClick(View v) {
        WeatherDTO resultWeather = null;
        OpenWeatherAPITask task = new OpenWeatherAPITask();
        try {
            switch (v.getId()) {
                case R.id.londonButton:
                    resultWeather = task.execute("London").get();
                    break;
                case R.id.seoulButton:
                    resultWeather = task.execute("Seoul").get();
                    break;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        ((TextView)findViewById(R.id.textView)).setText(resultWeather.toString());
    }

    class OpenWeatherAPITask extends AsyncTask<String, Void, WeatherDTO> {

        @Override
        protected WeatherDTO doInBackground(String... strings) {
            WeatherDTO dto = null;
            String city = strings[0];
            String urlString = "http://api.openweathermap.org/data/2.5/weather"
                    + "?q="+city+"&appid=d3c2808867edb5c332a32b8b7e052c41";
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                byte[] buffer = new byte[1000];
                in.read(buffer);

                JSONObject json = new JSONObject(new String(buffer));
                String temp = json.getJSONObject("main").getString("temp");
                String cityname = json.getString("name");
                dto = new WeatherDTO(cityname, temp);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return dto;
         }
    }

    class WeatherDTO {
        public String city;
        public String temperature;

        public WeatherDTO(String city, String temperature) {
            this.city = city;
            this.temperature = temperature;
        }

        public String toString() {
            return "city=["+city+"], temperature=["+temperature+"]";
        }
    }
}
