package com.example.danil.duckychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class SignUp extends AppCompatActivity {

    private RequestQueue requestQueue;
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

        requestQueue = Volley.newRequestQueue(this);
        etUser = (EditText)findViewById(R.id.editText3);
        etPW = (EditText)findViewById(R.id.editText5);
        etNombre = (EditText)findViewById(R.id.editText8);
        etApellido = (EditText)findViewById(R.id.editText9);
        etPuesto = (EditText)findViewById(R.id.editText10);
        etCorreo = (EditText)findViewById(R.id.editText11);
    }

    public void IngresarUsuario(View view) throws JSONException {
        String url = "http://192.168.1.57:3000/signup";
        JSONObject body = new JSONObject();
        body.put("username", etUser.getText().toString());
        body.put("password", etPW.getText().toString());
        body.put("nombre", etNombre.getText().toString());
        body.put("apellido", etApellido.getText().toString());
        body.put("puesto", etPuesto.getText().toString());
        body.put("correo", etCorreo.getText().toString());

        final String requestBody = body.toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);

    }
}
