package com.vitocarlengiovanni.ajr;


import static com.android.volley.Request.Method.GET;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.vitocarlengiovanni.ajr.models.Driver;
import com.vitocarlengiovanni.ajr.models.DriverResponse;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProfileDriverActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView tvID, tvNama, tvAlamat, tvTelepon, tvEmail, tvRating, tvStatus;
    private RequestQueue queue;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_driver);
        getSupportActionBar().hide();

        long id = getIntent().getLongExtra("tempIDDriver", -1);

        // Pendeklarasian request queue
        queue = Volley.newRequestQueue(this);

        tvID = findViewById(R.id.tv_id);
        tvNama = findViewById(R.id.tv_nama);
        tvAlamat = findViewById(R.id.tv_alamat);
        tvTelepon = findViewById(R.id.tv_telepon);
        tvEmail = findViewById(R.id.tv_email);
        tvRating = findViewById(R.id.tv_rating);
        tvStatus = findViewById(R.id.tv_status);

        getDriverById(id);

        Button btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(ProfileDriverActivity.this, UpdateDriverActivity.class);
                i.putExtra("tempIDDriver", id);
                startActivity(i);
                overridePendingTransition(0,0);
                finish();
            }
        });

        Button btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        i = new Intent(ProfileDriverActivity.this, HomeActivityDriver.class);
                        i.putExtra("tempIDDriver", id);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.riwayat_transaksi:
                        i = new Intent(ProfileDriverActivity.this, RiwayatTransaksiDriverActivity.class);
                        i.putExtra("tempIDDriver", id);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });
    }

    private void getDriverById(long id) {

        StringRequest stringRequest = new StringRequest(GET, "https://atmajayarental.xyz/api/driver/" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API menjadi java object ProdukResponse menggunakan Gson */
                DriverResponse driverResponse = gson.fromJson(response, DriverResponse.class);

                Driver driver = driverResponse.getDriverList().get(0);
                tvID.setText(driver.getFormat_id_driver()+String.valueOf(driver.getId_driver()));
                tvNama.setText(driver.getNama_driver());
                tvAlamat.setText(driver.getAlamat_driver());
                tvTelepon.setText(driver.getNomor_telepon_driver());
                tvEmail.setText(driver.getEmail_driver());
                tvRating.setText(String.valueOf(driver.getRerata_rating_driver())+" ★");

                if(driver.getStatus_ketersediaan_driver()==0) {
                    tvStatus.setText("Tidak Tersedia");
                }else {
                    tvStatus.setText("Tersedia");
                }

                Toast.makeText(ProfileDriverActivity.this, driverResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(ProfileDriverActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ProfileDriverActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            // Menambahkan header pada request
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        // Menambahkan request ke request queue
        queue.add(stringRequest);
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        // set title dialog
        alertDialogBuilder.setTitle("Atma Jaya Rental");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah Anda Yakin Ingin Logout?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        startActivity(new Intent(ProfileDriverActivity.this, MainActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
}