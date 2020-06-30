package com.example.apotikabdi.fragment.rekomendasi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.apotikabdi.R;
import com.example.apotikabdi.model.Resep;

import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class RekomendasiActivity extends AppCompatActivity implements RekomendasiView {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RekomendasiPresenter rekomendasiPresenter;
    RekomendasiAdapter.ItemClickListener itemClickListener;
    List<Resep> resep;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekomendasi);

        bindViews();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Rekomendasi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Inisiasi class presenter
        rekomendasiPresenter = new RekomendasiPresenter(this);
        rekomendasiPresenter.getDaftarRekomendasi();

        //Saat tampilan direfresh
        swipeRefreshLayout.setOnRefreshListener(() -> rekomendasiPresenter.getDaftarRekomendasi());

        //Saat card di ketuk
        itemClickListener = ((view, position) -> {

            String obat_id = resep.get(position).getObat_id();
            String nama_obat = resep.get(position).getNama_obat();
            String jumlah_obat = resep.get(position).getJumlah();
            String harga_obat = resep.get(position).getTotalharga();
            String perubahan_harga = resep.get(position).getHargadiskon();

            Intent intent = new Intent(this , EditorRekomendasiActivity.class);
            intent.putExtra("obat_id", obat_id);
            intent.putExtra("nama_obat", nama_obat);
            intent.putExtra("jumlah_obat", jumlah_obat);
            intent.putExtra("harga_obat", harga_obat);
            intent.putExtra("perubahan_harga", perubahan_harga);
            startActivity(intent);
        });

    }

    private void bindViews() {

        toolbar = findViewById(R.id.toolbar_rekomendasi);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_rekomendasi);
        recyclerView = findViewById(R.id.recycler_view_rekomendasi);
    }

    @Override
    public void showLoad() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoad() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetResult(List<Resep> resepList) {

        RekomendasiAdapter rekomendasiAdapter = new RekomendasiAdapter(this, resepList, itemClickListener);
        rekomendasiAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(rekomendasiAdapter);

        resep = resepList;
    }

    @Override
    public void onErrorLoad(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
