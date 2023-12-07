package com.gpsutils.DisplayGPSLocation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final long MIN_TIME_BW_UPDATES = 0;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    private boolean useNetworkOverGPS = false;

    private StringBuffer sensbuf ;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sensor_testing);
        setContentView(R.layout.activity_main);
        sensbuf = new StringBuffer() ;

        TextView tview = new TextView(this) ;
        tview.setText(sensbuf);
        tview.setTextColor(Color.WHITE);
        tview.setBackgroundColor(Color.BLACK);

        ((LinearLayout)findViewById(R.id.layoutID)).addView(tview);

        TextView latbox = findViewById(R.id.latView) ;
        TextView longbox = findViewById(R.id.longView) ;
        TextView bearingbox = findViewById(R.id.bearingView) ;
        TextView heightbox = findViewById(R.id.heightView) ;
        TextView speedbox = findViewById(R.id.speedView) ;

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if( isNetworkEnabled == true )
        {
            Spannable gpstext = new SpannableString("NETWORK AVAILABLE!!!\n");

            gpstext.setSpan(new ForegroundColorSpan(Color.GREEN), 0, gpstext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            tview.append(gpstext);

        }
        else{
            Spannable gpstext = new SpannableString("NETWORK NOT AVAILABLE!!!\n");

            gpstext.setSpan(new ForegroundColorSpan(Color.RED), 0, gpstext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            tview.append(gpstext);
        }

        if( isGPSEnabled == true )
        {
            Spannable gpstext = new SpannableString("GPS AVAILABLE!!!\n");

            gpstext.setSpan(new ForegroundColorSpan(Color.GREEN), 0, gpstext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            tview.append(gpstext);

        }
        else{
            Spannable gpstext = new SpannableString("GPS NOT AVAILABLE!!!\n");

            gpstext.setSpan(new ForegroundColorSpan(Color.RED), 0, gpstext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            tview.append(gpstext);

        }



        if (isNetworkEnabled && useNetworkOverGPS == true) {
            //check the network permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            }

            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, new GpsTracker(latbox,longbox,bearingbox,heightbox,speedbox));

            Log.d("Network", "Network");
            if (locationManager != null) {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                }
            }
        }
        else if (isGPSEnabled) {
                //check the network permission
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                }
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, new GpsTracker(latbox,longbox,bearingbox,heightbox,speedbox));

                Log.d("GPS Enabled", "GPS Enabled");
                if (locationManager != null) {
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        Log.i("LOCATION-INFO:", "lat:= " + latitude + " ---- long:= " + longitude);
                    }
                }

            }
            else {
            Spannable gpstext = new SpannableString("NO AVAILABLE NETWORK OR GPS DEVICES!!!\n");

            gpstext.setSpan(new ForegroundColorSpan(Color.RED), 0, gpstext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            tview.append(gpstext);

            }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if( location != null )
                Log.i("LOCATION-INFO:", "Provider GPS := " + location.toString() + " || " + location.getAltitude());
            }
        }


    }

    public void exitAction(View view)
    {
        //  Context context = getApplicationContext();
        //  CharSequence text = "Android sensors say goodbye!";
        //  int duration = Toast.LENGTH_SHORT;

        //  Toast toast = Toast.makeText(context, text, duration);
        //  toast.setGravity(Gravity.TOP| Gravity.LEFT, 0, 0);
        //   toast.show();
        //   super.onDestroy();
        //this.finish();
        this.finish();
        System.exit(0);
    }

    protected void onDestroy() {


        super.onDestroy();
        Log.i( "SENSORINFO:" ,"destroying app now ") ;

    }


}