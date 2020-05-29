package com.example.apotikabdi.fragment.pasien;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apotikabdi.R;
import com.example.apotikabdi.model.Pasien;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class PasienFragment extends Fragment implements PasienView {

    private static final int INTENT_EDIT = 200;
    private static final int INTENT_ADD = 100;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private PasienPresenter pasienPresenter;
    private PasienAdapter.ItemClickListener itemClickListener;
    private List<Pasien> pasien;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pasien, container, false);
        setHasOptionsMenu(true);

        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_pasien);
        recyclerView = root.findViewById(R.id.recycler_view_pasien);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ExtendedFloatingActionButton fab = root.findViewById(R.id.fab_tambahpasien);
        fab.setOnClickListener(v -> startActivityForResult(new Intent(getActivity(), EditorPasienActivity.class), INTENT_ADD));

        //Inisiasi class presenter
        pasienPresenter = new PasienPresenter(this);
        pasienPresenter.getDaftarPasien("");

        //Saat tampilan direfresh
        swipeRefreshLayout.setOnRefreshListener(() -> pasienPresenter.getDaftarPasien(""));
        //Saat card di ketuk

        itemClickListener = ((view, position) -> {

            String id = pasien.get(position).getId();
            String nama = pasien.get(position).getNama();
            String no_rekammedis = pasien.get(position).getNo_rekammedis();
            String tanggal_lahir = pasien.get(position).getTanggal_lahir();
            String jenis_kelamin = pasien.get(position).getJenis_kelamin();
            String alamat = pasien.get(position).getAlamat();
            String nohp = pasien.get(position).getNohp();
            String status = "edit";

            Intent intent = new Intent(getActivity(), EditorPasienActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("nama", nama);
            intent.putExtra("no_rekammedis", no_rekammedis);
            intent.putExtra("tanggal_lahir", tanggal_lahir);
            intent.putExtra("jenis_kelamin", jenis_kelamin);
            intent.putExtra("alamat", alamat);
            intent.putExtra("nohp", nohp);
            intent.putExtra("status", status);
            startActivityForResult(intent, INTENT_EDIT);

        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) requireActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        int id = searchView.getContext()
                .getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView textView = searchView.findViewById(id);
        textView.setTextColor(Color.WHITE);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(requireActivity().getComponentName())
        );

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                pasienPresenter.getDaftarPasien(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pasienPresenter.getDaftarPasien(newText);
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            pasienPresenter.getDaftarPasien("");
            return false;
        });

        MenuItem searchMenuItem = menu.findItem(R.id.search);
        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do whatever you need
                return true; // KEEP IT TO TRUE OR IT DOESN'T OPEN !!
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                pasienPresenter.getDaftarPasien("");
                return true; // OR FALSE IF YOU DIDN'T WANT IT TO CLOSE!
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_ADD && resultCode == Activity.RESULT_OK) {
            pasienPresenter.getDaftarPasien("");
        } else if (requestCode == INTENT_EDIT && resultCode == Activity.RESULT_OK) {
            pasienPresenter.getDaftarPasien("");
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
    public void onGetResult(List<Pasien> pasienList) {

        PasienAdapter pasienAdapter = new PasienAdapter(getActivity(), pasienList, itemClickListener);
        pasienAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(pasienAdapter);

        pasien = pasienList;
    }

    @Override
    public void onErrorLoad(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
