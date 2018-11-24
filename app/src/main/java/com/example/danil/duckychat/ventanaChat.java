package com.example.danil.duckychat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.danil.duckychat.models.Message;
import com.example.danil.duckychat.services.ProveedorAPI;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ventanaChat extends AppCompatActivity {


    EditText textoIngresar;
    ListView miLista;
    String Emisor = "";
    String Receptor = "";
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

        Receptor = getIntent().getStringExtra("usuario");
        Emisor = getIntent().getStringExtra("usuarioLogeado");

        miLista2.clear();
        ProveedorAPI.getService().Conversation(MainActivity.JWT).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()){
                    List<Message> lista = response.body();
                    for (Message mes : lista){
                        if (mes.getEmisor().equals(Emisor)&&mes.getReceptor().equals(Receptor))
                    {
                        miLista2.add(recibirMensajes(mes.getMensaje(),"Tu"));
                    }
                    else if(Emisor.equals(mes.getReceptor())&& Receptor.equals(mes.getEmisor()))
                    {
                        miLista2.add(recibirMensajes(mes.getMensaje(),mes.getEmisor()));
                    }
                    }
                    miLista.setAdapter(null);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ventanaChat.this, android.R.layout.simple_list_item_1, android.R.id.text1, miLista2);
                    miLista.setAdapter(adapter);
                }
                else {
                    Toast.makeText(ventanaChat.this, "Expiró la sesión", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ventanaChat.this, MainActivity.class);
                    startActivity(i);
                }

            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(ventanaChat.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    public void refresco(View view){
        miLista2.clear();
        ProveedorAPI.getService().Conversation(MainActivity.JWT).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                    List<Message> lista = response.body();
                    for (Message mes : lista){
                        miLista2.add(recibirMensajes(mes.getMensaje(),mes.getMensaje()));
                    }
                    miLista.setAdapter(null);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ventanaChat.this, android.R.layout.simple_list_item_1, android.R.id.text1, miLista2);
                    miLista.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    public void enviarMensaje(View view)
    {
        //este string es el que va cifrado
        String mensajeCifrado = retorno(textoIngresar.getText().toString(),5);
        Message mens = new Message(Emisor,Receptor,mensajeCifrado);
        ProveedorAPI.getService().createMessageBody(mens, MainActivity.JWT).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(ventanaChat.this, "Mensaje enviado", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ventanaChat.this, "Expiró la sesión", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ventanaChat.this, MainActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ventanaChat.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        miLista2.add(mostarListView(Emisor,textoIngresar.getText().toString()));
        refresh();
    }

    //para que los mensajes nuevos aparezcan
    public void refresh()
    {

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
    public String recibirMensajes(String textoCifrado,String emisor)
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

    public String comprimirImagen(String ruta)
    {
        if(ruta.endsWith("jpg")|| ruta.endsWith("JPG"))
        {
            Bitmap bm = BitmapFactory.decodeFile(ruta);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] b = baos.toByteArray();
            String encodedImage = android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT);
            return encodedImage;
        }
        else
        {
            Bitmap bm = BitmapFactory.decodeFile(ruta);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG,100,baos);
            byte[] b = baos.toByteArray();
            String encodedImage = android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT);
            return encodedImage;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void DescomprimirImagen(String imagen, String ruta)
    {
        if(ruta.endsWith("jpg")|| ruta.endsWith("JPG"))
        {
            byte[] decodedString = android.util.Base64.decode(imagen, android.util.Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            try(FileOutputStream out = new FileOutputStream(ruta))
            {
                bmp.compress(Bitmap.CompressFormat.JPEG,100,out);
            }
             catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else
        {
            byte[] decodedString = android.util.Base64.decode(imagen, android.util.Base64.DEFAULT);

            Bitmap bmp = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            try(FileOutputStream out = new FileOutputStream(ruta)) {

                bmp.compress(Bitmap.CompressFormat.PNG,100,out);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
//lol
}
