package com.example.rrifafauzikomara.weatherapi;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rrifafauzikomara.weatherapi.Adapter.CuacaAdapter;
import com.example.rrifafauzikomara.weatherapi.Entity.Cuaca;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refresh;
    private ListView list;
    private AlertDialog.Builder alert;
    private List<Cuaca> cuaca;
    private String JSON_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Bandung&APPID=2939b4f9a70e7dd25e181b06ab14bc5d&mode=json&units=metric&cnt=17";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refresh = findViewById(R.id.sr1);
        list = findViewById(R.id.list1);
        alert = new AlertDialog.Builder(this);
        cuaca = new ArrayList<>();

        refresh.setOnRefreshListener(MainActivity.this);
        refresh.post(new Runnable() {
            @Override
            public void run() {
               refresh.setRefreshing(true);
               cuaca.clear();
               RequestParams params = new RequestParams();
               requestWeather(params);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ActivityDetail.class);
                Bundle bundle = new Bundle();
                bundle.putString("date", cuaca.get(i).getDate());
                bundle.putString("temp", cuaca.get(i).getTemp());
                bundle.putString("weather", cuaca.get(i).getWeather());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    public void requestWeather(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        refresh.setRefreshing(true);
        client.get(JSON_URL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                refresh.setRefreshing(false);
                Log.d("LOG", "Response : " + response);
                try {
                    JSONObject weather = new JSONObject(response);
                    JSONArray listWeather = weather.getJSONArray("list");
                    for (int i = 0; i < listWeather.length(); i++) {
                        JSONObject weatherData = listWeather.getJSONObject(i);
                        String dt = convertDateTime(weatherData.getLong("dt"));
                        JSONObject temp = weatherData.getJSONObject("temp");
                        String t = String.valueOf(temp.getInt("day")) + " C";
                        JSONArray main = weatherData.getJSONArray("weather");
                        String w = main.getJSONObject(0).getString("main");
                        cuaca.add(new Cuaca(dt,t, w));
                    }
                    CuacaAdapter adapter = new CuacaAdapter(MainActivity.this, cuaca);
                    list.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                    alert.setTitle("Terjadi Kesalahan");
                    alert.setMessage("Request Time Out");
                    alert.show();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                refresh.setRefreshing(false);
                if (statusCode == 404) {
                    alert.setTitle("Terjadi Kesalahan");
                    alert.setMessage("404 Not Found");
                    alert.show();
                } else if (statusCode == 500 ) {
                    alert.setTitle("Terjadi Kesalahan");
                    alert.setMessage("500 Internal Server Error");
                    alert.show();
                } else {
                    alert.setTitle("Terjadi Kesalahan");
                    alert.setMessage("No Internet Connection");
                    alert.show();
                }
            }
        });
    }

    public String convertDateTime (long dateTime) {
        Date date = new Date (dateTime * 1000 );
        Format dateTimeFormat = new SimpleDateFormat("EEE, dd MMM");
        return dateTimeFormat.format(date);
    }

    @Override
    public void onRefresh() {
        refresh.setRefreshing(true);
        cuaca.clear();
        RequestParams params = new RequestParams();
        requestWeather(params);
    }
}
