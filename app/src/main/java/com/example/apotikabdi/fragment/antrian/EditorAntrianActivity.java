package com.example.apotikabdi.fragment.antrian;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.apotikabdi.R;
import com.example.apotikabdi.api.ApiClient;
import com.example.apotikabdi.api.ApiInterface;
import com.example.apotikabdi.model.Pasien;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditorAntrianActivity extends AppCompatActivity implements EditorAntrianView {

    AutoCompleteTextView autoCompleteTextViewNamaPasien;
    TextInputEditText editTextKeluhan;
    TextInputLayout textInputLayout;
    Menu actionMenu;
    ProgressDialog progressDialog;
    Toolbar toolbar;

    ArrayAdapter<String> adapter;
    String id, nama_pasien, keluhan, status;
    String[] namaPasienListString;

    EditorAntrianPresenter editorAntrianPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_antrian);

        bindViews();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Tambah Data Antrian");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        setDataFromIntentExtra();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon tunggu, data sedang ditambahkan...");

        editorAntrianPresenter = new EditorAntrianPresenter(this);

        selectNamaPasien();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        actionMenu = menu;

        if (id != null) {

            actionMenu.findItem(R.id.edit).setVisible(true);
            actionMenu.findItem(R.id.delete).setVisible(true);
            actionMenu.findItem(R.id.save).setVisible(false);
            actionMenu.findItem(R.id.update).setVisible(false);

        } else {

            actionMenu.findItem(R.id.edit).setVisible(false);
            actionMenu.findItem(R.id.delete).setVisible(false);
            actionMenu.findItem(R.id.save).setVisible(true);
            actionMenu.findItem(R.id.update).setVisible(false);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        nama_pasien = autoCompleteTextViewNamaPasien.getText().toString().trim();
        keluhan = Objects.requireNonNull(editTextKeluhan.getText()).toString().trim();
        status = "Onsite";


        switch (item.getItemId()) {

            case R.id.edit:

                editMode();
                actionMenu.findItem(R.id.edit).setVisible(false);
                actionMenu.findItem(R.id.delete).setVisible(false);
                actionMenu.findItem(R.id.save).setVisible(false);
                actionMenu.findItem(R.id.update).setVisible(true);

                return true;

            case R.id.save:

                if (keluhan.isEmpty()) {
                    editTextKeluhan.setError("Keluhan tidak boleh kosong");
                } else {
                    editorAntrianPresenter.tambahAntrian(nama_pasien, keluhan, status);
                }

                return true;

            case R.id.update:

                if (keluhan.isEmpty()) {
                    editTextKeluhan.setError("Keluhan tidak boleh kosong");
                } else {
                    editorAntrianPresenter.ubahAntrian(id, nama_pasien, keluhan);
                }

                return true;

            case R.id.delete:

                new MaterialAlertDialogBuilder(this)
                        .setTitle("Hapus Data Antrian")
                        .setMessage("Apakah anda yakin untuk menghapus data antrian ini?")
                        .setPositiveButton("Ya", (dialog, which) -> {
                            dialog.dismiss();
                            editorAntrianPresenter.hapusAntrian(id);
                        })
                        .setNegativeButton("Tidak", null)
                        .show();

            default:
                return super.onOptionsItemSelected(item);
        }
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

    private void selectNamaPasien() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Pasien>> call = apiInterface.getDaftarPasien("");
        call.enqueue(new Callback<List<Pasien>>() {
            @Override
            public void onResponse(@NonNull Call<List<Pasien>> call, @NonNull Response<List<Pasien>> response) {
                List<Pasien> pasienList = response.body();

                if (pasienList != null && pasienList.size() > 0) {
                    namaPasienListString = new String[pasienList.size()];

                    for (int i = 0; i < pasienList.size(); i++) {
                        namaPasienListString[i] = pasienList.get(i).getNama();

                        adapter = new ArrayAdapter<>(getBaseContext(), R.layout.dropdown_menu, namaPasienListString);
                        autoCompleteTextViewNamaPasien = findViewById(R.id.auto_complete);
                        autoCompleteTextViewNamaPasien.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Pasien>> call, @NonNull Throwable t) {
                Toast.makeText(getBaseContext(), "Terjadi kesalahan saat mengambil data pasien", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDataFromIntentExtra() {

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        nama_pasien = intent.getStringExtra("nama_pasien");
        keluhan = intent.getStringExtra("keluhan");

        if (id != null) {

            autoCompleteTextViewNamaPasien.setText(nama_pasien);
            editTextKeluhan.setText(keluhan);
            Objects.requireNonNull(getSupportActionBar()).setTitle("Data Antrian");
            readMode();
        }
    }

    private void editMode() {

        Objects.requireNonNull(getSupportActionBar()).setTitle("Ubah Data Antrian");
        textInputLayout.setEndIconMode(TextInputLayout.END_ICON_DROPDOWN_MENU);
        autoCompleteTextViewNamaPasien.setEnabled(true);
        editTextKeluhan.setEnabled(true);
    }

    private void readMode() {

        textInputLayout.setEndIconMode(TextInputLayout.END_ICON_NONE);
        autoCompleteTextViewNamaPasien.setEnabled(false);
        editTextKeluhan.setEnabled(false);
    }

    private void bindViews() {

        editTextKeluhan = findViewById(R.id.keluhan);
        autoCompleteTextViewNamaPasien = findViewById(R.id.auto_complete);
        textInputLayout = findViewById(R.id.textInputLayout1);
        toolbar = findViewById(R.id.toolbar_editor_antrian);
    }

}
