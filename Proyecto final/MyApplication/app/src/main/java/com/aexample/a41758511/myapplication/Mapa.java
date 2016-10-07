package com.aexample.a41758511.myapplication;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {
    SimpleDateFormat simpleDateFormat;
    String time;
    Calendar calander;
    public static TextView tvR;
    public static String IdSubida;
    Handler handler;
    Runnable runnableCode;
    Button btnSubirme;
    Button btnActualizar;
    Button btnBajarme;
    ImageButton btnRefresh;
    Button btnReg;
    public static AlarmManager alarmManager;
    private GoogleMap mMap;
    int subido;
    public static PendingIntent pending;

    @Override
    protected void onResume() {
        super.onResume();
        if(subido==0)
        {
            btnSubirme.setVisibility(View.VISIBLE);
            btnBajarme.setVisibility(View.INVISIBLE);
        }
        else
        {
            btnSubirme.setVisibility(View.INVISIBLE);
            btnBajarme.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);



    }

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
subido=0;
        time = simpleDateFormat.format(calander.getTime());
        /*SharedPreferences.Editor editor = getSharedPreferences("Dic", MODE_PRIVATE).edit();
        editor.putString("IdSubida", "");
        editor.commit();
        SharedPreferences prefs = getSharedPreferences("Dic", MODE_PRIVATE);
        if(prefs.getString("IdSubida", "")=="")
        {
            btnSubirme.setVisibility(View.VISIBLE);
            btnBajarme.setVisibility(View.INVISIBLE);
        }
        else
        {
            btnSubirme.setVisibility(View.INVISIBLE);
            btnBajarme.setVisibility(View.VISIBLE);
        }*/
        tvR.setText("Me subí al " + MainActivity.nombre + " a las " + time + " en ");
        Ubicacion ub = new Ubicacion(Mapa.this);
        sydney = ub.getLocation();
        String todojunto=sydney.latitude+","+sydney.longitude;
        new ObtenerCallesTask().execute(todojunto,"Registrar");
        btnSubirme=(Button)findViewById(R.id.btSubir);

        btnBajarme=(Button) findViewById(R.id.btnBajarme);
        btnBajarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mapa.alarmManager.cancel(Mapa.pending);
                OkHttpClient cli=new OkHttpClient();
                SharedPreferences prefs = getSharedPreferences("Dic", MODE_PRIVATE);
                String url = "http://www.bdalex.hol.es/bd/ActualizarCondicion.php?Condicion=BAJADO&IdSubida=" + prefs.getString("IdSubida", "9");
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                try {
                    Response res = cli.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"Se ha registrado su bajada",Toast.LENGTH_LONG).show();
                btnSubirme.setVisibility(View.VISIBLE);
                btnBajarme.setVisibility(View.INVISIBLE);
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                nMgr.cancel(0);
                subido=0;
            }
        });
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
                    //String url ="bdalex.hol.es/bd/AgregarSubida.php";
                    JSONObject json = new JSONObject();
                    String url ="http://bdalex.hol.es/bd/AgregarSubida.php";
                    json.put("LatLong",solonum1+","+solonum2);
                    json.put("IdLinea", Linea);
                    json.put("Horasubida", Hora);
                    json.put("Calle", ObtenerCallesTask.callepublica);
                    json.put("Condicion", "Viajando");

                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());

                    Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();

                    Response response = client.newCall(request).execute();
                    String jsonId=response.body().string();
                    SharedPreferences.Editor editor = getSharedPreferences("Dic", MODE_PRIVATE).edit();
                    editor.putString("IdSubida", jsonId.substring(0,jsonId.length()-1));
                    editor.commit();
                    Log.d("Response", response.body().string());
                } catch (IOException | JSONException e) {
                    Log.d("Error", e.getMessage());
                }
                subido=1;

              btnSubirme.setVisibility(View.INVISIBLE);
                btnBajarme.setVisibility(View.VISIBLE);
               alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent alarmIntent = new Intent(getApplicationContext(), MyIntentService.class);
                pending = PendingIntent.getService(getApplicationContext(), 0, alarmIntent, 0);

                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() +
                                30000,60000, pending);
                Intent intent = new Intent(getApplicationContext(), Bajarse.class);

// use System.currentTimeMillis() to have a unique ID for the pending intent
                PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
                Notification n  = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                            R.drawable.logoproyecto);

                    n = new Notification.Builder(getApplicationContext())
                            .setContentTitle("Ya me subi")
                            .setContentText("BAJARME")
                            .setSmallIcon(R.drawable.logoproyecto)
                            .setContentIntent(pIntent)
                            .setAutoCancel(true)
                            .setLargeIcon(icon)
                            //.addAction(R.drawable.logoproyecto, "Call", pIntent)

                            .build();
                }


                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                notificationManager.notify(0, n);

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
                Toast.makeText(getApplicationContext(),"Subida registrada correctamente",Toast.LENGTH_LONG).show();

                Intent Vuelve= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(Vuelve);
            }

        });
        btnActualizar=(Button) findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 OkHttpClient client3 = new OkHttpClient();
                try {

                    String url ="bdalex.hol.es/bd/ActualizarUbicacion.php";
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


        mMap.addMarker(new MarkerOptions().position(sydney).title("Yo"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(sydney, 16.0f);
        mMap.animateCamera(cameraUpdate);
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
     //   mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f));
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
                            new LatLng(-34.608605, -58.434719),
                            new LatLng(-34.608799, -58.436146),
                            new LatLng(-34.616715, -58.432888),
                            new LatLng(-34.616729, -58.432910),
                            new LatLng(-34.615204, -58.429452),
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
        btnRefresh=(ImageButton) findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                tvR.setText("Me subí al " + MainActivity.nombre + " a las " + time + " en ");
                Ubicacion ub = new Ubicacion(Mapa.this);
                sydney = ub.getLocation();
                String todojunto=sydney.latitude+","+sydney.longitude;
                new ObtenerCallesTask().execute(todojunto,"Registrar");
                mMap.addMarker(new MarkerOptions().position(sydney).title("Yo"));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(sydney, 16.0f);
                mMap.animateCamera(cameraUpdate);
            }
        });

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
public String origen;
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        callepublica = "";
        callepublica = s;
        if (origen.equals("Registrar")) {
            Mapa.tvR.setText(Mapa.tvR.getText() + " " + callepublica);
        }
        if (origen.equals("Cercano1")) {
            Cercano.tvCerca.setText("Estas en " + ObtenerCallesTask.callepublica);

        }
        if (origen.equals("Cercano2")) {
            Cercano.tvCerca.setText(Cercano.tvCerca.getText() + ObtenerCallesTask.callepublica);
        }
    }
    public static String result="";
    @Override
    protected String doInBackground(String... voids) {
        origen=voids[1];
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
