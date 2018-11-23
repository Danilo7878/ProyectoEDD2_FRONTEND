package com.example.danil.duckychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText etUser;
    private EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUser = (EditText)findViewById(R.id.editText);
        etPass = (EditText)findViewById(R.id.editText2);
    }

    public void iniciarSesión(View view){
        String usuario = etUser.getText().toString();
        String password_NoCifrado = etPass.getText().toString();

        Intent contactosCambio = new Intent(this,Contactos.class);
        startActivity(contactosCambio);
    }

    public void Registrarse(View view){
        Intent signup = new Intent(this, SignUp.class);
        startActivity(signup);
    }
}
