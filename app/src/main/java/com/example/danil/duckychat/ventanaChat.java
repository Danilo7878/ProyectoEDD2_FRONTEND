package com.example.danil.duckychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ventanaChat extends AppCompatActivity {


    EditText textoIngresar;
    ListView miLista;
    String Emisor;
    String Receptor;
    Button refrescar;
    public static int numero =0;
    String nombre;
    public static List<String> miLista2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_chat);


        textoIngresar = (EditText) findViewById(R.id.txtMensaje);
        miLista = (ListView) findViewById(R.id.lvChats);

        refrescar = (Button) findViewById(R.id.btnRefresh);
        refrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                refresh2();
            }
        });

        miLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                // Poner toda tu lógica aquí.
                nombre = (String) arg0.getItemAtPosition(arg2);
                numero = arg2;
            }
        });

    }


    //Aca solo falta cifrar para que lo mande a la base de datos
    public void enviarMensaje(View view)
    {
        //este estring es el que va cifrado
        String mensajeCifrado = retorno(textoIngresar.getText().toString(),5);
        miLista2.add(mostarListView(Emisor,textoIngresar.getText().toString()));
        refresh();
    }

    //para que los mensajes nuevos aparezcan
    public void refresh()
    {
        miLista.setAdapter(null);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, miLista2);
        miLista.setAdapter(adapter);
    }

    //no lo tomes en cuenta es prueba
    public void refresh2()
    {
        if(numero!=0)
        {
            Toast.makeText(this,("Este es una prueba: " + nombre),Toast.LENGTH_SHORT).show();
        }
    }

    //Solo genera un string para el listview
    public String mostarListView(String Emisor, String mensaje)
    {
        return (Emisor + ": " + mensaje);
    }

    //Descifra el mensaje man
    public String recibirMensajes(String textoCifrado, String receptor,String emisor)
    {
        Cifrado miDescifrado = new Cifrado();

        String texto= emisor+": " + miDescifrado.Descifrar(textoCifrado,5);
        return  texto;
    }

    //Retorna un texto cifrado
    public String retorno(String mensaje, int nivel2)
    {
        String numeros = String.valueOf(nivel2);
        int nivel = Integer.valueOf(numeros);
        int numCaracteres= (nivel-2)+nivel;
        String todo = mensaje;
        int numero = todo.length()%numCaracteres;
        while(numero!=0)
        {
            todo = todo+"%";
            numero = todo.length()%numCaracteres;
        }
        Cifrado miCifrado = new Cifrado(todo,nivel);

        return miCifrado.getCadenaCifrada();
    }

}
