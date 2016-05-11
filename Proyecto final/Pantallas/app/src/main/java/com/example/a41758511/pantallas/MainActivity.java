package com.example.a41758511.pantallas;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Linea> lista=new ArrayList<Linea>();
        Linea linea1=new Linea();
        linea1.Nombre="15";
        linea1.Foto="Foto15.jpg";
        lista.add(linea1);

// Selection of the spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setAdapter(new MyCustomSpinnerAdapter(this, R.layout.layoutspinner, this.diasSemana));
// Application of the Array to the Spinner
        ArrayAdapter<Linea> spinnerArrayAdapter = new ArrayAdapter<Linea>(this,R.layout.layoutspinner,lista);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.layoutspinner); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
    }
    public class MyCustomSpinnerAdapter extends ArrayAdapter
    {
        private Context contexto;
        private ArrayList lista;

        public MyCustomSpinnerAdapter(Context context, int textViewResourceId,ArrayList items) {
            super(context, textViewResourceId, items);
            this.contexto = context;
            this.lista = items;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflador = this.contexto.getLayoutInflater();
            View fila = inflador.inflate(R.layout.layoutspinner, parent, false);
            TextView texto = (TextView) fila.findViewById(R.id.weekofday);
            texto.setText(linea[position]);

            ImageView icono = (ImageView) fila.findViewById(R.id.icon);

            if (diaSemana[position].equals("Sunday")){
                icono.setImageResource(R.drawable.icon);
            }else{
                icono.setImageResource(R.drawable.icongray);
            }

            return fila;
        }
    }
}
}
