package com.aexample.a41758511.myapplication;

import android.graphics.Color;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UbicacionUsuario extends FragmentActivity implements OnMapReadyCallback {
    Handler mHandler;
Timer miRelok;
    TimerTask MiTareaRepetitiva;
    private GoogleMap mMap;
    OkHttpClient clien=new OkHttpClient();
TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_usuario);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        tv=(TextView) findViewById(R.id.tvResultt);
        tv.setText("Esta en "+ListSubidas.calle+" a las "+ ListSubidas.hora+". Estado: "+ListSubidas.condicion);
        miRelok=new Timer();

        MiTareaRepetitiva=new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                        StrictMode.setThreadPolicy(policy);
                        Request request = new Request.Builder()
                                .url("http://bdalex.hol.es/bd/TraerSubida.php?IdSubida=" + ListSubidas.Id)
                                .build();
                        try {
                            Response response = clien.newCall(request).execute();
                            String json = response.body().string();
                            JSONObject obj = new JSONObject(json);
                            mMap.clear();
                            LatLng pos = new LatLng(Double.parseDouble(obj.getString("LatLong").split(",")[0]), Double.parseDouble(obj.getString("LatLong").split(",")[1]));
                            mMap.addMarker(new MarkerOptions().position(pos).title("El"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
                            tv.setText("Esta en " + obj.getString("Calle") + " a las " + obj.getString("Horasubida") + ". Estado: " + obj.getString("Condicion"));
                        } catch (IOException | JSONException e) {
                            Log.d("Error", e.getMessage());

                        }
                    }
                });
            }
        };
        miRelok.schedule(MiTareaRepetitiva,30000,30000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MiTareaRepetitiva.cancel();

    }

    /**
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

        mMap.addMarker(new MarkerOptions().position(ListSubidas.ub).title("El"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ListSubidas.ub));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f));
        MapsInitializer.initialize(this);


        if(MainActivity.nombre.equals("15")) {
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(-34.409722, -58.685378),
                            new LatLng(-34.417003, -58.698964),
                            new LatLng(-34.413476, -58.701770),
                            new LatLng(-34.411086, -58.713068),
                            new LatLng(-34.415583, -58.721340),
                            new LatLng(-34.416565, -58.722123),
                            new LatLng(-34.417857, -58.722327),
                            new LatLng(-34.427831, -58.714720),
                            new LatLng(-34.433539, -58.712735),
                            new LatLng(-34.441136, -58.704767),
                            new LatLng(-34.463970, -58.687000),
                            new LatLng(-34.468985, -58.681861),
                            new LatLng(-34.470984, -58.677870),
                            new LatLng(-34.485895, -58.609992),
                            new LatLng(-34.488716, -58.572805),
                            new LatLng(-34.494765, -58.556324),
                            new LatLng(-34.498541, -58.550820),
                            new LatLng(-34.509381, -58.527206),
                            new LatLng(-34.545777, -58.494147),
                            new LatLng(-34.546148, -58.493149),
                            new LatLng(-34.535130, -58.467203),
                            new LatLng(-34.555744, -58.448517),
                            new LatLng(-34.557790, -58.452020),
                            new LatLng(-34.557816, -58.452020),
                            new LatLng(-34.558408, -58.450625),
                            new LatLng(-34.561492, -58.444177),
                            new LatLng(-34.562000, -58.444622),
                            new LatLng(-34.567646, -58.437895),
                            new LatLng(-34.577451, -58.428378),
                            new LatLng(-34.578237, -58.425803),
                            new LatLng(-34.580799, -58.421779),
                            new LatLng(-34.580720, -58.421061),
                            new LatLng(-34.581320, -58.420277),
                            new LatLng(-34.581753, -58.420395),
                            new LatLng(-34.582062, -58.420374),
                            new LatLng(-34.585068, -58.415933),
                            new LatLng(-34.602255, -58.442583),
                            new LatLng(-34.604516, -58.437144),
                            new LatLng(-34.604410, -58.434912),
                            new LatLng(-34.605593, -58.433110),
                            new LatLng(-34.607218, -58.432755),
                            new LatLng(-34.608269, -58.433839),
                            new LatLng(-34.608729, -58.434708),
                            new LatLng(-34.608799, -58.430481),

                            new LatLng(-34.615439, -58.430073),

                            new LatLng(-34.615228, -58.429233),
                            new LatLng(-34.642780, -58.422840),
                            new LatLng(-34.646337, -58.419890),
                            new LatLng(-34.646169, -58.416210),
                            new LatLng(-34.646999, -58.415309),
                            new LatLng(-34.660776, -58.416757))
                    .width(3)
                    .color(Color.RED));
        }
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);
    }
}
