package com.vitocarlengiovanni.ajr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText emailET, passwordET;
    private Button clearField, login;
    private Intent intent;
    private static String URL_LOGIN = "https://atmajayarental.xyz/api/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        emailET = findViewById(R.id.email);
        passwordET = findViewById(R.id.password);
        clearField = findViewById(R.id.clearField);
        login = findViewById(R.id.login);

        clearField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailET.getText().clear();
                passwordET.getText().clear();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();

                Login(email, password);
            }
        });
    }

    private void Login(final String email, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String user = jsonObject.getString("user");
                            long id = jsonObject.getLong("id");
                            String token_type = jsonObject.getString("token_type");
                            String access_token = jsonObject.getString("access_token");

                            if(message.equals("Authenticated as Customer")) {
                                Intent i = new Intent(MainActivity.this, HomeActivityCustomer.class);
                                i.putExtra("tempID", id);
                                startActivity(i);
                                overridePendingTransition(0, 0);
                                finish();

                                Toast.makeText(MainActivity.this, "Authenticated as Customer", Toast.LENGTH_SHORT).show();
                            }else if(message.equals("Authenticated as Driver")) {
                                Intent i = new Intent(MainActivity.this, HomeActivityDriver.class);
                                i.putExtra("tempIDDriver", id);
                                startActivity(i);
                                overridePendingTransition(0, 0);
                                finish();

                                Toast.makeText(MainActivity.this, "Authenticated as Driver", Toast.LENGTH_SHORT).show();
                            }else if(message.equals("Authenticated as Manager")) {
                                Intent i = new Intent(MainActivity.this, HomeActivityManager.class);
                                startActivity(i);
                                overridePendingTransition(0, 0);
                                finish();

                                Toast.makeText(MainActivity.this, "Authenticated as Manager", Toast.LENGTH_SHORT).show();
                            }

                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Authentication Failed", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}