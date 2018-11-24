package com.example.danil.duckychat;

import android.content.Intent;
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
import com.example.danil.duckychat.models.Usuario;
import com.example.danil.duckychat.services.API;
import com.example.danil.duckychat.services.ProveedorAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Contactos extends AppCompatActivity {

    ListView listaContactos;
    public static List<String> miLista2 = new ArrayList<>();
    String usuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        usuario = getIntent().getStringExtra("usuarioLogeado");
        ProveedorAPI.getService().TodosLosUsuarios(MainActivity.JWT).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, retrofit2.Response<List<Usuario>> response) {
                if(response.isSuccessful()) {
                    miLista2.clear();
                    List<Usuario> lista = response.body();
                    for (Usuario user : lista) {
                        miLista2.add(user.getNombre());
                    }
                    listaContactos = (ListView) findViewById(R.id.lvContactos);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Contactos.this, android.R.layout.simple_list_item_1, android.R.id.text1, miLista2);
                    listaContactos.setAdapter(adapter);
                    listaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            String nombre = (String) arg0.getItemAtPosition(arg2);
                            //String nombre = arg0.getItemAtPosition(arg2).toString();
                            Intent cambio = new Intent(Contactos.this, ventanaChat.class);
                            cambio.putExtra("usuario", nombre);
                            cambio.putExtra("usuarioLogeado", usuario);
                            startActivity(cambio);
                        }
                    });
                }
                else {
                    Toast.makeText(Contactos.this, "Expiró la sesión", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Contactos.this, MainActivity.class);
                    startActivity(i);
                }
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(Contactos.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void cambioVentana(String nombres)
    {
        Intent cambio = new Intent(this,ventanaChat.class);
        cambio.putExtra("usuario",nombres);
        cambio.putExtra("usuarioLogeado",usuario);
        startActivity(cambio);

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

    //Agregar a la lista uno por uno, solo lo mandas a llamar
    public void agregarLista(String usuarioAgregar)
    {
        miLista2.add(usuarioAgregar);
    }
}


