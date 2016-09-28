package com.aexample.a41758511.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cercano extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap mMap;
    public static LatLng sydney;
    public static Context ct;
    public static TextView tvCerca;
    public static String todojunto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cercano);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tvCerca=(TextView) findViewById(R.id.tvResultado);
        Ubicacion ub = new Ubicacion(Cercano.this);
        sydney = ub.getLocation();
       todojunto=sydney.latitude+","+sydney.longitude;
        new ObtenerCallesTask().execute(todojunto,"Cercano1");
        ct=this;



    }


    /**
     *
     *
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
       LatLng yo=new LatLng(Double.parseDouble(todojunto.split(",")[0]),Double.parseDouble(todojunto.split(",")[1]));
new CercanoTask().execute(); Cercano.mMap.addMarker(new MarkerOptions().position(yo).title("Yo"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(Cercano.sydney, 16.0f);
        Cercano.mMap.animateCamera(cameraUpdate);
    }
}
class CercanoTask extends AsyncTask<Void ,String,String>
{  private OkHttpClient client = new OkHttpClient();
    public String parada;
    public int dist;
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Cercano.tvCerca.setText(Cercano.tvCerca.getText()+" y su parada mas cercana esta a "+dist+" m en " );
        new ObtenerCallesTask().execute(parada,"Cercano2");
        double lat= Double.parseDouble(parada.split(",")[0]);
        double longi= Double.parseDouble(parada.split(",")[1]);
        LatLng stop=new LatLng(lat,longi);
        Cercano.mMap.addMarker(new MarkerOptions().position(stop).title("Yo"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(Cercano.sydney, 16.0f);
        Cercano.mMap.animateCamera(cameraUpdate);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    ProgressDialog dialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(Cercano.ct);
        dialog.setMessage("Calculando, por favor espere");
        dialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {

        Request request = new Request.Builder()
                .url("http://bdalex.hol.es/bd/ParadaCercana.php?Loc=+"+Cercano.todojunto+"&Linea="+MainActivity.nombre)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject cerca=new JSONObject(response.body().string());
            parada=cerca.getString("LatLong");
            dist=cerca.getInt("Distancia");

        } catch (IOException | JSONException e) {
            Log.d("Error", e.getMessage());

        }
        return parada;

    }
}
