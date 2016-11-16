package com.aexample.a41758511.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CercanoTaskk extends AsyncTask<Void ,Void,String>
{  private OkHttpClient client = new OkHttpClient();
    public String parada;
    public static String bondi;
    public static int dis1;
    public int dist;
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Cercano.tvCerca.setText(Cercano.tvCerca.getText()+" y su parada mas cercana esta a "+dist+" m en " );
         new ObtenerCallesTask().execute(parada,"Cercano2");
        double lat= Double.parseDouble(parada.split(",")[0]);
        double longi= Double.parseDouble(parada.split(",")[1]);
        LatLng stop=new LatLng(lat,longi);
        double lat1= Double.parseDouble(CercanoTaskk.bondi.split(",")[0]);
        double longi1= Double.parseDouble(CercanoTaskk.bondi.split(",")[1]);
        LatLng bondi=new LatLng(lat1,longi1);
        Cercano.mMap.addMarker(new MarkerOptions().position(stop).title("Parada").icon(BitmapDescriptorFactory.fromResource(R.drawable.p)));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(Cercano.sydney, 16.0f);
        Cercano.mMap.animateCamera(cameraUpdate);

        Cercano.mMap.addMarker(new MarkerOptions().position(bondi).title("bondi").icon(BitmapDescriptorFactory.fromResource(R.drawable.b)));
        CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(bondi, 16.0f);
        Cercano.mMap.animateCamera(cameraUpdate1);
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
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {

        Request request = new Request.Builder()
                .url("http://bdalex.hol.es/bd/ParadaCercana.php?Loc="+Cercano.todojunto+"&Linea="+MainActivity.nombre)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject cerca=new JSONObject(response.body().string());
            parada=cerca.getString("LatLong");
            dist=cerca.getInt("Distancia");

        } catch (IOException | JSONException e) {
            Log.d("Error", e.getMessage());

        }
        Request request1 = new Request.Builder()
                .url("http://bdalex.hol.es/bd/ColectivoMasCercano.php?Loc="+Cercano.todojunto+"&Linea="+MainActivity.nombre)
                .build();
        try {
            Response response1 = client.newCall(request1).execute();
            JSONObject cerca1=new JSONObject(response1.body().string());
            bondi=cerca1.getString("LatLong");
            dis1=cerca1.getInt("Distancia");

        } catch (IOException | JSONException e) {
            Log.d("Error", e.getMessage());

        }
        return parada;

    }
}
