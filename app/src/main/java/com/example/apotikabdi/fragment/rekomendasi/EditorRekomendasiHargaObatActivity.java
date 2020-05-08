package com.example.apotikabdi.fragment.rekomendasi;

import android.os.Bundle;
import android.view.Menu;

import com.example.apotikabdi.R;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditorRekomendasiHargaObatActivity extends AppCompatActivity {

    Toolbar toolbar;
    Menu actionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_rekomendasi_harga_obat);

        bindViews();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Rekomendasi");
    }

    private void bindViews() {

        toolbar = findViewById(R.id.toolbar_editor_rekomendasi_harga_obat);
    }
}
