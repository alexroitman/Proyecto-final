
package com.aexample.a41758511.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.spdy.FrameReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    SimpleDateFormat simpleDateFormat;
    String time;
    Calendar calander;
    TextView tvR;
    public static String IdSubida;
     Handler handler;
     Runnable runnableCode;

    private GoogleMap mMap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
Button btnSubirme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
       // Intent i = getIntent();
       // Bundle b = i.getExtras();
       // new ObtenerCallesTask().execute();
        tvR = (TextView) findViewById(R.id.tvResult);
        calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm");

        time = simpleDateFormat.format(calander.getTime());

        tvR.setText("Me subí a la " + MainActivity.nombre + " a las " + time + " en ");

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        btnSubirme=(Button)findViewById(R.id.btSubir);
        btnSubirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                Ubicacion ub = new Ubicacion(MapsActivity.this);
                sydney = ub.getLocation();
                double solonum1= sydney.latitude;
                double solonum2= sydney.longitude;
                String Hora = time;
                String Linea =MainActivity.nombre ;



                    try {
                        OkHttpClient client = new OkHttpClient();
                        String url ="http://bdalex.hol.es/bd/AgregarSubida.php";
                        JSONObject json = new JSONObject();

                        json.put("LatLong",solonum1+","+solonum2);
                        json.put("IdLinea", Linea);
                        json.put("Horasubida", Hora);



                        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());

                        Request request = new Request.Builder()
                                .url(url)
                                .post(body)
                                .build();

                        Response response = client.newCall(request).execute();
                        IdSubida= getIdSubida(response.body().string());
                        Log.d("Response", response.body().string());
                    } catch (IOException | JSONException e) {
                        Log.d("Error", e.getMessage());
                    }
                Toast.makeText(getApplicationContext(),"Subida registrada correctamente",Toast.LENGTH_LONG).show();
                    //Intent intent = new Intent(this, ListarEventos.class);
                    //startActivity(intent);
               /* handler=new Handler();
// Define the code block to be executed
                runnableCode = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            String url ="http://bdalex.hol.es/bd/ActualizarUbicacion.php";
                            Ubicacion ub = new Ubicacion(MapsActivity.this);
                            JSONObject json = new JSONObject();
                            json.put("UltimaUbicacion",ub.getLocation());
                            json.put("IdSubida",IdSubida);
                            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());

                            Request request = new Request.Builder()
                                    .url(url)
                                    .post(body)
                                    .build();

                            Response response = client.newCall(request).execute();
                            Log.d("Response", response.body().string());
                        } catch (IOException | JSONException e) {
                            Log.d("Error", e.getMessage());
                        }
                        Log.d("Handlers", "Called on main thread");

                        handler.postDelayed(runnableCode, 60000);
                    }
                };

                handler.post(runnableCode);*/

            }

        });




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
    public  LatLng sydney;
    Handler han=new Handler();
    Runnable run;
    private OkHttpClient client1 = new OkHttpClient();
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera


        mMap.addMarker(new MarkerOptions().position(sydney).title("Yo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f));
        MapsInitializer.initialize(this);

        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);


        //Polyline polyline=new Polyline();
        //mMap.addPolyline();

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.aexample.a41758511.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.aexample.a41758511.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
    String getIdSubida(String json)
    {
        try {
            JSONObject obj=new JSONObject(json);
            return obj.getString("IdSubida");
        } catch (JSONException e) {
            e.printStackTrace();
            return new String("Error");
        }
    }


    private void ObtenerCalles(String json) {

    }

    }
