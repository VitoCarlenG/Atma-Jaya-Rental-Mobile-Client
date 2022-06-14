package com.vitocarlengiovanni.ajr;

import static com.android.volley.Request.Method.GET;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.vitocarlengiovanni.ajr.adapters.PromoAdapter;
import com.vitocarlengiovanni.ajr.adapters.PromoAdapter;
import com.vitocarlengiovanni.ajr.api.PromoApi;
import com.vitocarlengiovanni.ajr.models.PromoResponse;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PromoActivity extends AppCompatActivity {

    public static final int LAUNCH_ADD_ACTIVITY = 123;

    private SwipeRefreshLayout srPromo;
    private PromoAdapter adapter;
    private SearchView svPromo;
    private LinearLayout layoutLoading;
    private RequestQueue queue;
    private BottomNavigationView bottomNavigationView;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);
        getSupportActionBar().hide();

        long id = getIntent().getLongExtra("tempID", -1);

        // Pendeklarasian request queue
        queue = Volley.newRequestQueue(this);

        layoutLoading = findViewById(R.id.layout_loading);
        srPromo = findViewById(R.id.sr_promo);
        svPromo = findViewById(R.id.sv_promo);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.promo);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        i = new Intent(PromoActivity.this, HomeActivityCustomer.class);
                        i.putExtra("tempID", id);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.brosur:
                        i = new Intent(PromoActivity.this, BrosurActivity.class);
                        i.putExtra("tempID", id);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.promo:
                        return true;
                    case R.id.riwayat_transaksi:
                        i = new Intent(PromoActivity.this, RiwayatTransaksiCustomerActivity.class);
                        i.putExtra("tempID", id);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.profile:
                        i = new Intent(PromoActivity.this, ProfileCustomerActivity.class);
                        i.putExtra("tempID", id);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }
                return false;
            }
        });

        srPromo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllPromo();
            }
        });

        svPromo.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        RecyclerView rvPromo = findViewById(R.id.rv_promo);
        adapter = new PromoAdapter(new ArrayList<>(), this);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            //Grid Layout Portrait
            rvPromo.setLayoutManager(new GridLayoutManager(PromoActivity.this, 1));
        }else{
            //Grid Layout Landscape
            rvPromo.setLayoutManager(new GridLayoutManager(PromoActivity.this, 1));
        }
        rvPromo.setAdapter(adapter);

        getAllPromo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_ADD_ACTIVITY && resultCode == Activity.RESULT_OK)
            getAllPromo();
    }

    private void getAllPromo() {
        srPromo.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(GET, PromoApi.GET_ALL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                 /* Deserialiasai data dari response JSON dari API
                 menjadi java object MahasiswaResponse menggunakan Gson */
                PromoResponse promoResponse = gson.fromJson(response, PromoResponse.class);

                adapter.setPromoList(promoResponse.getPromoList());
                adapter.getFilter().filter(svPromo.getQuery());

                Toast.makeText(PromoActivity.this, promoResponse.getMessage(), Toast.LENGTH_SHORT).show();
                srPromo.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                srPromo.setRefreshing(false);

                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(PromoActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(PromoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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