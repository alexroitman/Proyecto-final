package com.aexample.a41758511.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aexample.a41758511.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/*public class ListViewAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    List<String> titulos;
    List<Integer> imagenes;
    LayoutInflater inflater;

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    public ListViewAdapter(Context context, List<String> titulos, List<Integer> imagenes) {
        super();
        this.context = context;
        this.titulos = titulos;
        this.imagenes = imagenes;
    }

    @Override
    public int getCount() {
        return titulos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtTitle;
        ImageView imgImg;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.activity_list_row, parent, false);

        // Locate the TextViews in listview_item.xml
        txtTitle = (TextView) itemView.findViewById(R.id.list_row_title);
        imgImg = (ImageView) itemView.findViewById(R.id.list_row_image);

        // Capture position and set to the TextViews
        txtTitle.setText(titulos.get(position));
        imgImg.setImageResource(imagenes.get(position));

        return itemView;
    }
}*/
public class ListViewAdapter extends BaseAdapter{

    protected Activity activity;
    //ARRAYLIST CON TODOS LOS ITEMS
    protected ArrayList<Subidas> items;

    //CONSTRUCTOR
    public ListViewAdapter(Activity activity, ArrayList<Subidas> items) {
        this.activity = activity;
        this.items = items;
    }
    //CUENTA LOS ELEMENTOS
    @Override
    public int getCount() {
        return items.size();
    }
    //DEVUELVE UN OBJETO DE UNA DETERMINADA POSICION
    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }
    //DEVUELVE EL ID DE UN ELEMENTO
    @Override
    public long getItemId(int position) {
        return Integer.parseInt(items.get(position).getId());
    }
    //METODO PRINCIPAL, AQUI SE LLENAN LOS DATOS
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // SE GENERA UN CONVERTVIEW POR MOTIVOS DE EFICIENCIA DE MEMORIA
        //ES UN NIVEL MAS BAJO DE VISTA, PARA QUE OCUPEN MENOS MEMORIA LAS   IMAGENES


        View v = convertView;
        //ASOCIAMOS LA VISTA AL LAYOUT DEL RECURSO XML DONDE ESTA LA BASE DE  CADA ITEM


        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService

                    (Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.activity_list_row, null);
        }

        Subidas dir = items.get(position);
        //RELLENAMOS LA IMAGEN Y EL TEXTO
        ImageView foto = (ImageView) v.findViewById(R.id.list_row_image);
        Resources res = ListSubidas.ctxSub.getResources();
        Integer ic = res.getIdentifier("a"+dir.IdLinea.toString(), "drawable", ListSubidas.ctxSub.getApplicationContext().getPackageName());
        ImageView ira = (ImageView) v.findViewById(R.id.imageView2);
        foto.setImageResource(ic);
        TextView nombre = (TextView) v.findViewById(R.id.list_row_title);
        nombre.setText(dir.getTexto());
        TextView txtCond=(TextView) v.findViewById(R.id.txtCond);
        TextView txtId=(TextView) v.findViewById(R.id.tvId);
        txtCond.setText(dir.getCond());
        txtId.setText(dir.IdSubida);


        // DEVOLVEMOS VISTA
        return v;
    }
}
