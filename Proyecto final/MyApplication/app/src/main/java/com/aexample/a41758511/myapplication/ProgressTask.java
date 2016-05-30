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
        import org.apache.http.client.methods.HttpGet;
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

class ProgressTask extends AsyncTask<String, Void, List<SocialNetwork>> {
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

    Spinner spinner ;
    protected void onPostExecute(  List<SocialNetwork> lislin) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        spinner = (Spinner) spinner.findViewById(R.id.spinner);
        spinner.setAdapter(new SocialNetworkSpinnerAdapter(MainActivity.ct,lislin));
        //lv = getListView();


    }
   private OkHttpClient client = new OkHttpClient();
    @Override
    protected List<SocialNetwork> doInBackground(String... params) {
        String url = params[0];

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
           List<SocialNetwork> li=parsearResultado(response.body().string());
            return li;
        } catch (IOException | JSONException e) {
            Log.d("Error", e.getMessage());
            return new ArrayList<SocialNetwork>();
        }
    }

    List<SocialNetwork> parsearResultado(String JSONstr) throws JSONException {
        ArrayList<SocialNetwork> eventos = new ArrayList<>();
        JSONArray jsonEventos = new JSONArray(JSONstr);
        List<SocialNetwork> lin= new ArrayList<SocialNetwork>();
        for (int i = 0; i < jsonEventos.length(); i++) {
            JSONObject jsonResultado = jsonEventos.getJSONObject(i);
            int id = jsonResultado.getInt("Id");
            Integer Numero = jsonResultado.getInt("Numero");
            String Icono = jsonResultado.getString("Imagen");


         //   SocialNetwork e = new SocialNetwork(Numero,Icono);
            lin.add(new SocialNetwork(Numero, "@R.drawable."+Icono));
         //   eventos.add(e);
        }
        return lin;
    }


    }