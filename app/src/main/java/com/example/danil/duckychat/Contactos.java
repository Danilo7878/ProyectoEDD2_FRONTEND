package com.example.danil.duckychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Contactos extends AppCompatActivity {

    ListView listaContactos;
    public static List<String> miLista2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);


        listaContactos = (ListView) findViewById(R.id.lvContactos);

        miLista2.add("Edwin");
        miLista2.add("Alex");
        miLista2.add("Salvados");
        miLista2.add("Ashly");
        miLista2.add("Iza");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, miLista2);
        listaContactos.setAdapter(adapter);
        listaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                // Poner toda tu lógica aquí.
                String nombre = (String) arg0.getItemAtPosition(arg2);
                Toast.makeText(Contactos.this,("Este es una prueba: " + nombre), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Agregar a la lista uno por uno, solo lo mandas a llamar
    public void agregarLista(String usuarioAgregar)
    {
        miLista2.add(usuarioAgregar);
    }
}
