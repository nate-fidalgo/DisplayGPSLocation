package com.gpsutils.DisplayGPSLocation;


import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.List;

public class GpsTracker extends Service implements LocationListener {

    private TextView lat_displaybox ;
    private TextView long_displaybox ;
    private TextView bearing_displaybox ;
    private TextView height_displaybox ;
    private TextView speed_displaybox ;

    public GpsTracker(TextView latview , TextView longview , TextView bearingview , TextView heightView , TextView speedView )
    {
        lat_displaybox = latview ;
        long_displaybox = longview ;
        bearing_displaybox = bearingview ;
        height_displaybox  = heightView ;
        speed_displaybox   = speedView ;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

      double latlocation = location.getLatitude() ;
      double longlocation = location.getLongitude() ;
      double  latlocationDMS[]  ;
      double longlocationDMS[]  ;

      latlocationDMS  =  convertDDtoDMS( latlocation ) ;
      longlocationDMS =  convertDDtoDMS( longlocation ) ;

      lat_displaybox.setText( "" + (int)latlocationDMS[0] + "\u00B0" + (int)latlocationDMS[1] + "' " + String.format("%.3f",latlocationDMS[2])  + "\""  );
      long_displaybox.setText( "" + (int)longlocationDMS[0] + "\u00B0" + (int)longlocationDMS[1] + "' " + String.format("%.3f",longlocationDMS[2])  + "\"" );
      bearing_displaybox.setText( "" + location.getBearing() + "\u00B0") ;
      height_displaybox.setText( "" +  String.format("%.6f",location.getAltitude())  + " meters" ) ;
      speed_displaybox.setText( "" +  String.format("%.6f",location.getSpeed()) + " m/s " ) ;

        Location tlocation = new Location(LocationManager.GPS_PROVIDER);
        tlocation.setLatitude(23.5);
        tlocation.setLongitude(34.4);

        Location t2location = new Location(LocationManager.GPS_PROVIDER);
        t2location.setLatitude(23.6);
        t2location.setLongitude(34.4);

        float distanceresult[] = new float[5] ;
        Log.println( Log.INFO , "DISTANCE" , " The distance is " + location.distanceTo(tlocation) ) ;
        Location.distanceBetween(tlocation.getLatitude(),tlocation.getLongitude(), t2location.getLatitude(),t2location.getLongitude() , distanceresult ) ;
        Log.println( Log.INFO , "DISTANCE BETWEEN" , " The distance between is " + distanceresult[0] + " meters , inital bearing = " + distanceresult[1] + " final bearing is = " + distanceresult[2] + " last stuff " + distanceresult[3] + " more stuff = " + distanceresult[4]  ) ;
     // lat_displaybox.setText( "" + location.getLatitude() );
     // long_displaybox.setText( "" + location.getLongitude() );

    }

    private double[] convertDDtoDMS(double longlocation) {

        double deg = (int)longlocation ;
        double tmpmin =  ( Math.abs( longlocation ) - Math.abs(deg) ) * 60  ;
        double min = (int) tmpmin ;
        double second =  (tmpmin - min) * 60  ;

        double DMS[] = { deg , min , second } ;

        return DMS ;
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
