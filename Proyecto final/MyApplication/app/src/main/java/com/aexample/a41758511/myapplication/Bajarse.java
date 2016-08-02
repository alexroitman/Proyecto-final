package com.aexample.a41758511.myapplication;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Bajarse extends AppCompatActivity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    OkHttpClient cli = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bajarse);
        OkHttpClient client = new OkHttpClient();

            String url = "bdalex.hol.es/bd/EliminarSubida.php?Sub=" + Mapa.IdSubida;
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response res = cli.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }


            finish();

    }
}