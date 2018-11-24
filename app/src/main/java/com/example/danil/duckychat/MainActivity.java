package com.example.danil.duckychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danil.duckychat.services.ProveedorAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etUser;
    private EditText etPass;
    String usuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUser = (EditText)findViewById(R.id.editText);
        etPass = (EditText)findViewById(R.id.editText2);
    }

    public void iniciarSesión(View view){
        usuario =etUser.getText().toString();
        String password_NoCifrado = etPass.getText().toString();

        ProveedorAPI.getService().login(usuario, password_NoCifrado).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Intent contactosCambio = new Intent(MainActivity.this,Contactos.class);
                    contactosCambio.putExtra("usuarioLogeado", usuario);
                    startActivity(contactosCambio);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Registrarse(View view){
        Intent signup = new Intent(this, SignUp.class);
        startActivity(signup);
    }
}
