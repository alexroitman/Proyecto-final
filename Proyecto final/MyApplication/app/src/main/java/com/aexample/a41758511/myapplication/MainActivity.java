package com.aexample.a41758511.myapplication;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Integer nombre;
    private Spinner spinner;
Button btn;
    public static Context ct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<SocialNetwork> items = new ArrayList<SocialNetwork>(15);
        //   items.add(new SocialNetwork(getString(R.string.none), R.drawable.ninguno));
     List<SocialNetwork> lisLin= (List<SocialNetwork>) new ProgressTask(MainActivity.this).execute("http://bdalex.hol.es/bd/listar.php");
        ct=getApplicationContext();
/*
        items.add(new SocialNetwork("Seleccione una linea",R.drawable.ic_play_light));
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
        items.add(new SocialNetwork("Linea 76", R.drawable.a76));
*/
btn=(Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,MapsActivity.class);
                Bundle b=new Bundle();
                b.putInt("Linea",nombre);
                i.putExtras(b);
                startActivity(i);
            }
        });






        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new SocialNetworkSpinnerAdapter(this,lisLin));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                nombre=((SocialNetwork) adapterView.getItemAtPosition(position)).getNombre();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                //nothing
            }
        });
        }


}

