package com.example.danil.duckychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Contactos extends AppCompatActivity {

    ListView listaContactos;
    public static List<String> miLista2 = new ArrayList<>();
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        requestQueue = Volley.newRequestQueue(this);
        ObtenerContactos();
        listaContactos = (ListView) findViewById(R.id.lvContactos);


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

    //Mostrar y ocultar el menú
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    //Asignar funciones a los item del menú
    public  boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.itemsignout){
            Toast.makeText(this, "Bryan Hueco", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.itemdelete){
            Toast.makeText(this, "Hilario Hueco", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    private void ObtenerContactos(){
        String url = "http://192.168.43.214:3000/users";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray myJsonArray = response.getJSONArray("users");
                    for(int i = 0; i <myJsonArray.length();i++) {
                        JSONObject usuarioObjeto = myJsonArray.getJSONObject(i);
                        String Username = usuarioObjeto.getString("username");
                        miLista2.add(Username);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        requestQueue.add(request);
    }

    //Agregar a la lista uno por uno, solo lo mandas a llamar
    public void agregarLista(String usuarioAgregar)
    {
        miLista2.add(usuarioAgregar);
    }
}


