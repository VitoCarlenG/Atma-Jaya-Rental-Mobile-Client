package com.vitocarlengiovanni.ajr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivityCustomer extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_customer);
        getSupportActionBar().hide();

        long id = getIntent().getLongExtra("tempID", -1);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.brosur:
                        i = new Intent(HomeActivityCustomer.this, BrosurActivity.class);
                        i.putExtra("tempID", id);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.promo:
                        i = new Intent(HomeActivityCustomer.this, PromoActivity.class);
                        i.putExtra("tempID", id);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.riwayat_transaksi:
                        i = new Intent(HomeActivityCustomer.this, RiwayatTransaksiCustomerActivity.class);
                        i.putExtra("tempID", id);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.profile:
                        i = new Intent(HomeActivityCustomer.this, ProfileCustomerActivity.class);
                        i.putExtra("tempID", id);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }
}