package com.aexample.a41758511.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 41758511 on 22/6/2016.
 */
public class SubidasTask extends AsyncTask<String, Void, List<SocialNetwork>> {
        private ProgressDialog dialog;
        ListView lv;

        public SubidasTask(Context activity, ListView l) {

            Log.i("1", "Called");
            context = activity;
            dialog = new ProgressDialog(context);
            l=lv;
        }

        public Context context;

        protected void onPreExecute() {
            this.dialog.setMessage("Cargando subidas, por favor espere");
            this.dialog.show();
        }
        ArrayAdapter<String> adapter;
        List<SocialNetwork> jsonlist = new ArrayList<SocialNetwork>();

        Spinner spinner ;
        protected void onPostExecute(  List<SocialNetwork> lislin) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            for (SocialNetwork esteSocial:lislin) {

                MainActivity.lislin.add(esteSocial);
            }
            MainActivity.adapter.notifyDataSetChanged();

            Log.d("Alex","error");
//        spinner = (Spinner) spinner.findViewById(R.id.spinner);
            //    spinner.setAdapter(new SocialNetworkSpinnerAdapter(MainActivity.ct,lislin));
            //lv = getListView();


        }
        private OkHttpClient client = new OkHttpClient();

        @Override
        protected List<SocialNetwork> doInBackground(String... params) {
            String url = params[0];
            url+=MainActivity.nombre;
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();

                return parsearResultado(response.body().string());
            } catch (IOException | JSONException e) {
                Log.d("Error", e.getMessage());
                return new ArrayList<SocialNetwork>();
            }
        }
        public static List<SocialNetwork> lin;
        List<SocialNetwork> parsearResultado(String JSONstr) throws JSONException {
            List<SocialNetwork> eventos = new ArrayList<>();
            JSONArray jsonEventos = new JSONArray(JSONstr);

            lin= new ArrayList<SocialNetwork>();
            for (int i = 0; i < jsonEventos.length(); i++) {
                JSONObject jsonResultado = jsonEventos.getJSONObject(i);
                int id = jsonResultado.getInt("Id");
                Integer Numero = jsonResultado.getInt("Numero");
                String Icono = jsonResultado.getString("Imagen");

                Resources res = context.getResources();

                int ic = res.getIdentifier(Icono, "drawable", context.getApplicationContext().getPackageName());
                //   SocialNetwork e = new SocialNetwork(Numero,Icono);
                lin.add(new SocialNetwork(Numero.toString(),ic));
                //   eventos.add(e);
            }

            return lin;
        }


    }

