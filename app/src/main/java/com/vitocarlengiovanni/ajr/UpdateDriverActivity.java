package com.vitocarlengiovanni.ajr;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.PUT;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.vitocarlengiovanni.ajr.api.BrosurApi;
import com.vitocarlengiovanni.ajr.models.Brosur;
import com.vitocarlengiovanni.ajr.models.BrosurResponse;
import com.vitocarlengiovanni.ajr.models.Driver;
import com.vitocarlengiovanni.ajr.models.DriverResponse;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UpdateDriverActivity extends AppCompatActivity {

    private EditText etNamaDriver, etAlamatDriver, etEmailDriver, etNomorTeleponDriver, etPasswordDriver;
    private RadioButton radioButtonTersedia, radioButtonTidakTersedia;
    private LinearLayout layoutLoading;
    private RequestQueue queue;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_driver);
        getSupportActionBar().hide();

        long id = getIntent().getLongExtra("tempIDDriver", -1);

        // Pendeklarasian request queue
        queue = Volley.newRequestQueue(this);

        etNamaDriver = findViewById(R.id.et_nama_driver);
        etAlamatDriver = findViewById(R.id.et_alamat_driver);
        etEmailDriver = findViewById(R.id.et_email_driver);
        etNomorTeleponDriver = findViewById(R.id.et_nomor_telepon_driver);
        radioButtonTersedia = findViewById(R.id.radio_button_tersedia);
        radioButtonTidakTersedia = findViewById(R.id.radio_button_tidak_tersedia);
        etPasswordDriver = findViewById(R.id.et_password_driver);
        layoutLoading = findViewById(R.id.layout_loading);

        Button btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(UpdateDriverActivity.this, ProfileDriverActivity.class);
                i.putExtra("tempIDDriver", id);
                startActivity(i);
                overridePendingTransition(0,0);
                finish();
            }
        });

        getDriverById(id);
    }

    private void getDriverById(long id) {

        setLoading(true);

        StringRequest stringRequest = new StringRequest(GET, "https://atmajayarental.xyz/api/driver/" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API menjadi java object ProdukResponse menggunakan Gson */
                DriverResponse driverResponse = gson.fromJson(response, DriverResponse.class);

                Driver driver = driverResponse.getDriverList().get(0);
                etNamaDriver.setText(driver.getNama_driver());
                etAlamatDriver.setText(driver.getAlamat_driver());
                etEmailDriver.setText(driver.getEmail_driver());
                etNomorTeleponDriver.setText(driver.getNomor_telepon_driver());

                if(driver.getStatus_ketersediaan_driver()==0) {
                    radioButtonTidakTersedia.setChecked(true);
                }else {
                    radioButtonTersedia.setChecked(true);
                }

                Toast.makeText(UpdateDriverActivity.this, driverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                setLoading(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(UpdateDriverActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(UpdateDriverActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public int getNewStatusKetersediaanDriver() {
        if(radioButtonTidakTersedia.isChecked()) {
            return 0;
        }else {
            return 1;
        }
    }

    private void updateDriver(long id) {
        // TODO: Tambahkan fungsi untuk mengubah data buku.
        setLoading(true);

        Driver driver = new Driver(
                etNamaDriver.getText().toString(),
                etAlamatDriver.getText().toString(),
                etEmailDriver.getText().toString(),
                etNomorTeleponDriver.getText().toString(),
                getNewStatusKetersediaanDriver(),
                etPasswordDriver.getText().toString());

        // Membuat request baru untuk mengedit data mahasiswa
        StringRequest stringRequest = new StringRequest(PUT, "https://atmajayarental.xyz/api/driver/" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API
                menjadi java object MahasiswaResponse menggunakan Gson */
                DriverResponse driverResponse = gson.fromJson(response, DriverResponse.class);

                i = new Intent(UpdateDriverActivity.this, ProfileDriverActivity.class);
                i.putExtra("tempIDDriver", id);
                startActivity(i);
                overridePendingTransition(0,0);
                finish();

                Toast.makeText(UpdateDriverActivity.this, driverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                setLoading(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);

                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(UpdateDriverActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(UpdateDriverActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
            // Menambahkan request body berupa object mahasiswa
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                 /* Serialisasi data dari java object ProdukResponse
                 menjadi JSON string menggunakan Gson */
                String requestBody = gson.toJson(driver);
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
            // Mendeklarasikan content type dari request body yang ditambahkan
            @Override
            public String getBodyContentType() {
                return "application/json";
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
                .setMessage("Apakah Anda Yakin Ingin Meng-update Profile?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        long idDriver = getIntent().getLongExtra("tempIDDriver", -1);
                        updateDriver(idDriver);
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