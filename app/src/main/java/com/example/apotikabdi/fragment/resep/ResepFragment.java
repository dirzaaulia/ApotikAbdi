package com.example.apotikabdi.fragment.resep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.apotikabdi.R;
import com.example.apotikabdi.model.Resep;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ResepFragment extends Fragment implements ResepView {

    private static final int INTENT_EDIT = 200;
    private static final int INTENT_ADD = 100;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ResepPresenter resepPresenter;
    private ResepAdapter.ItemClickListener itemClickListener;
    private List<Resep> resep;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_resep, container, false);

        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_resep);
        recyclerView = root.findViewById(R.id.recycler_view_resep);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ExtendedFloatingActionButton fab = root.findViewById(R.id.fab_tambahresep);
        fab.setOnClickListener(v -> startActivity(new Intent(getActivity(), EditorResepActivity.class)));

        //Inisiasi class presenter
        resepPresenter = new ResepPresenter(this);
        resepPresenter.getDaftarResep();

        //Saat tampilan direfresh
        swipeRefreshLayout.setOnRefreshListener(() -> resepPresenter.getDaftarResep());
        //Saat card di ketuk


        itemClickListener = ((view, position) -> {

            String id_obat = resep.get(position).getId();
            String kode_obat = resep.get(position).getObat_id();
            String nama_obat = resep.get(position).getNama_obat();
            String jumlah_obat = resep.get(position).getJumlah();
            String total_obat = resep.get(position).getTotalharga();

            Intent intent = new Intent(getActivity(), EditorResepActivity.class);
            intent.putExtra("id_obat", id_obat);
            intent.putExtra("kode_obat", kode_obat);
            intent.putExtra("nama_obat", nama_obat);
            intent.putExtra("jumlah_obat", jumlah_obat);
            intent.putExtra("total_obat", total_obat);

            startActivityForResult(intent, INTENT_EDIT);


        });

        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == INTENT_ADD && resultCode == Activity.RESULT_OK) {
            resepPresenter.getDaftarResep();
        } else if (requestCode == INTENT_EDIT && resultCode == Activity.RESULT_OK) {
            resepPresenter.getDaftarResep();
        }

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

        ResepAdapter resepAdapter = new ResepAdapter(getActivity(), resepList, itemClickListener);
        resepAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(resepAdapter);

        resep = resepList;
    }

    @Override
    public void onErrorLoad(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
