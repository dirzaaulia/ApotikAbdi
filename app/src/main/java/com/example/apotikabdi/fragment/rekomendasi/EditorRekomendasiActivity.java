package com.example.apotikabdi.fragment.rekomendasi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.apotikabdi.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditorRekomendasiActivity extends AppCompatActivity implements EditorRekomendasiView {

    TextInputEditText editTextObatId, editTextNamaObat, editTextJumlah, editTextHargaAwal, editTextPerubahanHarga;

    String obat_id, nama_obat, jumlah_obat, harga_awal, perubahan_harga;

    NumberFormat numberFormat;

    Toolbar toolbar;
    ProgressDialog progressDialog;

    EditorRekomendasiPresenter editorRekomendasiPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_rekomendasi);

        bindViews();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Data Rekomendasi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        numberFormat = NumberFormat.getCurrencyInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon tunggu, data sedang diperbaharui...");

        editorRekomendasiPresenter = new EditorRekomendasiPresenter(this);

        setDataFromIntentExtra();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_rekomendasi, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        obat_id = Objects.requireNonNull(editTextObatId.getText()).toString().trim();

        if (item.getItemId() == R.id.menu_rekomendasi_terapkan) {
            editorRekomendasiPresenter.terapkanRekomendasi(obat_id, perubahan_harga);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void onRequestSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setDataFromIntentExtra() {

        Intent intent = getIntent();
        obat_id = intent.getStringExtra("obat_id");
        nama_obat = intent.getStringExtra("nama_obat");
        jumlah_obat = intent.getStringExtra("jumlah_obat");
        harga_awal = intent.getStringExtra("harga_obat");
        perubahan_harga = intent.getStringExtra("perubahan_harga");

        editTextObatId.setText(obat_id);
        editTextNamaObat.setText(nama_obat);
        editTextJumlah.setText(jumlah_obat);
        editTextHargaAwal.setText(numberFormat.format(Integer.parseInt(harga_awal)));
        editTextPerubahanHarga.setText(numberFormat.format(Integer.parseInt(perubahan_harga)));
    }

    private void bindViews(){

        toolbar = findViewById(R.id.toolbar_editor_rekomendasi);
        editTextObatId = findViewById(R.id.edit_text_rekomendasi_obat_id);
        editTextNamaObat = findViewById(R.id.edit_text_rekomendasi_nama_obat);
        editTextJumlah = findViewById(R.id.edit_text_rekomendasi_jumlah);
        editTextHargaAwal = findViewById(R.id.edit_text_rekomendasi_harga_awal);
        editTextPerubahanHarga = findViewById(R.id.edit_text_rekomendasi_perubahan_harga);
    }
}