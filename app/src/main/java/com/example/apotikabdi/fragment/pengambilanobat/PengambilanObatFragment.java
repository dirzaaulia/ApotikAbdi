package com.example.apotikabdi.fragment.pengambilanobat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.apotikabdi.R;
import com.example.apotikabdi.fragment.rekomendasi.RekomendasiActivity;
import com.example.apotikabdi.model.PengambilanObat;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class PengambilanObatFragment extends Fragment implements PengambilanObatView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private PengambilanObatPresenter pengambilanObatPresenter;
    private PengambilanObatAdapter.ItemClickListener itemClickListener;
    private List<PengambilanObat> pengambilanobat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pengambilan_obat, container, false);
        setHasOptionsMenu(true);

        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_pengambilan_obat);
        recyclerView = root.findViewById(R.id.recycler_view_pengambilan_obat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Inisiasi class presenter
        pengambilanObatPresenter = new PengambilanObatPresenter(this);
        pengambilanObatPresenter.getDaftarPengambilanObat();

        //Saat tampilan direfresh
        swipeRefreshLayout.setOnRefreshListener(() -> pengambilanObatPresenter.getDaftarPengambilanObat());

        //Saat card di ketuk
        itemClickListener = ((view, position) -> {

            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            String initialStringDate = pengambilanobat.get(position).getTanggal();
            Locale id = new Locale("ID");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", id);
            try {
                Date date = format.parse(initialStringDate);
                String tanggal = new SimpleDateFormat("dd/MM/yyyy", id).format(date);

                String id_pengambilan_obat = pengambilanobat.get(position).getId();
                String id_resep = pengambilanobat.get(position).getResep_id();
                String nama_pasien = pengambilanobat.get(position).getPasien_nama();
                String sub_total = numberFormat.format(Integer.parseInt(pengambilanobat.get(position).getTotalbiaya()));
                String int_sub_total = pengambilanobat.get(position).getTotalbiaya();

                Intent intent = new Intent(getActivity(), EditorPengambilanObatActivity.class);
                intent.putExtra("id", id_pengambilan_obat);
                intent.putExtra("id_resep", id_resep);
                intent.putExtra("nama_pasien", nama_pasien);
                intent.putExtra("tanggal", tanggal);
                intent.putExtra("sub_total", sub_total);
                intent.putExtra("int_sub_total", int_sub_total);
                startActivity(intent);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_rekomendasi) {
            Intent intent = new Intent(getActivity(), RekomendasiActivity.class);
            startActivity(intent);
            return false;
        }

        return false;
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
    public void onGetResult(List<PengambilanObat> pengambilanObatList) {

        PengambilanObatAdapter pengambilanObatAdapter = new PengambilanObatAdapter(getActivity(), pengambilanObatList, itemClickListener);
        pengambilanObatAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(pengambilanObatAdapter);

        pengambilanobat = pengambilanObatList;
    }

    @Override
    public void onErrorLoad(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
