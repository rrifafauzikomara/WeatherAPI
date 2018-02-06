package com.example.rrifafauzikomara.weatherapi.Adapter;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rrifafauzikomara.weatherapi.Entity.Cuaca;
import com.example.rrifafauzikomara.weatherapi.R;

import java.util.List;

/**
 * Created by R Rifa Fauzi Komara on 04/02/2018.
 */

public class CuacaAdapter extends ArrayAdapter<Cuaca> {

    private final AppCompatActivity context;
    private final List<Cuaca> cuaca;

    public CuacaAdapter (AppCompatActivity context, List<Cuaca> cuaca) {
        super(context, R.layout.items, cuaca);
        this.context = context;
        this.cuaca = cuaca;
    }

    public View getView (int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.items, null, true);
        TextView dateText = rowView.findViewById(R.id.txt1);
        TextView tempText = rowView.findViewById(R.id.txt2);
        TextView weatherText = rowView.findViewById(R.id.txt3);

        dateText.setText(cuaca.get(position).getDate());
        tempText.setText(cuaca.get(position).getTemp());
        weatherText.setText(cuaca.get(position).getWeather());

        return rowView;
    }

}