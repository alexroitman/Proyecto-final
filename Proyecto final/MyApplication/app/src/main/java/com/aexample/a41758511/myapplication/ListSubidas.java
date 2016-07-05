package com.aexample.a41758511.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListSubidas extends AppCompatActivity {
public static ListView lv;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static ArrayList<Subidas> lisSub = new ArrayList<>();
    public static ArrayAdapter<Subidas> ad;
    public static ListViewAdapter lAdapter;
    public static Context ctxSub;
    public static Activity act;
    public static LatLng ub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_subidas);
        lv = (ListView) findViewById(R.id.lvSubidas);

        new SubidasTask(this, lv).execute("http://bdalex.hol.es/bd/ListarSubidas.php?IdLinea=");
       // ad=new ArrayAdapter<Subidas>(this,R.layout.activity_list_row,lisSub);
        ctxSub=getApplicationContext();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] latLng = lisSub.get(position).UbicacionSubida.split(",");
                double latitude = Double.parseDouble(latLng[0]);
                double longitude = Double.parseDouble(latLng[1]);

                ub=new LatLng(latitude, longitude);
                Intent I=new Intent(ListSubidas.this,UbicacionUsuario.class);
                startActivity(I);
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                     @Override
                                                     public void onRefresh() {
                                                         new SubidasTask(act, lv).execute("http://bdalex.hol.es/bd/ListarSubidas.php?IdLinea=");
                                                         mSwipeRefreshLayout.setRefreshing(false);
                                                     }
                                                 });
            act=this;
    }
}
class SubidasTask extends AsyncTask<String, Void,  List<Subidas>> {
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
    public static List<String> titulo= new ArrayList<>();
    public static List<Integer> Imagenes= new ArrayList<>();

    @Override
    protected void onPostExecute(List<Subidas> subidases) {
        super.onPostExecute(subidases);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        int i=0;
ListSubidas.lisSub.clear();
        for (Subidas estaSubida:subidases) {
            titulo.add("Me subi a las "+estaSubida.Hora+" en"+ estaSubida.UbicacionSubida);
            Resources res = context.getResources();
            Integer ic = res.getIdentifier(estaSubida.IdLinea.toString(), "drawable", context.getApplicationContext().getPackageName());
            try {
                Imagenes.add(ic);
            }
            catch (Exception ex)
            {
                throw ex;
            }

            i++;
            ListSubidas.lisSub.add(estaSubida);

        }

        ListSubidas.lAdapter = new ListViewAdapter(ListSubidas.act,ListSubidas.lisSub);
        ListSubidas.lv.setAdapter(ListSubidas.lAdapter);


        Log.d("Alex","error");
    }


    private OkHttpClient client = new OkHttpClient();

    @Override
    protected List<Subidas> doInBackground(String... params) {
        String url = params[0];
        url+=MainActivity.nombre;
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String json=response.body().string();
            parsearResultado(json);
        } catch (IOException | JSONException e) {
            Log.d("Error", e.getMessage());

        }
        return lin;
    }
    public static List<Subidas> lin;
    public static String[] textos=new String[]{};
    List<Subidas> parsearResultado(String JSONstr) throws JSONException {
        List<Subidas> eventos = new ArrayList<>();
        JSONArray jsonEventos = new JSONArray(JSONstr);

        lin= new ArrayList<Subidas>();
        for (int i = 0; i < jsonEventos.length(); i++) {
            JSONObject jsonResultado = jsonEventos.getJSONObject(i);
            Subidas sub=new Subidas();
            sub.IdSubida= jsonResultado.getString("IdSubida");
            sub.UbicacionSubida= jsonResultado.getString("LatLong");
            sub.IdLinea= jsonResultado.getInt("IdLinea");
            sub.Hora= jsonResultado.getString("Horasubida");
            sub.UltimaUbicacion = jsonResultado.getString("UltimaUbicacion");
            sub.Calle = jsonResultado.getString("Calle");

            lin.add(sub);

        }

        return lin;
    }


}

