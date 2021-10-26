package com.example.cronosappaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cronosappaps.Cammon.Comum;
import com.example.cronosappaps.Helper.Helper;
import com.example.cronosappaps.Model.AbrirMapaClima;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView textCidade, textLastUpdate, textDescription, textHumidity, textTime, textCelsius;
    ImageView imageView;
    LocationManager locationManager;
    String provider;
    int MY_PERMISSION = 0;

    static double lat, lng;
    AbrirMapaClima openWeatherMap = new AbrirMapaClima();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textCidade = findViewById(R.id.textCidade);
        textLastUpdate = findViewById(R.id.textLastUpdate);
        textDescription = findViewById(R.id.textDescription);
        textHumidity = findViewById(R.id.textHumidity);
        textTime = findViewById(R.id.textTime);
        textCelsius = findViewById(R.id.textCelsius);
        imageView = findViewById(R.id.imageTemp);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE


            }, MY_PERMISSION);
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location == null) {
            Log.e("TAG", "No location");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();



        locationManager.removeUpdates(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE

            }, MY_PERMISSION);
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();

        new GetWeather().execute(Comum.apiRequest(String.valueOf(lat),String.valueOf(lng)));


    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }


    private class GetWeather extends AsyncTask<String,Void,String> {

        ProgressDialog pd = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];

            Helper http = new Helper();
            stream = http.getHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.contains("Error: Cidade não encontrada")){
                pd.dismiss();
                return;
            }

            Gson gson = new Gson();
            Type mType = new TypeToken<AbrirMapaClima>() {}.getType();
            openWeatherMap = gson.fromJson(s,mType);
            pd.dismiss();

            textCidade.setText(String.format("%s, %s",openWeatherMap.getName(),openWeatherMap.getSys().getCountry()));
            textLastUpdate.setText(String.format("Ultima atualização: %s", Comum.getDateNow()));
            textDescription.setText(String.format("%s",openWeatherMap.getWeather().get(0).getDescription()));
            textHumidity.setText(String.format("%d%%",openWeatherMap.getMain()));
            textTime.setText(String.format("%s/%s", Comum.unixTimeStampToDateTime(openWeatherMap.getSys().getSunrise()),Comum.unixTimeStampToDateTime(openWeatherMap.getSys().getSunset())));
            textCelsius.setText(String.format("%.2f °C",openWeatherMap.getMain().getTemp()));
            Picasso.get().load(Comum.getImagem(openWeatherMap.getWeather().get(0).getIcon()))
                    .into(imageView);
        }

    }
}
