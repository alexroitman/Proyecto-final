package com.aexample.a41758511.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ListSubidas extends AppCompatActivity {
public static ListView lv;
    public static List<Subidas> lisSub = new ArrayList<>();

    public static ListViewAdapter lAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_subidas);
        lv = (ListView) findViewById(R.id.lvSubidas);
        new SubidasTask(this, lv).execute("http://bdalex.hol.es/bd/ListarSubidas.php?IdLinea=");


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
    public static String[] titulo;
    public static Integer[] Imagenes;
    protected void onPostExecute(  List<Subidas> lislin) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        int i=0;
        titulo = new String[lislin.size()];
        Imagenes = new Integer[lislin.size()];
        for (Subidas estaSubida:lislin) {

            ListSubidas.lisSub.add(estaSubida);
            titulo[i]="Me subi a las "+estaSubida.Hora+" en"+ estaSubida.UbicacionSubida;
            Resources res = context.getResources();

            Integer ic = res.getIdentifier(estaSubida.IdLinea, "drawable", context.getApplicationContext().getPackageName());
            try {
                Imagenes[i]=ic;
            }
            catch (Exception ex)
            {
                throw ex;
            }

            i++;


        }
        ListSubidas.lAdapter = new ListViewAdapter(context,SubidasTask.titulo,SubidasTask.Imagenes);
        lv.setAdapter(ListSubidas.lAdapter);
        MainActivity.adapter.notifyDataSetChanged();

        Log.d("Alex","error");
//        spinner = (Spinner) spinner.findViewById(R.id.spinner);
        //    spinner.setAdapter(new SocialNetworkSpinnerAdapter(MainActivity.ct,lislin));
        //lv = getListView();


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
            sub.IdLinea= jsonResultado.getString("IdLinea");
            sub.Hora= jsonResultado.getString("Horasubida");
            sub.UltimaUbicacion = jsonResultado.getString("UltimaUbicacion");

            lin.add(sub);

        }

        return lin;
    }


}

