package com.example.ww;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Place extends AppCompatActivity implements View.OnClickListener{

    Button addplace;

    long nowIndex;
    String sort = "place";

    private DbOpenHelper mDbOpenHelper;

    private ArrayList<WeatherList> weatherLists = new ArrayList<>();
    private ListAdapter oAdapter = new ListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        addplace = findViewById(R.id.addPlace);
        addplace.setOnClickListener(this);

        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();

    }


    @Override
    protected void onResume() {
        super.onResume();

        weatherLists.clear();
        showDatabase(sort);
        try{
            for(int i = 0; i <weatherLists.size(); i++) {
               new get_weather().execute(i);
            }
        }catch (Exception e){
            e.getStackTrace();
            Log.e("????",e.getMessage());
        }

        ListView m_oWeatherList = findViewById(R.id.db_list_view);
        m_oWeatherList.setAdapter(oAdapter);
        m_oWeatherList.setOnItemClickListener(onClickListener);
        m_oWeatherList.setOnItemLongClickListener(longClickListener);
    }

    private AdapterView.OnItemClickListener onClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("On Click", "position = " + position);
            nowIndex = Long.parseLong(weatherLists.get(position).tempIndex);
            Log.e("On Click", "nowIndex = " + nowIndex);
            Log.e("On Click", "Data: " + weatherLists.get(position).tempIndex);
            String[] tempData = {weatherLists.get(position).tempPlace, weatherLists.get(position).tempWeather};
            Log.e("On Click", "Split Result = " + tempData);

            Intent intent = new Intent(getApplicationContext(), ShowingWeather.class);
            Bundle bundle = new Bundle();
            bundle.putString("lat", weatherLists.get(position).tempLat);
            bundle.putString("lon", weatherLists.get(position).tempLon);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }

    };

    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("Long Click", "position = " + position);
            nowIndex = Long.parseLong(weatherLists.get(position).tempIndex);
            String[] nowData = {weatherLists.get(position).tempPlace, weatherLists.get(position).tempWeather};
            String viewData = nowData[0] + ", " + nowData[1];
            AlertDialog.Builder dialog = new AlertDialog.Builder(Place.this);
            dialog.setTitle("데이터 삭제")
                    .setMessage("해당 데이터를 삭제 하시겠습니까?" + "\n" + viewData)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Place.this, "데이터를 삭제했습니다.", Toast.LENGTH_SHORT).show();
                            mDbOpenHelper.deleteColumn(nowIndex);
                            onResume();
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Place.this, "삭제를 취소했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .create()
                    .show();
            return true;
        }
    };

    public void showDatabase(String sort) {
        Cursor iCursor = mDbOpenHelper.sortColumn(sort);
        Log.d("showDatabase", "DB Size: " + iCursor.getCount());
        while (iCursor.moveToNext()) {
            String tempIndex = iCursor.getString(iCursor.getColumnIndex("_id"));
            String tempPlace = iCursor.getString(iCursor.getColumnIndex("place"));
            tempPlace = setTextLength(tempPlace, 10);
            String tempLatitude = iCursor.getString(iCursor.getColumnIndex("latitude"));
            String tempLongitude = iCursor.getString(iCursor.getColumnIndex("longitude"));

            String tempWeather;
            tempWeather = setTextLength("로딩중...", 10);

            WeatherList w = new WeatherList();
            w.tempIndex = tempIndex;
            w.tempPlace = tempPlace;
            w.tempLat = tempLatitude;
            w.tempLon = tempLongitude;
            w.tempWeather = tempWeather;

            weatherLists.add(w);
            oAdapter.setList(weatherLists);
            //arrayIndex.add(tempIndex);
            Log.d("날씨 받아옴???","DB읽기"+weatherLists.size());
        }

    }

    public String setTextLength(String text, int length) {
        if (text.length() < length) {
            int gap = length - text.length();
            for (int i = 0; i < gap; i++) {
                text = text + " ";
            }
        }
        return text;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.addPlace:
                Intent intent = new Intent(getApplicationContext(), PlaceSearch.class);
                startActivity(intent);
                break;
        }

    }

    private class get_weather extends AsyncTask<Integer, String, Minutely> {

        @Override
        protected Minutely doInBackground(Integer... integers) {
            int i = integers[0];
            Retrofit client = new Retrofit.Builder().baseUrl("https://api2.sktelecom.com/weather/").addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitService service = client.create(RetrofitService.class);

            Call<RetrofitClient> call = service.currentweather("2", weatherLists.get(i).tempLat, weatherLists.get(i).tempLon);
            try {
                Minutely minutely = call.execute().body().getWeather().getMinutely().get(0);
                minutely.index = i;
                return minutely;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Minutely minutely) {
            super.onPostExecute(minutely);
            Log.e("Test", minutely.getTemperature().toString());

            oAdapter.weatherLists.get(minutely.index).tempWeather =  minutely.getTemperature().tc;
            oAdapter.notifyDataSetChanged();
        }
    }

}

