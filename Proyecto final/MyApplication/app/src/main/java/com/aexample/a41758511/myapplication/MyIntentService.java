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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }


        public static String IdSubida;

        private final Handler mHandler = new Handler();
        private Runnable mUpdateTimeTask;
        @Override
        protected void onHandleIntent(final Intent workIntent) {

            mUpdateTimeTask = new Runnable() {
                public void run() {

                    SharedPreferences prefs = getSharedPreferences("Dic", MODE_PRIVATE);
                    String dataString = workIntent.getDataString();
                    OkHttpClient client3 = new OkHttpClient();
                    try {

                        String time;
                        Calendar calander;
                        SimpleDateFormat simpleDateFormat;
                        String url = "bdalex.hol.es/bd/ActualizarUbicacion.php";
                        Ubicacion ub = new Ubicacion(getBaseContext());
                        LatLng syd = ub.getLocation();
                        double solonu = syd.latitude;
                        double solonu1 = syd.longitude;
                        JSONObject json = new JSONObject();
                        calander = Calendar.getInstance();
                        simpleDateFormat = new SimpleDateFormat("HH:mm");
                        String idsub = prefs.getString("IdSubida", "9");
                        time = simpleDateFormat.format(calander.getTime());
                        json.put("Hora", time);
                        json.put("UltimaUbicacion", solonu + "," + solonu1);
                        json.put("IdSubida", idsub);

                        json.put("Calle", ObtenerCallesTask.callepublica);
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
            };
            mHandler.removeCallbacks(mUpdateTimeTask);
            mHandler.postDelayed(mUpdateTimeTask, 1000*60*2);


        }
    }


