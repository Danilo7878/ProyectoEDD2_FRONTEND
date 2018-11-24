package com.example.danil.duckychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.danil.duckychat.models.Usuario;
import com.example.danil.duckychat.services.ProveedorAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class SignUp extends AppCompatActivity {

    private EditText etUser;
    private EditText etPW;
    private EditText etNombre;
    private EditText etApellido;
    private EditText etPuesto;
    private EditText etCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUser = (EditText)findViewById(R.id.editText3);
        etPW = (EditText)findViewById(R.id.editText5);
        etNombre = (EditText)findViewById(R.id.editText8);
        etApellido = (EditText)findViewById(R.id.editText9);
        etPuesto = (EditText)findViewById(R.id.editText10);
        etCorreo = (EditText)findViewById(R.id.editText11);
    }

    public void IngresarUsuario(View view){
        if(etUser.getText().toString().length() > 0 && etNombre.getText().toString().length() > 0 && etPW.getText().toString().length() > 0 &&
                etApellido.getText().toString().length() > 0 && etPuesto.getText().toString().length() > 0 && etCorreo.getText().toString().length() > 0) {
            Usuario user = new Usuario(etUser.getText().toString(), etPW.getText().toString(), etNombre.getText().toString(),
                    etApellido.getText().toString(), etPuesto.getText().toString(), etCorreo.getText().toString()
            );
            ProveedorAPI.getService().createUserBody(user).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(SignUp.this, "Usuario Creado, por favor autentiquese", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignUp.this, MainActivity.class);
                        startActivity(i);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(SignUp.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(SignUp.this, "Debe llenar todos lo campos", Toast.LENGTH_SHORT).show();
        }
    }
}
