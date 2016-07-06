package com.aexample.a41758511.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {
    SimpleDateFormat simpleDateFormat;
    String time;
    Calendar calander;
    public static TextView tvR;
    public String IdSubida;
    Handler handler;
    Runnable runnableCode;
    Button btnSubirme;
    Button btnActualizar;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        tvR = (TextView) findViewById(R.id.tvResult);
        calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm");

        time = simpleDateFormat.format(calander.getTime());

        tvR.setText("Me subí al " + MainActivity.nombre + " a las " + time + " en ");
        btnSubirme=(Button)findViewById(R.id.btSubir);
        btnSubirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                Ubicacion ub = new Ubicacion(Mapa.this);
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
                    json.put("Calle", ObtenerCallesTask.callepublica);


                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());

                    Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();

                    Response response = client.newCall(request).execute();
                    String jsonId=response.body().string();
                    IdSubida=jsonId;
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
        btnActualizar=(Button) findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 OkHttpClient client3 = new OkHttpClient();
                try {

                    String url ="http://bdalex.hol.es/bd/ActualizarUbicacion.php";
                    Ubicacion ub = new Ubicacion(Mapa.this);
                    LatLng syd=ub.getLocation();
                    double solonu= syd.latitude;
                    double solonu1= syd.longitude;
                    JSONObject json = new JSONObject();
                    calander = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat("HH:mm");

                    time = simpleDateFormat.format(calander.getTime());
                    json.put("Hora",time);
                    json.put("UltimaUbicacion",solonu+","+solonu1);
                    json.put("IdSubida",IdSubida.substring(0,IdSubida.length()-1));

                    json.put("Calle",ObtenerCallesTask.callepublica);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());

                    Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();

                    Response response = client3.newCall(request).execute();
                    Log.d("Response", response.body().string());
                } catch (IOException | JSONException e) {
                    Log.d("Error", e.getMessage());
                }
            }
        });

    }



    public  LatLng sydney;
    Handler han=new Handler();
    Runnable run;

    private OkHttpClient client1 = new OkHttpClient();
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        Ubicacion ub = new Ubicacion(Mapa.this);
        sydney = ub.getLocation();
        String todojunto=sydney.latitude+","+sydney.longitude;
        new ObtenerCallesTask().execute(todojunto);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Yo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f));
        MapsInitializer.initialize(this);

        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);


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
}

class ObtenerCallesTask extends AsyncTask<String,Void,String> {
    public static String callepublica="";
    private OkHttpClient cli = new OkHttpClient();

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        callepublica="";
        callepublica=s;
        Mapa.tvR.setText(Mapa.tvR.getText()+" "+callepublica);
    }
    public static String result="";
    @Override
    protected String doInBackground(String... voids) {

        Request request1 = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + voids[0])
                .build();

        try {
            Response response1;
            response1 = cli.newCall(request1).execute();
            String google = response1.body().string();
            result=ObtenerCalles(google);
        } catch (IOException e) {
            Log.d("Error", e.getMessage());
        }
        return result;

    }
    String ObtenerCalles(String json)
    {
        String calle="";
        try {
            JSONObject obj=new JSONObject(json);
            JSONArray res=obj.getJSONArray("results");
            JSONObject form=res.getJSONObject(0);
            calle=form.getString("formatted_address");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return calle;
    }
}
