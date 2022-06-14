package com.vitocarlengiovanni.ajr;

import static com.android.volley.Request.Method.GET;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ViewBrosurActivity extends AppCompatActivity {

    private EditText etNamaMobil, etTipeMobil, etJenisTransmisiMobil, etJenisBahanBakarMobil, etWarnaMobil, etVolumeBagasiMobil, etFasilitasMobil, etHargaSewaMobil;
    private ImageView ivGambar;
    private LinearLayout layoutLoading;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_brosur);
        getSupportActionBar().hide();

        // Pendeklarasian request queue
        queue = Volley.newRequestQueue(this);

        ivGambar = findViewById(R.id.iv_gambar);
        etNamaMobil = findViewById(R.id.et_nama_mobil);
        etTipeMobil = findViewById(R.id.et_tipe_mobil);
        etJenisTransmisiMobil = findViewById(R.id.et_jenis_transmisi_mobil);
        etJenisBahanBakarMobil = findViewById(R.id.et_jenis_bahan_bakar_mobil);
        etWarnaMobil = findViewById(R.id.et_warna_mobil);
        etVolumeBagasiMobil = findViewById(R.id.et_volume_bagasi_mobil);
        etFasilitasMobil = findViewById(R.id.et_fasilitas_mobil);
        etHargaSewaMobil = findViewById(R.id.et_harga_sewa_mobil);
        layoutLoading = findViewById(R.id.layout_loading);

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        long id_mobil = getIntent().getLongExtra("id_mobil", -1);

        getBrosurById(id_mobil);
    }

    private void getBrosurById(long id_mobil) {

        setLoading(true);

        StringRequest stringRequest = new StringRequest(GET, BrosurApi.GET_BY_ID_URL + id_mobil, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API menjadi java object ProdukResponse menggunakan Gson */
                BrosurResponse brosurResponse = gson.fromJson(response, BrosurResponse.class);

                Brosur brosur = brosurResponse.getBrosurList().get(0);
                etNamaMobil.setText(brosur.getNama_mobil());
                etTipeMobil.setText(brosur.getTipe_mobil());
                etJenisTransmisiMobil.setText(brosur.getJenis_transmisi_mobil());
                etJenisBahanBakarMobil.setText(brosur.getJenis_bahan_bakar_mobil());
                etWarnaMobil.setText(brosur.getWarna_mobil());
                etVolumeBagasiMobil.setText(brosur.getVolume_bagasi_mobil()+" mm");
                etFasilitasMobil.setText(brosur.getFasilitas_mobil());
                etHargaSewaMobil.setText("Rp"+String.valueOf(brosur.getHarga_sewa_mobil())+"0");
                Glide.with(ivGambar)
                        .load(brosur.getGambar_mobil())
                        .placeholder(R.drawable.no_image)
                        .into(ivGambar);

                Toast.makeText(ViewBrosurActivity.this, brosurResponse.getMessage(), Toast.LENGTH_SHORT).show();
                setLoading(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(ViewBrosurActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ViewBrosurActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
            layoutLoading.setVisibility(View.INVISIBLE);
        }
    }
}