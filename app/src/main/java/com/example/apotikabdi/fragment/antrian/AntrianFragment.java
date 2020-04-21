package com.example.apotikabdi.fragment.antrian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.apotikabdi.R;
import com.example.apotikabdi.model.Antrian;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class AntrianFragment extends Fragment implements AntrianView {

    private static final int INTENT_EDIT = 200;
    private static final int INTENT_ADD = 100;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton fab;

    private AntrianPresenter antrianPresenter;
    private AntrianAdapter.ItemClickListener itemClickListener;

    private List<Antrian> antrian;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_antrian, container, false);

        bindViews(root);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fab.setOnClickListener(v -> startActivity(new Intent(getActivity(), EditorAntrianActivity.class)));

        antrianPresenter = new AntrianPresenter(this);
        antrianPresenter.getDaftarAntrian();

        swipeRefreshLayout.setOnRefreshListener(() -> antrianPresenter.getDaftarAntrian());

        itemClickListener = ((view, position) -> {

            String id = antrian.get(position).getId();
            String nama_pasien = antrian.get(position).getNama_pasien();
            String keluhan = antrian.get(position).getKeluhan();


            Intent intent = new Intent(getActivity(), EditorAntrianActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("nama_pasien", nama_pasien);
            intent.putExtra("keluhan", keluhan);

            startActivityForResult(intent, INTENT_EDIT);

        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_ADD && resultCode == Activity.RESULT_OK) {
            antrianPresenter.getDaftarAntrian();
        } else if (requestCode == INTENT_EDIT && resultCode == Activity.RESULT_OK) {
            antrianPresenter.getDaftarAntrian();
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
    public void onGetResult(List<Antrian> antrianList) {

        AntrianAdapter antrianAdapter = new AntrianAdapter(getActivity(), antrianList, itemClickListener);
        antrianAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(antrianAdapter);

        antrian = antrianList;
    }

    @Override
    public void onErrorLoad(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void bindViews(View view) {

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_antrian);
        recyclerView = view.findViewById(R.id.recycler_view_antrian);
        fab = view.findViewById(R.id.fab_tambahantrian);
    }
}
