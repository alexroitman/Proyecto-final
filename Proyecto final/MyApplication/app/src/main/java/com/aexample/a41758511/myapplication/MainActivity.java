package com.aexample.a41758511.myapplication;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   //     List<SocialNetwork> items = new ArrayList<SocialNetwork>(15);
        //   items.add(new SocialNetwork(getString(R.string.none), R.drawable.ninguno));
        new ProgressTask(MainActivity.this).execute("http://localhost:11504/api/linea");


       /* items.add(new SocialNetwork("Seleccione una linea",R.drawable.ic_play_light));
        items.add(new SocialNetwork("Linea 15", R.drawable.a15));
        items.add(new SocialNetwork("Linea 36", R.drawable.a36));
        items.add(new SocialNetwork("Linea 55", R.drawable.a55));
        items.add(new SocialNetwork("Linea 71", R.drawable.a71));
        items.add(new SocialNetwork("Linea 92", R.drawable.a92));
        items.add(new SocialNetwork("Linea 110", R.drawable.a110));
        items.add(new SocialNetwork("Linea 111", R.drawable.a111));
        items.add(new SocialNetwork("Linea 141", R.drawable.a141));
        items.add(new SocialNetwork("Linea 106", R.drawable.a106));
        items.add(new SocialNetwork("Linea 109", R.drawable.a109));
        items.add(new SocialNetwork("Linea 134", R.drawable.a134));
              items.add(new SocialNetwork("Linea 24", R.drawable.a24));
        items.add(new SocialNetwork("Linea 34", R.drawable.a34));
        items.add(new SocialNetwork("Linea 36", R.drawable.a36));
        items.add(new SocialNetwork("Linea 53", R.drawable.a53));
        items.add(new SocialNetwork("Linea 99", R.drawable.a99));
        items.add(new SocialNetwork("Linea 90", R.drawable.a90));
        items.add(new SocialNetwork("Linea 140", R.drawable.a140));
        items.add(new SocialNetwork("Linea 160", R.drawable.a160));
        items.add(new SocialNetwork("Linea 166", R.drawable.a166));
        items.add(new SocialNetwork("Linea 76", R.drawable.a76));*/
btn=(Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,MapsActivity.class);
                startActivity(i);
            }
        });






        /*spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new SocialNetworkSpinnerAdapter(this,items));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                Toast.makeText(adapterView.getContext(), ((SocialNetwork) adapterView.getItemAtPosition(position)).getNombre(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                //nothing
            }
        });*/
        }
    private static final String numero = "numero";
    private static final String icono = "icono";
    private static final String fuel = "fuel";

    List<SocialNetwork> jsonlist = new ArrayList<SocialNetwork>();

    Spinner spin ;
    private class ProgressTask extends AsyncTask<String, Void, Boolean> {
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

        @Override
        protected void onPostExecute(final Boolean success) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            SpinnerAdapter adapter = new SocialNetworkSpinnerAdapter(context, jsonlist);
            spin.setAdapter(adapter);
            //lv = getListView();


        }

        protected Boolean doInBackground(final String... args) {
Log.d("Hola"en doin);
            JsonParser jParser = new JsonParser();
            JSONArray json = jParser.getJSONFromUrl("http://basededatosremotas.meximas.com/ramiroconnect/get_all_empresas.php");

            for (int i = 0; i < json.length(); i++) {

                try {
                    JSONObject c = json.getJSONObject(i);
                    String vLinea = c.getString(numero);

                    String vIcono = c.getString(icono);


           //         HashMap<String, String> map = new HashMap<String, String>();

                    jsonlist.add(new SocialNetwork(vLinea,vIcono));




                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            return null;

        }

    }
    }
