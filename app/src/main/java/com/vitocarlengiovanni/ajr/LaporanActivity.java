package com.vitocarlengiovanni.ajr;


import static com.android.volley.Request.Method.GET;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.vitocarlengiovanni.ajr.api.PromoApi;
import com.vitocarlengiovanni.ajr.models.Driver;
import com.vitocarlengiovanni.ajr.models.DriverResponse;
import com.vitocarlengiovanni.ajr.models.Laporan;
import com.vitocarlengiovanni.ajr.models.LaporanResponse;
import com.vitocarlengiovanni.ajr.models.PromoResponse;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LaporanActivity extends AppCompatActivity {

    private TextView tvMY;
    private Button btnMY, btn1, btn2, btn3, btn4, btn5;
    private RequestQueue queue;
    private BottomNavigationView bottomNavigationView;

    int pointer, month, year;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);
        getSupportActionBar().hide();

        // Pendeklarasian request queue
        queue = Volley.newRequestQueue(this);

        tvMY = findViewById(R.id.tv_my);

        btnMY = findViewById(R.id.btnMY);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);

        btnMY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMY(view);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvMY.getText().toString().equals("MONTH / YEAR")) {
                    Toast.makeText(LaporanActivity.this, "Pilih Bulan & Tahun Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }else {
                    pointer=1;
                    getLaporanByPointer(pointer, month, year);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvMY.getText().toString().equals("MONTH / YEAR")) {
                    Toast.makeText(LaporanActivity.this, "Pilih Bulan & Tahun Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }else {
                    pointer=2;
                    getLaporanByPointer(pointer, month, year);
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvMY.getText().toString().equals("MONTH / YEAR")) {
                    Toast.makeText(LaporanActivity.this, "Pilih Bulan & Tahun Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }else {
                    pointer=3;
                    getLaporanByPointer(pointer, month, year);
                }
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvMY.getText().toString().equals("MONTH / YEAR")) {
                    Toast.makeText(LaporanActivity.this, "Pilih Bulan & Tahun Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }else {
                    pointer=4;
                    getLaporanByPointer(pointer, month, year);
                }
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvMY.getText().toString().equals("MONTH / YEAR")) {
                    Toast.makeText(LaporanActivity.this, "Pilih Bulan & Tahun Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }else {
                    pointer=5;
                    getLaporanByPointer(pointer, month, year);
                }
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.laporan);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        i = new Intent(LaporanActivity.this, HomeActivityManager.class);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.laporan:
                        return true;
                }
                return false;
            }
        });
    }

    public void btnMY(View view) {
        final Calendar today = Calendar.getInstance();
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(LaporanActivity.this,
                new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        tvMY.setText((selectedMonth + 1)+" / "+selectedYear);
                        month = selectedMonth+1;
                        year = selectedYear;
                    }
                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

        builder.setActivatedMonth(today.get(Calendar.MONTH))
                .setMinYear(2000)
                .setActivatedYear(today.get(Calendar.YEAR))
                .setMaxYear(2100)
                .setTitle("SELECT MONTH / YEAR")
                .build().show();
    }

    private void getLaporanByPointer(int pointer, int month, int year) {

        StringRequest stringRequest = new StringRequest(GET, "https://atmajayarental.xyz/api/laporan/" + pointer+"/"+month+"/"+year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                 /* Deserialiasai data dari response JSON dari API
                 menjadi java object MahasiswaResponse menggunakan Gson */
                LaporanResponse laporanResponse = gson.fromJson(response, LaporanResponse.class);

                try {
                    cetakPDF(laporanResponse.getLaporanList(), pointer, month, year);
                } catch (FileNotFoundException | DocumentException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(LaporanActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(LaporanActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void cetakPDF(List<Laporan> laporanList, int pointer, int month, int year) throws FileNotFoundException, DocumentException {
        File folder = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

        if (!folder.exists()) {
            folder.mkdir();
        }

        Date currentTime = Calendar.getInstance().getTime();
        String pdfName = currentTime.getTime() + ".pdf";

        File pdfFile = new File(folder.getAbsolutePath(), pdfName);
        OutputStream outputStream = new FileOutputStream(pdfFile);

        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // bagian header
        Paragraph judul = new Paragraph("ATMA JOGJA RENTAL \n\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 16,
                        Font.BOLD, BaseColor.BLACK));

        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);

        if(pointer==1) {

            // Buat tabel
            PdfPTable tables = new PdfPTable(new float[]{16, 8});

            // Settingan ukuran tabel
            tables.getDefaultCell().setFixedHeight(50);
            tables.setTotalWidth(PageSize.A4.getWidth());
            tables.setWidthPercentage(100);
            tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            PdfPCell cellSupplier = new PdfPCell();
            cellSupplier.setPaddingLeft(20);
            cellSupplier.setPaddingBottom(10);
            cellSupplier.setBorder(Rectangle.NO_BORDER);

            Paragraph kepada = new Paragraph(
                    "Laporan Penyewaan Mobil Pada Bulan Dan Tahun Tertentu",
                    new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10,
                            Font.NORMAL, BaseColor.BLACK));

            kepada.setAlignment(Element.ALIGN_CENTER);
            cellSupplier.addElement(kepada);
            tables.addCell(cellSupplier);

            Paragraph NomorTanggal = new Paragraph(
                    "Bulan : " + month + "\n\n" +
                            "Tahun : " + year + "\n",
                    new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                            com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));

            NomorTanggal.setPaddingTop(5);
            tables.addCell(NomorTanggal);
            document.add(tables);
            com.itextpdf.text.Font f = new
                    com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
            Paragraph Pembuka = new Paragraph("\nDiurutkan Berdasarkan Banyaknya Pendapatan \n\n", f);
            Pembuka.setIndentationLeft(20);
            document.add(Pembuka);
            PdfPTable tableHeader = new PdfPTable(new float[]{5, 5, 5, 5, 5, 5});

            tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableHeader.getDefaultCell().setFixedHeight(30);
            tableHeader.setTotalWidth(PageSize.A4.getWidth());
            tableHeader.setWidthPercentage(100);

            // Setup Column
            PdfPCell h1 = new PdfPCell(new Phrase("Tipe Mobil"));
            h1.setHorizontalAlignment(Element.ALIGN_CENTER);
            h1.setPaddingBottom(5);
            PdfPCell h2 = new PdfPCell(new Phrase("Nama Mobil"));
            h2.setHorizontalAlignment(Element.ALIGN_CENTER);
            h2.setPaddingBottom(5);
            PdfPCell h3 = new PdfPCell(new Phrase("Harga Sewa Mobil"));
            h3.setHorizontalAlignment(Element.ALIGN_CENTER);
            h3.setPaddingBottom(5);
            PdfPCell h4 = new PdfPCell(new Phrase("Jumlah Peminjaman"));
            h4.setHorizontalAlignment(Element.ALIGN_CENTER);
            h4.setPaddingBottom(5);
            PdfPCell h5 = new PdfPCell(new Phrase("Total Durasi Sewa"));
            h5.setHorizontalAlignment(Element.ALIGN_CENTER);
            h5.setPaddingBottom(5);
            PdfPCell h6 = new PdfPCell(new Phrase("Pendapatan Dari Mobil"));
            h6.setHorizontalAlignment(Element.ALIGN_CENTER);
            h6.setPaddingBottom(5);

            tableHeader.addCell(h1);
            tableHeader.addCell(h2);
            tableHeader.addCell(h3);
            tableHeader.addCell(h4);
            tableHeader.addCell(h5);
            tableHeader.addCell(h6);

            // Beri warna untuk kolumn
            for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
                cells.setBackgroundColor(BaseColor.PINK);
            }

            document.add(tableHeader);
            PdfPTable tableData = new PdfPTable(new float[]{5, 5, 5, 5, 5, 5});

            tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableData.getDefaultCell().setFixedHeight(30);
            tableData.setTotalWidth(PageSize.A4.getWidth());
            tableData.setWidthPercentage(100);
            tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            // masukan data pegawai jadi baris
            for (Laporan L : laporanList) {
                tableData.addCell(L.getTIPE_MOBIL());
                tableData.addCell(L.getNAMA_MOBIL());
                tableData.addCell("Rp"+String.valueOf(L.getHARGA_SEWA_MOBIL())+"0");
                tableData.addCell(String.valueOf(L.getJUMLAH_PEMINJAMAN())+" Kali");
                tableData.addCell(String.valueOf(L.getTOTAL_DURASI_SEWA())+" Hari");
                tableData.addCell("Rp"+String.valueOf(L.getPENDAPATAN_DARI_MOBIL())+"0");
            }

            document.add(tableData);
            com.itextpdf.text.Font h = new
                    com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL);
            String tglDicetak = currentTime.toLocaleString();
            Paragraph P = new Paragraph("\nDicetak Pada Tanggal " + tglDicetak, h);
            P.setAlignment(Element.ALIGN_RIGHT);
            document.add(P);
            document.close();
            previewPdf(pdfFile);
            Toast.makeText(this, "Laporan Berhasil Dicetak", Toast.LENGTH_SHORT).show();
        }else if(pointer==2) {

            // Buat tabel
            PdfPTable tables = new PdfPTable(new float[]{16, 8});

            // Settingan ukuran tabel
            tables.getDefaultCell().setFixedHeight(50);
            tables.setTotalWidth(PageSize.A4.getWidth());
            tables.setWidthPercentage(100);
            tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            PdfPCell cellSupplier = new PdfPCell();
            cellSupplier.setPaddingLeft(20);
            cellSupplier.setPaddingBottom(10);
            cellSupplier.setBorder(Rectangle.NO_BORDER);

            Paragraph kepada = new Paragraph(
                    "Laporan Detail Pendapatan Pada Bulan Dan Tahun Tertentu",
                    new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10,
                            Font.NORMAL, BaseColor.BLACK));

            kepada.setAlignment(Element.ALIGN_CENTER);
            cellSupplier.addElement(kepada);
            tables.addCell(cellSupplier);

            Paragraph NomorTanggal = new Paragraph(
                    "Bulan : " + month + "\n\n" +
                            "Tahun : " + year + "\n",
                    new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                            com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));

            NomorTanggal.setPaddingTop(5);
            tables.addCell(NomorTanggal);
            document.add(tables);
            com.itextpdf.text.Font f = new
                    com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);

            PdfPTable tableHeader = new PdfPTable(new float[]{5, 5, 5, 5, 5});

            tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableHeader.getDefaultCell().setFixedHeight(30);
            tableHeader.setTotalWidth(PageSize.A4.getWidth());
            tableHeader.setWidthPercentage(100);

            // Setup Column
            PdfPCell h1 = new PdfPCell(new Phrase("Nama Customer"));
            h1.setHorizontalAlignment(Element.ALIGN_CENTER);
            h1.setPaddingBottom(5);
            PdfPCell h2 = new PdfPCell(new Phrase("Nama Mobil"));
            h2.setHorizontalAlignment(Element.ALIGN_CENTER);
            h2.setPaddingBottom(5);
            PdfPCell h3 = new PdfPCell(new Phrase("Jenis Transaksi"));
            h3.setHorizontalAlignment(Element.ALIGN_CENTER);
            h3.setPaddingBottom(5);
            PdfPCell h4 = new PdfPCell(new Phrase("Jumlah Transaksi"));
            h4.setHorizontalAlignment(Element.ALIGN_CENTER);
            h4.setPaddingBottom(5);
            PdfPCell h5 = new PdfPCell(new Phrase("Pendapatan Yang Diperoleh Dari Customer"));
            h5.setHorizontalAlignment(Element.ALIGN_CENTER);
            h5.setPaddingBottom(5);

            tableHeader.addCell(h1);
            tableHeader.addCell(h2);
            tableHeader.addCell(h3);
            tableHeader.addCell(h4);
            tableHeader.addCell(h5);

            // Beri warna untuk kolumn
            for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
                cells.setBackgroundColor(BaseColor.PINK);
            }

            document.add(tableHeader);
            PdfPTable tableData = new PdfPTable(new float[]{5, 5, 5, 5, 5});

            tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableData.getDefaultCell().setFixedHeight(30);
            tableData.setTotalWidth(PageSize.A4.getWidth());
            tableData.setWidthPercentage(100);
            tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            // masukan data pegawai jadi baris
            for (Laporan L : laporanList) {
                tableData.addCell(L.getNAMA_CUSTOMER());
                tableData.addCell(L.getNAMA_MOBIL());

                if(L.getJENIS_TRANSAKSI()==0) {
                    tableData.addCell("Peminjaman Mobil");
                }else {
                    tableData.addCell("Peminjaman Mobil + Driver");
                }

                tableData.addCell(String.valueOf(L.getJUMLAH_TRANSAKSI())+" Kali");
                tableData.addCell("Rp"+String.valueOf(L.getPENDAPATAN_YANG_DIPEROLEH_DARI_CUSTOMER())+"0");
            }

            document.add(tableData);
            com.itextpdf.text.Font h = new
                    com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL);
            String tglDicetak = currentTime.toLocaleString();
            Paragraph P = new Paragraph("\nDicetak Pada Tanggal " + tglDicetak, h);
            P.setAlignment(Element.ALIGN_RIGHT);
            document.add(P);
            document.close();
            previewPdf(pdfFile);
            Toast.makeText(this, "Laporan Berhasil Dicetak", Toast.LENGTH_SHORT).show();
        }else if(pointer==3) {

            // Buat tabel
            PdfPTable tables = new PdfPTable(new float[]{16, 8});

            // Settingan ukuran tabel
            tables.getDefaultCell().setFixedHeight(50);
            tables.setTotalWidth(PageSize.A4.getWidth());
            tables.setWidthPercentage(100);
            tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            PdfPCell cellSupplier = new PdfPCell();
            cellSupplier.setPaddingLeft(20);
            cellSupplier.setPaddingBottom(10);
            cellSupplier.setBorder(Rectangle.NO_BORDER);

            Paragraph kepada = new Paragraph(
                    "Laporan Top 5 Driver Pada Bulan Dan Tahun Tertentu",
                    new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10,
                            Font.NORMAL, BaseColor.BLACK));

            kepada.setAlignment(Element.ALIGN_CENTER);
            cellSupplier.addElement(kepada);
            tables.addCell(cellSupplier);

            Paragraph NomorTanggal = new Paragraph(
                    "Bulan : " + month + "\n\n" +
                            "Tahun : " + year + "\n",
                    new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                            com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));

            NomorTanggal.setPaddingTop(5);
            tables.addCell(NomorTanggal);
            document.add(tables);
            com.itextpdf.text.Font f = new
                    com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);

            Paragraph Pembuka = new Paragraph("\nDiurutkan Berdasarkan Jumlah Transaksi Terbanyak \n\n", f);
            Pembuka.setIndentationLeft(20);
            document.add(Pembuka);

            PdfPTable tableHeader = new PdfPTable(new float[]{5, 5, 5});

            tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableHeader.getDefaultCell().setFixedHeight(30);
            tableHeader.setTotalWidth(PageSize.A4.getWidth());
            tableHeader.setWidthPercentage(100);

            // Setup Column
            PdfPCell h1 = new PdfPCell(new Phrase("ID Driver"));
            h1.setHorizontalAlignment(Element.ALIGN_CENTER);
            h1.setPaddingBottom(5);
            PdfPCell h2 = new PdfPCell(new Phrase("Nama Driver"));
            h2.setHorizontalAlignment(Element.ALIGN_CENTER);
            h2.setPaddingBottom(5);
            PdfPCell h3 = new PdfPCell(new Phrase("Jumlah Transaksi"));
            h3.setHorizontalAlignment(Element.ALIGN_CENTER);
            h3.setPaddingBottom(5);

            tableHeader.addCell(h1);
            tableHeader.addCell(h2);
            tableHeader.addCell(h3);

            // Beri warna untuk kolumn
            for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
                cells.setBackgroundColor(BaseColor.PINK);
            }

            document.add(tableHeader);
            PdfPTable tableData = new PdfPTable(new float[]{5, 5, 5});

            tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableData.getDefaultCell().setFixedHeight(30);
            tableData.setTotalWidth(PageSize.A4.getWidth());
            tableData.setWidthPercentage(100);
            tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            // masukan data pegawai jadi baris
            for (Laporan L : laporanList) {
                tableData.addCell(L.getFormat_id_driver()+String.valueOf(L.getId_driver()));
                tableData.addCell(L.getNAMA_DRIVER());
                tableData.addCell(String.valueOf(L.getJUMLAH_TRANSAKSI())+" Kali");
            }

            document.add(tableData);
            com.itextpdf.text.Font h = new
                    com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL);
            String tglDicetak = currentTime.toLocaleString();
            Paragraph P = new Paragraph("\nDicetak Pada Tanggal " + tglDicetak, h);
            P.setAlignment(Element.ALIGN_RIGHT);
            document.add(P);
            document.close();
            previewPdf(pdfFile);
            Toast.makeText(this, "Laporan Berhasil Dicetak", Toast.LENGTH_SHORT).show();
        }else if(pointer==4) {

            // Buat tabel
            PdfPTable tables = new PdfPTable(new float[]{16, 8});

            // Settingan ukuran tabel
            tables.getDefaultCell().setFixedHeight(50);
            tables.setTotalWidth(PageSize.A4.getWidth());
            tables.setWidthPercentage(100);
            tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            PdfPCell cellSupplier = new PdfPCell();
            cellSupplier.setPaddingLeft(20);
            cellSupplier.setPaddingBottom(10);
            cellSupplier.setBorder(Rectangle.NO_BORDER);

            Paragraph kepada = new Paragraph(
                    "Laporan Performa Top 5 Driver Pada Bulan Dan Tahun Tertentu",
                    new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10,
                            Font.NORMAL, BaseColor.BLACK));

            kepada.setAlignment(Element.ALIGN_CENTER);
            cellSupplier.addElement(kepada);
            tables.addCell(cellSupplier);

            Paragraph NomorTanggal = new Paragraph(
                    "Bulan : " + month + "\n\n" +
                            "Tahun : " + year + "\n",
                    new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                            com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));

            NomorTanggal.setPaddingTop(5);
            tables.addCell(NomorTanggal);
            document.add(tables);
            com.itextpdf.text.Font f = new
                    com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);

            Paragraph Pembuka = new Paragraph("\nDiurutkan Berdasarkan Banyaknya Transaksi \n\n", f);
            Pembuka.setIndentationLeft(20);
            document.add(Pembuka);

            PdfPTable tableHeader = new PdfPTable(new float[]{5, 5, 5, 5});

            tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableHeader.getDefaultCell().setFixedHeight(30);
            tableHeader.setTotalWidth(PageSize.A4.getWidth());
            tableHeader.setWidthPercentage(100);

            // Setup Column
            PdfPCell h1 = new PdfPCell(new Phrase("ID Driver"));
            h1.setHorizontalAlignment(Element.ALIGN_CENTER);
            h1.setPaddingBottom(5);
            PdfPCell h2 = new PdfPCell(new Phrase("Nama Driver"));
            h2.setHorizontalAlignment(Element.ALIGN_CENTER);
            h2.setPaddingBottom(5);
            PdfPCell h3 = new PdfPCell(new Phrase("Jumlah Transaksi"));
            h3.setHorizontalAlignment(Element.ALIGN_CENTER);
            h3.setPaddingBottom(5);
            PdfPCell h4 = new PdfPCell(new Phrase("Rerata Rating Driver"));
            h4.setHorizontalAlignment(Element.ALIGN_CENTER);
            h4.setPaddingBottom(5);

            tableHeader.addCell(h1);
            tableHeader.addCell(h2);
            tableHeader.addCell(h3);
            tableHeader.addCell(h4);

            // Beri warna untuk kolumn
            for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
                cells.setBackgroundColor(BaseColor.PINK);
            }

            document.add(tableHeader);
            PdfPTable tableData = new PdfPTable(new float[]{5, 5, 5, 5});

            tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableData.getDefaultCell().setFixedHeight(30);
            tableData.setTotalWidth(PageSize.A4.getWidth());
            tableData.setWidthPercentage(100);
            tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            // masukan data pegawai jadi baris
            for (Laporan L : laporanList) {
                tableData.addCell(L.getFormat_id_driver()+String.valueOf(L.getId_driver()));
                tableData.addCell(L.getNAMA_DRIVER());
                tableData.addCell(String.valueOf(L.getJUMLAH_TRANSAKSI())+" Kali");
                tableData.addCell(String.valueOf(L.getRERATA_RATING_DRIVER())+" ★");
            }

            document.add(tableData);
            com.itextpdf.text.Font h = new
                    com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL);
            String tglDicetak = currentTime.toLocaleString();
            Paragraph P = new Paragraph("\nDicetak Pada Tanggal " + tglDicetak, h);
            P.setAlignment(Element.ALIGN_RIGHT);
            document.add(P);
            document.close();
            previewPdf(pdfFile);
            Toast.makeText(this, "Laporan Berhasil Dicetak", Toast.LENGTH_SHORT).show();
        }else if(pointer==5) {

            // Buat tabel
            PdfPTable tables = new PdfPTable(new float[]{16, 8});

            // Settingan ukuran tabel
            tables.getDefaultCell().setFixedHeight(50);
            tables.setTotalWidth(PageSize.A4.getWidth());
            tables.setWidthPercentage(100);
            tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            PdfPCell cellSupplier = new PdfPCell();
            cellSupplier.setPaddingLeft(20);
            cellSupplier.setPaddingBottom(10);
            cellSupplier.setBorder(Rectangle.NO_BORDER);

            Paragraph kepada = new Paragraph(
                    "Laporan Top 5 Customer Pada Bulan Dan Tahun Tertentu",
                    new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10,
                            Font.NORMAL, BaseColor.BLACK));

            kepada.setAlignment(Element.ALIGN_CENTER);
            cellSupplier.addElement(kepada);
            tables.addCell(cellSupplier);

            Paragraph NomorTanggal = new Paragraph(
                    "Bulan : " + month + "\n\n" +
                            "Tahun : " + year + "\n",
                    new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                            com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));

            NomorTanggal.setPaddingTop(5);
            tables.addCell(NomorTanggal);
            document.add(tables);
            com.itextpdf.text.Font f = new
                    com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);

            Paragraph Pembuka = new Paragraph("\nDiurutkan Berdasarkan Banyaknya Transaksi \n\n", f);
            Pembuka.setIndentationLeft(20);
            document.add(Pembuka);

            PdfPTable tableHeader = new PdfPTable(new float[]{5, 5});

            tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableHeader.getDefaultCell().setFixedHeight(30);
            tableHeader.setTotalWidth(PageSize.A4.getWidth());
            tableHeader.setWidthPercentage(100);

            // Setup Column
            PdfPCell h1 = new PdfPCell(new Phrase("Nama Customer"));
            h1.setHorizontalAlignment(Element.ALIGN_CENTER);
            h1.setPaddingBottom(5);
            PdfPCell h2 = new PdfPCell(new Phrase("Jumlah Transaksi"));
            h2.setHorizontalAlignment(Element.ALIGN_CENTER);
            h2.setPaddingBottom(5);

            tableHeader.addCell(h1);
            tableHeader.addCell(h2);

            // Beri warna untuk kolumn
            for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
                cells.setBackgroundColor(BaseColor.PINK);
            }

            document.add(tableHeader);
            PdfPTable tableData = new PdfPTable(new float[]{5, 5});

            tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableData.getDefaultCell().setFixedHeight(30);
            tableData.setTotalWidth(PageSize.A4.getWidth());
            tableData.setWidthPercentage(100);
            tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            // masukan data pegawai jadi baris
            for (Laporan L : laporanList) {
                tableData.addCell(L.getNAMA_CUSTOMER());
                tableData.addCell(String.valueOf(L.getJUMLAH_TRANSAKSI())+" Kali");
            }

            document.add(tableData);
            com.itextpdf.text.Font h = new
                    com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL);
            String tglDicetak = currentTime.toLocaleString();
            Paragraph P = new Paragraph("\nDicetak Pada Tanggal " + tglDicetak, h);
            P.setAlignment(Element.ALIGN_RIGHT);
            document.add(P);
            document.close();
            previewPdf(pdfFile);
            Toast.makeText(this, "Laporan Berhasil Dicetak", Toast.LENGTH_SHORT).show();
        }
    }

    private void previewPdf(File pdfFile) {
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List<ResolveInfo> list = packageManager.queryIntentActivities(testIntent,
                PackageManager.MATCH_DEFAULT_ONLY);

        if (list.size() > 0) {
            Uri uri;
            uri = FileProvider.getUriForFile(this, getPackageName() + ".provider",
                    pdfFile);
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(uri, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            this.grantUriPermission(getPackageName(), uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(pdfIntent);
        }
    }
}