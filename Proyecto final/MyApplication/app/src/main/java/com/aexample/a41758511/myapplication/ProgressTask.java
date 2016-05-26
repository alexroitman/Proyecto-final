package com.aexample.a41758511.myapplication;



        import android.app.ProgressDialog;
        import android.content.Context;
        import android.os.AsyncTask;
        import android.util.Log;
        import android.widget.Spinner;
        import android.widget.SpinnerAdapter;

        import com.squareup.okhttp.OkHttpClient;
        import com.squareup.okhttp.Request;
        import com.squareup.okhttp.Response;

        import org.apache.http.HttpResponse;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.apache.http.util.EntityUtils;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by 41758511 on 24/5/2016.
 */

class ProgressTask extends AsyncTask<String, Void, ArrayList<SocialNetwork>> {
    private ProgressDialog dialog;

    public ProgressTask(Context activity) {

        Log.i("1", "Called");
        context = activity;
        dialog = new ProgressDialog(context);
    }

    private Context context;

    protected void onPreExecute() {
        this.dialog.setMessage("Progress start");
        this.dialog.show();
    }

    List<SocialNetwork> jsonlist = new ArrayList<SocialNetwork>();

    Spinner spin ;
    protected void onPostExecute(final Boolean success) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        SpinnerAdapter adapter = new SocialNetworkSpinnerAdapter(context, jsonlist);
        spin.setAdapter(adapter);
        //lv = getListView();


    }
    HttpClient client = new DefaultHttpClient();
    @Override
    protected ArrayList<SocialNetwork> doInBackground(String... params) {

        String url = params[0];

       /* Request request = new Request.Builder()
                .url(url)
                .build();*/
        HttpPost myConnection = new HttpPost(url);
        try {

            HttpResponse response = client.execute(myConnection);        // Llamado al Google API
            String str = EntityUtils.toString(response.getEntity(), "UTF-8");
            return parsearResultado(str);      // Convierto el resultado en ArrayList<Direccion>

        } catch (IOException | JSONException e) {
            Log.d("Error",e.getMessage());                          // Error de Network o al parsear JSON
            return new ArrayList<SocialNetwork>();
        }

    }
    ArrayList<SocialNetwork> parsearResultado(String JSONstr) throws JSONException {
        ArrayList<SocialNetwork> lineas = new ArrayList<>();
        JSONObject json = new JSONObject(JSONstr);                 // Convierto el String recibido a JSONObject
        JSONArray jsonLineas = json.getJSONArray("results");  // Array - una busqueda puede retornar varios resultados
        for (int i=0; i<jsonLineas.length(); i++) {
            // Recorro los resultados recibidos
            JSONObject jsonResultado = jsonLineas.getJSONObject(i);
            String jsonAddress = jsonResultado.getString("formatted_address");  // Obtiene la direccion formateada

            JSONObject jsonGeometry = jsonResultado.getJSONObject("geometry");
            JSONObject jsonLocation = jsonGeometry.getJSONObject("location");
            String Ico = jsonLocation.getString("Icono");                     // Obtiene latitud
            String Num = jsonLocation.getString("Numero");                     // Obtiene longitud


            SocialNetwork d = new SocialNetwork(Num, Ico);                    // Creo nueva instancia de direccion
            lineas.add(d);                                                 // Agrego objeto d al array list

        }
        return lineas;
    }

}