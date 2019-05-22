package com.example.ww;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter{

    private LayoutInflater inflater = null;
    public ArrayList<WeatherList> weatherLists = new ArrayList<>();

    @Override
    public int getCount() {
        return weatherLists.size();
    }

    @Override
    public Object getItem(int i) {
        return weatherLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            final Context context = parent.getContext();
            if(inflater == null){
                inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.list_row,parent,false);
        }

        TextView TextPlace = convertView.findViewById(R.id.place_value);
        TextView TextWeather = convertView.findViewById(R.id.weather_value);

        TextPlace.setText(weatherLists.get(position).tempPlace);
        TextWeather.setText(weatherLists.get(position).tempWeather);

        return convertView;
    }

    public void clear() {
        this.weatherLists.clear();
    }

    public void addWeather(WeatherList weather) {
        this.weatherLists.add(weather);
        notifyDataSetChanged();
    }

    public void setList(ArrayList<WeatherList> arrays){
        this.weatherLists = arrays;
        notifyDataSetChanged();
    }
}
