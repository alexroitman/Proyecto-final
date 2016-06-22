package com.aexample.a41758511.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ListSubidas extends AppCompatActivity {
ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_subidas);
        lv=(ListView) findViewById(R.id.listView);
        new SubidasTask(getApplicationContext(),lv).execute("http://bdalex.hol.es/bd/ListarSubidas.php?IdLinea=");
    }
}
