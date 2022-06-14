package com.vitocarlengiovanni.ajr;

import static com.android.volley.Request.Method.GET;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.vitocarlengiovanni.ajr.api.BrosurApi;
import com.vitocarlengiovanni.ajr.models.Brosur;
import com.vitocarlengiovanni.ajr.models.BrosurResponse;
import com.vitocarlengiovanni.ajr.models.Customer;
import com.vitocarlengiovanni.ajr.models.CustomerResponse;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProfileCustomerActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView tvID, tvNama, tvAlamat, tvTelepon, tvEmail;
    private RequestQueue queue;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_customer);
        getSupportActionBar().hide();

        long id = getIntent().getLongExtra("tempID", -1);

        // Pendeklarasian request queue
        queue = Volley.newRequestQueue(this);

        tvID = findViewById(R.id.tv_id);
        tvNama = findViewById(R.id.tv_nama);
        tvAlamat = findViewById(R.id.tv_alamat);
        tvTelepon = findViewById(R.id.tv_telepon);
        tvEmail = findViewById(R.id.tv_email);

        getCustomerById(id);

        Button btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(ProfileCustomerActivity.this, UpdateCustomerActivity.class);
                i.putExtra("tempID", id);
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
                        i = new Intent(ProfileCustomerActivity.this, HomeActivityCustomer.class);
                        i.putExtra("tempID", id);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.brosur:
                        i = new Intent(ProfileCustomerActivity.this, BrosurActivity.class);
                        i.putExtra("tempID", id);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.promo:
                        i = new Intent(ProfileCustomerActivity.this, PromoActivity.class);
                        i.putExtra("tempID", id);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.riwayat_transaksi:
                        i = new Intent(ProfileCustomerActivity.this, RiwayatTransaksiCustomerActivity.class);
                        i.putExtra("tempID", id);
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

    private void getCustomerById(long id) {

        StringRequest stringRequest = new StringRequest(GET, "https://atmajayarental.xyz/api/customer/" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API menjadi java object ProdukResponse menggunakan Gson */
                CustomerResponse customerResponse = gson.fromJson(response, CustomerResponse.class);

                Customer customer = customerResponse.getCustomerList().get(0);
                tvID.setText(customer.getFormat_id_customer()+String.valueOf(customer.getId_customer()));
                tvNama.setText(customer.getNama_customer());
                tvAlamat.setText(customer.getAlamat_customer());
                tvTelepon.setText(customer.getNomor_telepon_customer());
                tvEmail.setText(customer.getEmail_customer());

                Toast.makeText(ProfileCustomerActivity.this, customerResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(ProfileCustomerActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ProfileCustomerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(ProfileCustomerActivity.this, MainActivity.class));
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