package com.aexample.a41758511.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by 41758511 on 13/7/2016.
 */
public class Service extends IntentService {
    public static String IdSubida;
    public Service(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        SharedPreferences prefs = getSharedPreferences("Dic", MODE_PRIVATE);
        String dataString = workIntent.getDataString();

        OkHttpClient client3 = new OkHttpClient();
        try {
            String time;
            Calendar calander;
            SimpleDateFormat simpleDateFormat;
            String url ="http://yamesubi.azurewebsites.net/ActualizarUbicacion.php";
            Ubicacion ub = new Ubicacion(this);
            LatLng syd=ub.getLocation();
            double solonu= syd.latitude;
            double solonu1= syd.longitude;
            JSONObject json = new JSONObject();
            calander = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("HH:mm");
String id=prefs.getString("IdSubida","1");
            time = simpleDateFormat.format(calander.getTime());
            json.put("Hora",time);
            json.put("UltimaUbicacion",solonu+","+solonu1);
            json.put("IdSubida",id.substring(0,id.length()-2));

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
}
