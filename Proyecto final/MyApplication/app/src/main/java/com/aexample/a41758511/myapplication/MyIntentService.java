package com.aexample.a41758511.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
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

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    int i = 0;
    public static String IdSubida;
    double solonu ;
    double solonu1;
    private OkHttpClient cli = new OkHttpClient();

    @Override
    protected void onHandleIntent(final Intent workIntent) {


        SharedPreferences prefs = getSharedPreferences("Dic", MODE_PRIVATE);
        String dataString = workIntent.getDataString();
        String idsub = prefs.getString("IdSubida", "9");
        OkHttpClient client3 = new OkHttpClient();
        String calle = "";
        try {

            String time;
            Calendar calander;
            SimpleDateFormat simpleDateFormat;
            //  String url = "bdalex.hol.es/bd/ActualizarUbicacion.php";
            Ubicacion ub = new Ubicacion(getBaseContext());
            LatLng syd = ub.getLocation();
             solonu = syd.latitude;
             solonu1 = syd.longitude;
            String todojunto = syd.latitude + "," + syd.longitude;
            Request request1 = new Request.Builder()
                    .url("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + todojunto)
                    .build();

            try {
                Response response1;
                response1 = cli.newCall(request1).execute();
                String google = response1.body().string();
                JSONObject obj = new JSONObject(google);
                JSONArray res = obj.getJSONArray("results");
                JSONObject form = res.getJSONObject(0);
                calle = form.getString("formatted_address");

            } catch (IOException e) {
                Log.d("Error", e.getMessage());
            }


            JSONObject json = new JSONObject();
            calander = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("HH:mm");

            time = simpleDateFormat.format(calander.getTime());
            json.put("Hora", time);
            json.put("UltimaUbicacion", solonu + "," + solonu1);
            json.put("IdSubida", idsub);

            json.put("Calle", calle);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());

            Request request = new Request.Builder()
                    .url("http://bdalex.hol.es/bd/ActualizarUbicacion.php")
                    .post(body)
                    .build();

            Response response = client3.newCall(request).execute();
            i++;
            Log.d("Response", response.body().string());
        } catch (IOException | JSONException e) {
            Log.d("Error", e.getMessage());
        }
        Request request = new Request.Builder()
                .url("http://bdalex.hol.es/bd/BajarAut.php?Loc="+ solonu + "," + solonu1+"&Linea="+MainActivity.nombre+"&IdSubida="+idsub)
                .build();
        try {
            Response response = cli.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}



