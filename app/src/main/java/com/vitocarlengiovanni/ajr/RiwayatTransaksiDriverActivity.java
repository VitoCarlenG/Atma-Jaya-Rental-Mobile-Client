package com.vitocarlengiovanni.ajr;


import static com.android.volley.Request.Method.GET;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.vitocarlengiovanni.ajr.adapters.PromoAdapter;
import com.vitocarlengiovanni.ajr.adapters.TransaksiAdapter;
import com.vitocarlengiovanni.ajr.api.PromoApi;
import com.vitocarlengiovanni.ajr.models.PromoResponse;
import com.vitocarlengiovanni.ajr.models.Transaksi;
import com.vitocarlengiovanni.ajr.models.TransaksiResponse;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RiwayatTransaksiDriverActivity extends AppCompatActivity {

    public static final int LAUNCH_ADD_ACTIVITY = 123;

    private SwipeRefreshLayout srTransaksi;
    private TransaksiAdapter adapter;
    private SearchView svTransaksi;
    private LinearLayout layoutLoading;
    private RequestQueue queue;
    private BottomNavigationView bottomNavigationView;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_transaksi_driver);
        getSupportActionBar().hide();

        long id = getIntent().getLongExtra("tempIDDriver", -1);

        // Pendeklarasian request queue
        queue = Volley.newRequestQueue(this);

        layoutLoading = findViewById(R.id.layout_loading);
        srTransaksi = findViewById(R.id.sr_transaksi);
        svTransaksi = findViewById(R.id.sv_transaksi);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.riwayat_transaksi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        i = new Intent(RiwayatTransaksiDriverActivity.this, HomeActivityDriver.class);
                        i.putExtra("tempIDDriver", id);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.riwayat_transaksi:
                        return true;
                    case R.id.profile:
                        i = new Intent(RiwayatTransaksiDriverActivity.this, ProfileDriverActivity.class);
                        i.putExtra("tempIDDriver", id);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }
                return false;
            }
        });

        srTransaksi.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllTransaksi(id);
            }
        });

        svTransaksi.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        RecyclerView rvTransaksi = findViewById(R.id.rv_transaksi);
        adapter = new TransaksiAdapter(new ArrayList<>(), this);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            //Grid Layout Portrait
            rvTransaksi.setLayoutManager(new GridLayoutManager(RiwayatTransaksiDriverActivity.this, 1));
        }else{
            //Grid Layout Landscape
            rvTransaksi.setLayoutManager(new GridLayoutManager(RiwayatTransaksiDriverActivity.this, 1));
        }
        rvTransaksi.setAdapter(adapter);

        getAllTransaksi(id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        long id = getIntent().getLongExtra("tempIDDriver", -1);

        if (requestCode == LAUNCH_ADD_ACTIVITY && resultCode == Activity.RESULT_OK)
            getAllTransaksi(id);
    }

    private void getAllTransaksi(long id) {
        srTransaksi.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(GET, "https://atmajayarental.xyz/api/transaksi/" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                 /* Deserialiasai data dari response JSON dari API
                 menjadi java object MahasiswaResponse menggunakan Gson */
                TransaksiResponse transaksiResponse = gson.fromJson(response, TransaksiResponse.class);

                adapter.setTransaksiList(transaksiResponse.getTransaksiList());
                adapter.getFilter().filter(svTransaksi.getQuery());

                Toast.makeText(RiwayatTransaksiDriverActivity.this, transaksiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                srTransaksi.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                srTransaksi.setRefreshing(false);

                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(RiwayatTransaksiDriverActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(RiwayatTransaksiDriverActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    // Fungsi ini digunakan menampilkan layout loading
    private void setLoading(boolean isLoading) {
        if (isLoading) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.VISIBLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.GONE);
        }
    }
}