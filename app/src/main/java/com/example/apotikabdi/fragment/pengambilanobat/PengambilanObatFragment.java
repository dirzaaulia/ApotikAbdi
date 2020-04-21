package com.example.apotikabdi.fragment.pengambilanobat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.apotikabdi.R;
import com.example.apotikabdi.model.PengambilanObat;

import java.util.List;

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

            String id_resep = pengambilanobat.get(position).getResep_id();
            String nama_pasien = pengambilanobat.get(position).getPasien_nama();
            String tanggal = pengambilanobat.get(position).getTanggal();
            String sub_total = pengambilanobat.get(position).getTotalbiaya();

            Intent intent = new Intent(getActivity(), EditorPengambilanObatActivity.class);
            intent.putExtra("id_resep", id_resep);
            intent.putExtra("nama_pasien", nama_pasien);
            intent.putExtra("tanggal", tanggal);
            intent.putExtra("sub_total", sub_total);

            startActivity(intent);

        });

        return root;
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