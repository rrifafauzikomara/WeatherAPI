package com.example.rrifafauzikomara.weatherapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView detailImage = findViewById(R.id.detail_Image);
        TextView detailDate = findViewById(R.id.detail_date);
        TextView detailTemp = findViewById(R.id.detail_temp);
        TextView detailWeather = findViewById(R.id.detail_weather);

        if (getIntent() !=null) {
            Bundle bundle = getIntent().getExtras();
            detailDate.setText(bundle.getString("date"));
            detailTemp.setText(bundle.getString("temp"));
            detailWeather.setText(bundle.getString("weather"));

         if (detailWeather.getText().toString().equals("clear")) {
             detailImage.setBackgroundResource(R.drawable.art_clear);
         }  else if (detailWeather.getText().toString().equals("clouds")) {
             detailImage.setBackgroundResource(R.drawable.art_clouds);
         } else if (detailWeather.getText().toString().equals("light_clouds")) {
             detailImage.setBackgroundResource(R.drawable.art_clouds);
         } else {
             detailImage.setBackgroundResource(R.drawable.art_light_rain);
         }

        } else {
            Toast.makeText(this, "Data tidak tersedia", Toast.LENGTH_LONG).show();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(ActivityDetail.this, MainActivity.class);
        startActivity(intent);
        return true;
    }

}
