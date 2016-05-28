package com.aexample.a41758511.myapplication;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Alex on 13/05/2016.
 */
public class Ubicacion implements LocationListener {
    private Context ctx;
    LocationManager locationManager;
    String provedor;
    public boolean NetON;
    public Ubicacion(Context ctx)
    {
        this.ctx=ctx;
        locationManager=(LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        provedor=LocationManager.NETWORK_PROVIDER;
        locationManager.requestLocationUpdates(provedor,100,1,this);
        NetON=locationManager.isProviderEnabled(provedor);

getLocation();
    } LatLng yo;
    public  LatLng getLocation()
    {

     if(NetON)
     {
         Location loc=locationManager.getLastKnownLocation(provedor);
         if(loc!=null)
         {
             StringBuilder builder=new StringBuilder();
             builder.append("Latitude: ").append(loc.getLatitude())
             .append(" Longitud : ").append(loc.getLongitude());
             yo=new LatLng(loc.getLatitude(),loc.getLongitude());

         }
     }return yo;
    }
    @Override
    public void onLocationChanged(Location location) {
getLocation();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this.ctx, "RED ACTIVADA", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this.ctx, "RED DESACTIVADA", Toast.LENGTH_SHORT).show();

    }
}
