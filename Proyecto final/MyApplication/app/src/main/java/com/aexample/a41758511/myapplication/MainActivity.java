package com.aexample.a41758511.myapplication;

import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
   public static String nombre;
    public static List<SocialNetwork> lislin = new ArrayList<>();
    public static Spinner spinner;
    public static SocialNetworkSpinnerAdapter adapter;
    Button btnSubidas;
    Button btnCercano;
public static Button btn;
    public static Context ct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner=(Spinner)findViewById(R.id.spinner);

        List<SocialNetwork> items = new ArrayList<SocialNetwork>(22);

        //   items.add(new SocialNetwork(getString(R.string.none), R.drawable.ninguno));
     //   new ProgressTask(MainActivity.this,spinner).execute("bdalex.hol.es/bd/listarlineas.php");
        new ProgressTask(MainActivity.this,spinner).execute("http://bdalex.hol.es/bd/listarlineas.php");
        //ct=getApplicationContext();
        try {
            Thread.sleep(500);
        }catch (Exception e){


        }
        adapter = new SocialNetworkSpinnerAdapter(getApplicationContext(),lislin);
        spinner.setAdapter(adapter);
        btnSubidas=(Button) findViewById(R.id.button2);

btn=(Button) findViewById(R.id.button);
        MainActivity.btn.setEnabled(true);

        MainActivity.btn.setClickable(true);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

                if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    buildAlertMessageNoGps();
                }
                else {


                    Intent i = new Intent(MainActivity.this, Mapa.class);
                    //     Bundle b=new Bundle();
                    //    b.putInt("Linea",nombre);
                    //    i.putExtras(b);
                    startActivity(i);
                    MainActivity.btn.setEnabled(false);
                    MainActivity.btn.setClickable(false);
                }
            }
        });

btnSubidas.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent i=new Intent(MainActivity.this,ListSubidas.class);

        startActivity(i);
    }
});

btnCercano=(Button) findViewById(R.id.btnCercano);
        btnCercano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

                if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    buildAlertMessageNoGps();
                }
                else {
                    Intent i = new Intent(MainActivity.this, Cercano.class);

                    startActivity(i);
                }
            }
        });

      //  spinner = (Spinner) findViewById(R.id.spinner);
      //  spinner.setAdapter(new SocialNetworkSpinnerAdapter(this,items));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
              //  nombre= (Integer) adapterView.getItemAtPosition(position);
                adapterView.setSelection(position);
                nombre=((SocialNetwork) adapterView.getItemAtPosition(position)).getNombre();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                //nothing
            }
        });
        }
    private void buildAlertMessageNoGps(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Su ubicacion esta desactivada, desea activarla?")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}

