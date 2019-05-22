package com.example.ww;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowingWeather extends AppCompatActivity {

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    private GpsTracker gpsTracker;

    CoordinatorLayout layout;
    Glide glide;

    TextView tv_updateTime;
    TextView tv_currentLocation;
    TextView tv_currentTemp;
    TextView tv_minmaxTemp;
    TextView tv_fineDust;
    TextView tv_superFineDust;
    TextView shortWeatherTime1;
    TextView shortWeatherTime2;
    TextView shortWeatherTime3;
    TextView shortWeatherTime4;
    TextView shortWeatherTime5;
    TextView shortWeatherTime6;
    TextView shortWeatherTime7;
    TextView shortWeatherTime8;
    TextView shortWeatherTime9;

    ImageView skyImage[];

    TextView shortWeatherTemp1;
    TextView shortWeatherTemp2;
    TextView shortWeatherTemp3;
    TextView shortWeatherTemp4;
    TextView shortWeatherTemp5;
    TextView shortWeatherTemp6;
    TextView shortWeatherTemp7;
    TextView shortWeatherTemp8;
    TextView shortWeatherTemp9;

    TextView longWeatherSky;
    TextView longWeatherTemp;

    String currentLocation;
    String hour_temperature;
    String hour_sky;
    String hour_time;

    double lat;
    double lon;

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("MM/dd hh:mm");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SlidingView sv = new SlidingView(this);
        View v1 = View.inflate(this, R.layout.activity_showing_weahter, null);
        View v2 = View.inflate(this, R.layout.activity_look_book, null);
        sv.addView(v1);
        sv.addView(v2);
        setContentView(sv);

        layout = findViewById(R.id.showingWeather);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

        tv_updateTime = findViewById(R.id.updateTime);
        tv_currentLocation = findViewById(R.id.curLoc);
        tv_currentTemp = findViewById(R.id.currentWeather);
        tv_minmaxTemp = findViewById(R.id.dailyMaxMin);
        tv_fineDust = findViewById(R.id.fineDust);
        tv_superFineDust = findViewById(R.id.superFineDust);

        shortWeatherTime1 = findViewById(R.id.shortWeatherTime1);
        shortWeatherTime2 = findViewById(R.id.shortWeatherTime2);
        shortWeatherTime3 = findViewById(R.id.shortWeatherTime3);
        shortWeatherTime4 = findViewById(R.id.shortWeatherTime4);
        shortWeatherTime5 = findViewById(R.id.shortWeatherTime5);
        shortWeatherTime6 = findViewById(R.id.shortWeatherTime6);
        shortWeatherTime7 = findViewById(R.id.shortWeatherTime7);
        shortWeatherTime8 = findViewById(R.id.shortWeatherTime8);
        shortWeatherTime9 = findViewById(R.id.shortWeatherTime9);

        skyImage = new ImageView[]{findViewById(R.id.shortWeatherStatus1), findViewById(R.id.shortWeatherStatus2),
                findViewById(R.id.shortWeatherStatus3), findViewById(R.id.shortWeatherStatus4),
                findViewById(R.id.shortWeatherStatus5), findViewById(R.id.shortWeatherStatus6),
                findViewById(R.id.shortWeatherStatus7), findViewById(R.id.shortWeatherStatus8),
                findViewById(R.id.shortWeatherStatus9)};

        shortWeatherTemp1 = findViewById(R.id.shortWeatherTemp1);
        shortWeatherTemp2 = findViewById(R.id.shortWeatherTemp2);
        shortWeatherTemp3 = findViewById(R.id.shortWeatherTemp3);
        shortWeatherTemp4 = findViewById(R.id.shortWeatherTemp4);
        shortWeatherTemp5 = findViewById(R.id.shortWeatherTemp5);
        shortWeatherTemp6 = findViewById(R.id.shortWeatherTemp6);
        shortWeatherTemp7 = findViewById(R.id.shortWeatherTemp7);
        shortWeatherTemp8 = findViewById(R.id.shortWeatherTemp8);
        shortWeatherTemp9 = findViewById(R.id.shortWeatherTemp9);

        longWeatherSky = findViewById(R.id.longWeatherSky);
        longWeatherTemp = findViewById(R.id.longWeatherTemp);

        tv_updateTime.setText(getTime());
        if (!isPermission)
            callPermission();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        lat = Double.valueOf(bundle.getString("lat"));
        lon = Double.valueOf(bundle.getString("lon"));

        currentLocation = getCurrentAddress(lat, lon);
        Log.d("위치 받아옴?", currentLocation);
        tv_currentLocation.setText(currentLocation);
        new ReceiveCurrentWeather().execute();
        new ReceiveShortWeather().execute();
        new ReceiveWeekWeather().execute();
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isAccessFineLocation = true;
        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isAccessCoarseLocation = true;
        }
        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    public String getCurrentAddress(double lat, double lon) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_SHORT).show();
            return "주소 미발견";
        } else {
            // GPS 를 사용할수 없으므로
            // gpsTracker.showSettingsAlert();
        }
        callPermission();  // 권한 요청을 해야 함

        Address address = addresses.get(0);
        return address.getAddressLine(0) + "\n";
    }

    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_ACCESS_COARSE_LOCATION);

        } else {
            isPermission = true;
        }
    }

    public class ReceiveCurrentWeather extends AsyncTask<URL, Integer, Long> {
        String currentTemp;
        String dailyMax;
        String dailyMin;
        String currentSky;

        @Override
        protected Long doInBackground(URL... urls) {
            Retrofit client = new Retrofit.Builder().baseUrl("https://api2.sktelecom.com/weather/").addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitService service = client.create(RetrofitService.class);

            Call<RetrofitClient> call = service.currentweather("2", String.valueOf(lat), String.valueOf(lon));
            try {

                Minutely minutely = call.execute().body().getWeather().getMinutely().get(0);
                currentSky = minutely.getSky().getName();
                currentTemp = minutely.getTemperature().tc;
                dailyMax = minutely.getTemperature().tmax;
                dailyMin = minutely.getTemperature().tmin;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {
           /* Glide.with(ShowingWeather.this)
                    .load(R.raw.background_night)
                    .asGif()
                    .into(new SimpleTarget<GifDrawable>() {
                        @Override
                        public void onResourceReady(GifDrawable resource, GlideAnimation<? super GifDrawable> glideAnimation) {
                            layout.setBackgroundResource(R.raw.background_cloud);
                        }
                    });*/
            tv_currentTemp.setText(currentTemp + "°C");
            tv_minmaxTemp.setText(String.format("%s °C/%s °C", dailyMax, dailyMin));
        }
    }

    public class ReceiveShortWeather extends AsyncTask<URL, Integer, Long> {

        Forecast3days forecast3days;
        String temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9;
        String sky[];
        String timeRelease;

        protected Long doInBackground(URL... urls) {

            Retrofit client = new Retrofit.Builder().baseUrl("https://api2.sktelecom.com/weather/").addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitService service = client.create(RetrofitService.class);

            Call<RetrofitClient> call = service.shortWeather("2", String.valueOf(lat), String.valueOf(lon));
            Log.d("뭘 보냄??", call.request().toString());
            Log.d("받아옴123?", lat + " " + lon);

            try {
                forecast3days = call.execute().body().getWeather().getForecast3days().get(0);
                timeRelease = forecast3days.getTimeRelease();
                Log.d("시간시간", timeRelease);
                temp1 = forecast3days.getFcst3hour().getTemperature().getT4();
                temp2 = forecast3days.getFcst3hour().getTemperature().getT7();
                temp3 = forecast3days.getFcst3hour().getTemperature().getT10();
                temp4 = forecast3days.getFcst3hour().getTemperature().getT13();
                temp5 = forecast3days.getFcst3hour().getTemperature().getT16();
                temp6 = forecast3days.getFcst3hour().getTemperature().getT19();
                temp7 = forecast3days.getFcst3hour().getTemperature().getT22();
                temp8 = forecast3days.getFcst3hour().getTemperature().getT25();
                temp9 = forecast3days.getFcst3hour().getTemperature().getT28();
                       /*+ " " + forecast3days.getFcst3hour().getTemperature().getT31()
                        + " " + forecast3days.getFcst3hour().getTemperature().getT34()
                        + " " + forecast3days.getFcst3hour().getTemperature().getT37()
                        + " " + forecast3days.getFcst3hour().getTemperature().getT40()
                        + " " + forecast3days.getFcst3hour().getTemperature().getT43()
                        + " " + forecast3days.getFcst3hour().getTemperature().getT46()
                        + " " + forecast3days.getFcst3hour().getTemperature().getT49()
                        + " " + forecast3days.getFcst3hour().getTemperature().getT52()
                        + " " + forecast3days.getFcst3hour().getTemperature().getT55()
                        + " " + forecast3days.getFcst3hour().getTemperature().getT58()
                        + " " + forecast3days.getFcst3hour().getTemperature().getT61()
                        + " " + forecast3days.getFcst3hour().getTemperature().getT64()
                        + " " + forecast3days.getFcst3hour().getTemperature().getT67()*/

                sky = new String[]{forecast3days.getFcst3hour().getSky().getN4(), forecast3days.getFcst3hour().getSky().getN7(), forecast3days.getFcst3hour().getSky().getN10()
                        , forecast3days.getFcst3hour().getSky().getN13(), forecast3days.getFcst3hour().getSky().getN16(), forecast3days.getFcst3hour().getSky().getN19()
                        , forecast3days.getFcst3hour().getSky().getN22(), forecast3days.getFcst3hour().getSky().getN25(), forecast3days.getFcst3hour().getSky().getN28()};

                /* sky2 = forecast3days.getFcst3hour().getSky().getN7();
                sky3 = forecast3days.getFcst3hour().getSky().getN10();
                sky4 = forecast3days.getFcst3hour().getSky().getN13();
                sky5 = forecast3days.getFcst3hour().getSky().getN16();
                sky6 = forecast3days.getFcst3hour().getSky().getN19();
                sky7 = forecast3days.getFcst3hour().getSky().getN22();
                sky8 = forecast3days.getFcst3hour().getSky().getN25();
                sky9 = forecast3days.getFcst3hour().getSky().getN28();*/
                        /*+ " " + forecast3days.getFcst3hour().getSky().getN31()
                        + " " + forecast3days.getFcst3hour().getSky().getN34()
                        + " " + forecast3days.getFcst3hour().getSky().getN37()
                        + " " + forecast3days.getFcst3hour().getSky().getN40()
                        + " " + forecast3days.getFcst3hour().getSky().getN43()
                        + " " + forecast3days.getFcst3hour().getSky().getN46()
                        + " " + forecast3days.getFcst3hour().getSky().getN49()
                        + " " + forecast3days.getFcst3hour().getSky().getN52()
                        + " " + forecast3days.getFcst3hour().getSky().getN55()
                        + " " + forecast3days.getFcst3hour().getSky().getN58()
                        + " " + forecast3days.getFcst3hour().getSky().getN61()
                        + " " + forecast3days.getFcst3hour().getSky().getN64()
                        + " " + forecast3days.getFcst3hour().getSky().getN67()*/
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Long result) {
            int time = Integer.valueOf(timeRelease.substring(11, 13));
            String[] t = new String[9];
            time = time + 4;
            for (int i = 0; i < 9; i++) {

                if (((time / 12) % 2) == 0) {
                    if (time % 12 == 0)
                        t[i] = "오전 12시";
                    else
                        t[i] = "오전 " + (time % 12) + "시";
                } else {
                    if (time % 12 == 0)
                        t[i] = "오후 12시";
                    else

                        t[i] = "오후 " + (time % 12) + "시";
                }
                time = time + 3;
            }


            shortWeatherTime1.setText(t[0]);
            shortWeatherTime2.setText(t[1]);
            shortWeatherTime3.setText(t[2]);
            shortWeatherTime4.setText(t[3]);
            shortWeatherTime5.setText(t[4]);
            shortWeatherTime6.setText(t[5]);
            shortWeatherTime7.setText(t[6]);
            shortWeatherTime8.setText(t[7]);
            shortWeatherTime9.setText(t[8]);


            shortWeatherTemp1.setText(temp1);
            shortWeatherTemp2.setText(temp2);
            shortWeatherTemp3.setText(temp3);
            shortWeatherTemp4.setText(temp4);
            shortWeatherTemp5.setText(temp5);
            shortWeatherTemp6.setText(temp6);
            shortWeatherTemp7.setText(temp7);
            shortWeatherTemp8.setText(temp8);
            shortWeatherTemp9.setText(temp9);

            for (int i = 0; i < 9; i++) {
                if (sky[i].equals("맑음"))
                    skyImage[i].setImageResource(R.drawable.sun);
                if (sky[i].equals("구름조금"))
                    skyImage[i].setImageResource(R.drawable.cloud);
                if (sky[i].equals("구름많음"))
                    skyImage[i].setImageResource(R.drawable.clouds);
                if (sky[i].equals("구름많고 비") || sky[i].equals("구름많고 비 또는 눈"))
                    skyImage[i].setImageResource(R.drawable.rainy);
                if (sky[i].equals("구름많고 눈"))
                    skyImage[i].setImageResource(R.drawable.snowy);
                if (sky[i].equals("흐림"))
                    skyImage[i].setImageResource(R.drawable.cloudy);
                if (sky[i].equals("흐리고 비") || sky[i].equals("흐리고 비 또는 눈"))
                    skyImage[i].setImageResource(R.drawable.cloudy_rain);
                if (sky[i].equals("흐리고 눈"))
                    skyImage[i].setImageResource(R.drawable.cloudy_snow);
                if (sky[i].equals("흐리고 낙뢰"))
                    skyImage[i].setImageResource(R.drawable.cloudy_storm);
                if (sky[i].equals("뇌우/비"))
                    skyImage[i].setImageResource(R.drawable.storm);
                if (sky[i].equals("뇌우/눈"))
                    skyImage[i].setImageResource(R.drawable.storm_snow);
                if (sky[i].equals("뇌우/비 또는 눈"))
                    skyImage[i].setImageResource(R.drawable.thunder);
            }
        }

    }

    public class ReceiveWeekWeather extends AsyncTask<URL, Integer, Long> {

        Forecast6days forecast6days;
        String temp;
        String sky;
        String timeRelease;

        @Override
        protected Long doInBackground(URL... urls) {
            Retrofit client = new Retrofit.Builder().baseUrl("https://api2.sktelecom.com/weather/").addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitService service = client.create(RetrofitService.class);

            Call<RetrofitClient> call = service.longWeather("2", String.valueOf(lat), String.valueOf(lon));
            try {

                forecast6days = call.execute().body().getWeather().getForecast6days().get(0);
                timeRelease = forecast6days.getTimeRelease();
                temp = forecast6days.getTemperature().getTmax2day() + "/" + forecast6days.getTemperature().getTmin2day() + "\n"
                        + forecast6days.getTemperature().getTmax3day() + "/" + forecast6days.getTemperature().getTmin3day() + "\n"
                        + forecast6days.getTemperature().getTmax4day() + "/" + forecast6days.getTemperature().getTmin4day() + "\n"
                        + forecast6days.getTemperature().getTmax5day() + "/" + forecast6days.getTemperature().getTmin5day() + "\n"
                        + forecast6days.getTemperature().getTmax6day() + "/" + forecast6days.getTemperature().getTmin6day() + "\n"
                        + forecast6days.getTemperature().getTmax7day() + "/" + forecast6days.getTemperature().getTmin7day() + "\n"
                        + forecast6days.getTemperature().getTmax8day() + "/" + forecast6days.getTemperature().getTmin8day() + "\n"
                        + forecast6days.getTemperature().getTmax9day() + "/" + forecast6days.getTemperature().getTmin9day() + "\n"
                        + forecast6days.getTemperature().getTmax10day() + "/" + forecast6days.getTemperature().getTmin10day() + "\n";

                sky = forecast6days.getSky().getAm2day() + "/" + forecast6days.getSky().getPm2day() + "\n"
                        + forecast6days.getSky().getAm3day() + "/" + forecast6days.getSky().getPm3day() + "\n"
                        + forecast6days.getSky().getAm4day() + "/" + forecast6days.getSky().getPm4day() + "\n"
                        + forecast6days.getSky().getAm5day() + "/" + forecast6days.getSky().getPm5day() + "\n"
                        + forecast6days.getSky().getAm6day() + "/" + forecast6days.getSky().getPm6day() + "\n"
                        + forecast6days.getSky().getAm7day() + "/" + forecast6days.getSky().getPm7day() + "\n"
                        + forecast6days.getSky().getAm8day() + "/" + forecast6days.getSky().getPm8day() + "\n"
                        + forecast6days.getSky().getAm9day() + "/" + forecast6days.getSky().getPm9day() + "\n"
                        + forecast6days.getSky().getAm10day() + "/" + forecast6days.getSky().getPm10day() + "\n";

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            Log.d("시간시간시간", timeRelease);
            longWeatherSky.setText(sky);
            longWeatherTemp.setText(temp);
        }
    }

    private String getTime() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                Intent intent = new Intent(this, Place.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
