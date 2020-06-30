package com.example.apotikabdi.fragment.resep;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apotikabdi.R;
import com.example.apotikabdi.api.ApiClient;
import com.example.apotikabdi.api.ApiInterface;
import com.example.apotikabdi.model.Antrian;
import com.example.apotikabdi.model.Obat;
import com.example.apotikabdi.model.Resep;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorResepActivity extends AppCompatActivity implements EditorResepView {

    AutoCompleteTextView autoCompleteTextViewNamaPasien, autoCompleteTextViewObat1, autoCompleteTextViewObat2, autoCompleteTextViewObat3, autoCompleteTextViewObat4,
            autoCompleteTextViewObat5;
    TextInputEditText editTextJumlah1, editTextJumlah2, editTextJumlah3, editTextJumlah4, editTextJumlah5, editTextTotal1, editTextTotal2, editTextTotal3, editTextTotal4, editTextTotal5,
            editTextSubTotal, editTextIDResep;
    TextInputLayout textInputLayoutNamaPasien, textInputLayoutTotal1, textInputLayoutTotal2, textInputLayoutTotal3, textInputLayoutTotal4, textInputLayoutTotal5;
    MaterialCardView cardViewObat2, cardViewObat3, cardViewObat4, cardViewObat5;
    Button buttonTambahObat, buttonHapusObat, buttonTotalHarga;
    TextView textViewObat1;
    LinearLayout linearLayout;
    Toolbar toolbar;

    String id, kodeObat1, kodeObat2, kodeObat3, kodeObat4, kodeObat5, hargaObat1, hargaObat2, hargaObat3, hargaObat4, hargaObat5, nomor_antrian, pasien_nama, nama_obat, jumlah_obat, total_obat, kode_obat, id_obat;
    int jumlah1, jumlah2, jumlah3, jumlah4, jumlah5, total1, total2, total3, total4, total5, subTotal;

    String[] nomorAntrianListString, namaPasienListString, kodeObatListString, namaObatListString, hargaObatListString;

    ArrayAdapter<String> adapter;

    NumberFormat numberFormat;

    Menu actionMenu;
    ProgressDialog progressDialog;

    EditorResepPresenter editorResepPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_resep);

        bindViews();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Tambah Data Resep");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        numberFormat = NumberFormat.getCurrencyInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon tunggu, data sedang ditambahkan...");

        editorResepPresenter = new EditorResepPresenter(this);

        setDataFromIntentExtra();
        selectDaftarAntrian();
        selectDaftarObat();
        viewOnClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        actionMenu = menu;

        if (id_obat != null) {

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

        id = Objects.requireNonNull(editTextIDResep.getText()).toString().trim();

        switch (item.getItemId()) {

            case R.id.edit:

                editMode();
                actionMenu.findItem(R.id.edit).setVisible(false);
                actionMenu.findItem(R.id.delete).setVisible(false);
                actionMenu.findItem(R.id.save).setVisible(false);
                actionMenu.findItem(R.id.update).setVisible(true);

                return true;

            case R.id.save:

                if (!Objects.requireNonNull(editTextJumlah5.getText()).toString().isEmpty()) {

                    editorResepPresenter.tambahResep(id, kodeObat1, Integer.toString(jumlah1), Integer.toString(total1));
                    editorResepPresenter.tambahResep(id, kodeObat2, Integer.toString(jumlah2), Integer.toString(total2));
                    editorResepPresenter.tambahResep(id, kodeObat3, Integer.toString(jumlah3), Integer.toString(total3));
                    editorResepPresenter.tambahResep(id, kodeObat4, Integer.toString(jumlah4), Integer.toString(total4));
                    editorResepPresenter.tambahResep(id, kodeObat5, Integer.toString(jumlah5), Integer.toString(total5));
                    editorResepPresenter.tambahPengambilanObat(nomor_antrian, pasien_nama, id, Integer.toString(subTotal));

                } else if (!Objects.requireNonNull(editTextJumlah4.getText()).toString().isEmpty()) {

                    editorResepPresenter.tambahResep(id, kodeObat1, Integer.toString(jumlah1), Integer.toString(total1));
                    editorResepPresenter.tambahResep(id, kodeObat2, Integer.toString(jumlah2), Integer.toString(total2));
                    editorResepPresenter.tambahResep(id, kodeObat3, Integer.toString(jumlah3), Integer.toString(total3));
                    editorResepPresenter.tambahResep(id, kodeObat4, Integer.toString(jumlah4), Integer.toString(total4));
                    editorResepPresenter.tambahPengambilanObat(nomor_antrian, pasien_nama, id, Integer.toString(subTotal));

                } else if (!Objects.requireNonNull(editTextJumlah3.getText()).toString().isEmpty()) {

                    editorResepPresenter.tambahResep(id, kodeObat1, Integer.toString(jumlah1), Integer.toString(total1));
                    editorResepPresenter.tambahResep(id, kodeObat2, Integer.toString(jumlah2), Integer.toString(total2));
                    editorResepPresenter.tambahResep(id, kodeObat3, Integer.toString(jumlah3), Integer.toString(total3));
                    editorResepPresenter.tambahPengambilanObat(nomor_antrian, pasien_nama, id, Integer.toString(subTotal));

                } else if (!Objects.requireNonNull(editTextJumlah2.getText()).toString().isEmpty()) {

                    editorResepPresenter.tambahResep(id, kodeObat1, Integer.toString(jumlah1), Integer.toString(total1));
                    editorResepPresenter.tambahResep(id, kodeObat2, Integer.toString(jumlah2), Integer.toString(total2));
                    editorResepPresenter.tambahPengambilanObat(nomor_antrian, pasien_nama, id, Integer.toString(subTotal));

                } else if (!Objects.requireNonNull(editTextJumlah1.getText()).toString().isEmpty()) {

                    editorResepPresenter.tambahResep(id, kodeObat1, Integer.toString(jumlah1), Integer.toString(total1));
                    editorResepPresenter.tambahPengambilanObat(nomor_antrian, pasien_nama, id, Integer.toString(subTotal));

                }

                return true;


            case R.id.update:

                id = editTextIDResep.getText().toString().trim();
                jumlah_obat = Objects.requireNonNull(editTextJumlah1.getText()).toString().trim();

                if (nama_obat.equals(autoCompleteTextViewObat1.getText().toString())) {
                    editorResepPresenter.ubahResep(id, kode_obat, kode_obat, jumlah_obat, Integer.toString(total1));
                } else {
                    editorResepPresenter.ubahResep(id, kode_obat, kodeObat1, jumlah_obat, Integer.toString(total1));
                }

                return true;

            case R.id.delete:

                id = editTextIDResep.getText().toString().trim();
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Hapus Data Resep")
                        .setMessage("Apakah anda yakin untuk menghapus data resep ini?")
                        .setPositiveButton("Ya", (dialog, which) -> {
                            dialog.dismiss();
                            editorResepPresenter.hapusResep(id, kode_obat);
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

    private void bindViews(){

        linearLayout = findViewById(R.id.linear_layout_resep);

        textViewObat1 = findViewById(R.id.label_obat_1);

        textInputLayoutNamaPasien = findViewById(R.id.layout_nama_pasien);
        textInputLayoutTotal1 = findViewById(R.id.layout_total_harga1);
        textInputLayoutTotal2 = findViewById(R.id.layout_total_harga2);
        textInputLayoutTotal3 = findViewById(R.id.layout_total_harga3);
        textInputLayoutTotal4 = findViewById(R.id.layout_total_harga4);
        textInputLayoutTotal5 = findViewById(R.id.layout_total_harga5);

        autoCompleteTextViewNamaPasien = findViewById(R.id.auto_complete_nama_pasien);
        autoCompleteTextViewObat1 = findViewById(R.id.auto_complete_obat1);
        autoCompleteTextViewObat2 = findViewById(R.id.auto_complete_obat2);
        autoCompleteTextViewObat3 = findViewById(R.id.auto_complete_obat3);
        autoCompleteTextViewObat4 = findViewById(R.id.auto_complete_obat4);
        autoCompleteTextViewObat5 = findViewById(R.id.auto_complete_obat5);

        cardViewObat2 = findViewById(R.id.card_view_obat2);
        cardViewObat3 = findViewById(R.id.card_view_obat3);
        cardViewObat4 = findViewById(R.id.card_view_obat4);
        cardViewObat5 = findViewById(R.id.card_view_obat5);

        editTextIDResep = findViewById(R.id.edit_text_id_resep);
        editTextJumlah1 = findViewById(R.id.edit_text_jumlah1);
        editTextJumlah2 = findViewById(R.id.edit_text_jumlah2);
        editTextJumlah3 = findViewById(R.id.edit_text_jumlah3);
        editTextJumlah4 = findViewById(R.id.edit_text_jumlah4);
        editTextJumlah5 = findViewById(R.id.edit_text_jumlah5);
        editTextTotal1 = findViewById(R.id.edit_text_total_harga1);
        editTextTotal2 = findViewById(R.id.edit_text_total_harga2);
        editTextTotal3 = findViewById(R.id.edit_text_total_harga3);
        editTextTotal4 = findViewById(R.id.edit_text_total_harga4);
        editTextTotal5 = findViewById(R.id.edit_text_total_harga5);
        editTextSubTotal = findViewById(R.id.edit_text_subtotal);

        buttonTambahObat = findViewById(R.id.button_tambah_obat);
        buttonHapusObat = findViewById(R.id.button_hapus_obat);
        buttonTotalHarga = findViewById(R.id.button_total_harga);

        toolbar = findViewById(R.id.toolbar_editor_resep);

        autoCompleteTextViewObat1.setInputType(InputType.TYPE_NULL);
        autoCompleteTextViewObat2.setInputType(InputType.TYPE_NULL);
        autoCompleteTextViewObat3.setInputType(InputType.TYPE_NULL);
        autoCompleteTextViewObat4.setInputType(InputType.TYPE_NULL);
        autoCompleteTextViewObat5.setInputType(InputType.TYPE_NULL);
    }

    private void viewOnClick(){

        buttonTambahObat.setOnClickListener(v -> {
            if (cardViewObat2.getVisibility() == View.GONE) {
                buttonHapusObat.setVisibility(View.VISIBLE);
                cardViewObat2.setVisibility(View.VISIBLE);
            } else if (cardViewObat3.getVisibility() == View.GONE) {
                cardViewObat3.setVisibility(View.VISIBLE);
            } else if (cardViewObat4.getVisibility() == View.GONE) {
                cardViewObat4.setVisibility(View.VISIBLE);
            } else if (cardViewObat5.getVisibility() == View.GONE) {
                cardViewObat5.setVisibility(View.VISIBLE);
                buttonTambahObat.setVisibility(View.GONE);
            }
        });

        buttonHapusObat.setOnClickListener(v -> {
            if (cardViewObat5.getVisibility() == View.VISIBLE) {
                cardViewObat5.setVisibility(View.GONE);
                buttonTambahObat.setVisibility(View.VISIBLE);
            } else if (cardViewObat4.getVisibility() == View.VISIBLE) {
                cardViewObat4.setVisibility(View.GONE);
            } else if (cardViewObat3.getVisibility() == View.VISIBLE) {
                cardViewObat3.setVisibility(View.GONE);
            } else if (cardViewObat2.getVisibility() == View.VISIBLE) {
                cardViewObat2.setVisibility(View.GONE);
                buttonHapusObat.setVisibility(View.GONE);
            }
        });

        autoCompleteTextViewNamaPasien.setOnItemClickListener((parent, view, position, id) -> {
            nomor_antrian = nomorAntrianListString[position];
            pasien_nama = namaPasienListString[position];
        });

        autoCompleteTextViewObat1.setOnItemClickListener((parent, view, position, id) -> {
            kodeObat1 = kodeObatListString[position];
            hargaObat1 = hargaObatListString[position];
            textInputLayoutTotal1.setHelperText("Harga Satuan Obat : " + numberFormat.format(Integer.parseInt(hargaObat1)));
        });

        autoCompleteTextViewObat2.setOnItemClickListener((parent, view, position, id) -> {
            kodeObat2 = kodeObatListString[position];
            hargaObat2 = hargaObatListString[position];
            textInputLayoutTotal2.setHelperText("Harga Satuan Obat : " + numberFormat.format(Integer.parseInt(hargaObat2)));
        });

        autoCompleteTextViewObat3.setOnItemClickListener((parent, view, position, id) -> {
            kodeObat3 = kodeObatListString[position];
            hargaObat3 = hargaObatListString[position];
            textInputLayoutTotal3.setHelperText("Harga Satuan Obat : " + numberFormat.format(Integer.parseInt(hargaObat3)));
        });

        autoCompleteTextViewObat4.setOnItemClickListener((parent, view, position, id) -> {
            kodeObat4 = kodeObatListString[position];
            hargaObat4 = hargaObatListString[position];
            textInputLayoutTotal4.setHelperText("Harga Satuan Obat : " + numberFormat.format(Integer.parseInt(hargaObat4)));
        });

        autoCompleteTextViewObat5.setOnItemClickListener((parent, view, position, id) -> {
            kodeObat5 = kodeObatListString[position];
            hargaObat5 = hargaObatListString[position];
            textInputLayoutTotal5.setHelperText("Harga Satuan Obat : " + numberFormat.format(Integer.parseInt(hargaObat5)));
        });

        buttonTotalHarga.setOnClickListener(v -> ambilDataInput());
    }

    private void ambilDataInput() {

        if (!Objects.requireNonNull(editTextJumlah5.getText()).toString().isEmpty()) {
            jumlah5 = Integer.parseInt(Objects.requireNonNull(editTextJumlah5.getText()).toString());
            total5 = jumlah5 * Integer.parseInt(hargaObat5);
            editTextTotal5.setText(numberFormat.format(total5));
        }
        if (!Objects.requireNonNull(editTextJumlah4.getText()).toString().isEmpty()) {
            jumlah4 = Integer.parseInt(Objects.requireNonNull(editTextJumlah4.getText()).toString());
            total4 = jumlah4 * Integer.parseInt(hargaObat4);
            editTextTotal4.setText(numberFormat.format(total4));
        }
        if (!Objects.requireNonNull(editTextJumlah3.getText()).toString().isEmpty()) {
            jumlah3 = Integer.parseInt(Objects.requireNonNull(editTextJumlah3.getText()).toString());
            total3 = jumlah3 * Integer.parseInt(hargaObat3);
            editTextTotal3.setText(numberFormat.format(total3));
        }
        if (!Objects.requireNonNull(editTextJumlah2.getText()).toString().isEmpty()) {
            jumlah2 = Integer.parseInt(Objects.requireNonNull(editTextJumlah2.getText()).toString());
            total2 = jumlah2 * Integer.parseInt(hargaObat2);
            editTextTotal2.setText(numberFormat.format(total2));
        }
        if (!Objects.requireNonNull(editTextJumlah1.getText()).toString().isEmpty()) {
            jumlah1 = Integer.parseInt(Objects.requireNonNull(editTextJumlah1.getText()).toString());
            total1 = jumlah1 * Integer.parseInt(hargaObat1);
            editTextTotal1.setText(numberFormat.format(total1));
        }
        if (editTextSubTotal.getVisibility() == View.VISIBLE) {
            subTotal = total1 + total2 + total3 + total4 + total5;
            editTextSubTotal.setText(numberFormat.format(subTotal));
        }

    }

    private void selectIDResep() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Resep>> call = apiInterface.getDaftarIDResep();
        call.enqueue(new Callback<List<Resep>>() {
            @Override
            public void onResponse(@NonNull Call<List<Resep>> call, @NonNull Response<List<Resep>> response) {
                List<Resep> resepList = response.body();

                if (resepList != null && resepList.size() > 0) {
                    String[] idResepListString = new String[resepList.size()];

                    for (int i = 0; i < resepList.size(); i++) {
                        idResepListString[i] = resepList.get(i).getId();

                        editTextIDResep.setText(idResepListString[i]);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Resep>> call, @NonNull Throwable t) {
                Toast.makeText(getBaseContext(), "Terjadi kesalahan saat mengambil data ID Resep", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectDaftarAntrian() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Antrian>> call = apiInterface.getDaftarAntrian();
        call.enqueue(new Callback<List<Antrian>>() {
            @Override
            public void onResponse(@NonNull Call<List<Antrian>> call, @NonNull Response<List<Antrian>> response) {
                List<Antrian> antrianList = response.body();

                if (antrianList != null && antrianList.size() > 0) {
                    namaPasienListString = new String[antrianList.size()];
                    nomorAntrianListString = new String[antrianList.size()];

                    for (int i = 0; i < antrianList.size(); i++) {
                        namaPasienListString[i] = antrianList.get(i).getNama_pasien();
                        nomorAntrianListString[i] = antrianList.get(i).getId();

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), R.layout.dropdown_menu, namaPasienListString);
                        autoCompleteTextViewNamaPasien.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Antrian>> call, @NonNull Throwable t) {
                Toast.makeText(getBaseContext(), "Terjadi kesalahan saat mengambil data antrian", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectDaftarObat() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Obat>> call = apiInterface.getDaftarObat();
        call.enqueue(new Callback<List<Obat>>() {
            @Override
            public void onResponse(@NonNull Call<List<Obat>> call, @NonNull Response<List<Obat>> response) {
                List<Obat> obatList = response.body();

                if (obatList != null && obatList.size() > 0) {
                    kodeObatListString = new String[obatList.size()];
                    namaObatListString = new String[obatList.size()];
                    hargaObatListString = new String[obatList.size()];

                    for (int i = 0; i < obatList.size(); i++) {
                        kodeObatListString[i] = obatList.get(i).getKodeobat();
                        namaObatListString[i] = obatList.get(i).getNamaobat();
                        hargaObatListString[i] = obatList.get(i).getPerubahan_harga();

                        adapter = new ArrayAdapter<>(getBaseContext(), R.layout.dropdown_menu, namaObatListString);
                        autoCompleteTextViewObat1.setAdapter(adapter);
                        autoCompleteTextViewObat2.setAdapter(adapter);
                        autoCompleteTextViewObat3.setAdapter(adapter);
                        autoCompleteTextViewObat4.setAdapter(adapter);
                        autoCompleteTextViewObat5.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Obat>> call, @NonNull Throwable t) {
                Toast.makeText(getBaseContext(), "Terjadi kesalahan saat mengambil data obat", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDataFromIntentExtra() {

        Intent intent = getIntent();
        id_obat = intent.getStringExtra("id_obat");
        kode_obat = intent.getStringExtra("kode_obat");
        nama_obat = intent.getStringExtra("nama_obat");
        jumlah_obat = intent.getStringExtra("jumlah_obat");
        total_obat = intent.getStringExtra("total_obat");

        if (id_obat != null) {
            editTextIDResep.setText(id_obat);
            autoCompleteTextViewObat1.setText(nama_obat);
            editTextJumlah1.setText(jumlah_obat);
            editTextTotal1.setText(numberFormat.format(Integer.parseInt(total_obat)));
            Objects.requireNonNull(getSupportActionBar()).setTitle("Data Resep");
            readMode();
        } else {
            selectIDResep();
        }

    }

    private void editMode() {

        Objects.requireNonNull(getSupportActionBar()).setTitle("Ubah Data Resep");
        autoCompleteTextViewObat1.setEnabled(true);
        editTextJumlah1.setEnabled(true);
        editTextTotal1.setEnabled(true);
        editTextTotal1.setFocusable(false);

        buttonTotalHarga.setVisibility(View.VISIBLE);
    }

    private void readMode() {
        autoCompleteTextViewObat1.setEnabled(false);
        editTextJumlah1.setEnabled(false);
        editTextTotal1.setEnabled(false);

        buttonTambahObat.setVisibility(View.GONE);
        buttonTotalHarga.setVisibility(View.GONE);
        editTextSubTotal.setVisibility(View.GONE);
        textInputLayoutNamaPasien.setVisibility(View.GONE);
        autoCompleteTextViewNamaPasien.setVisibility(View.GONE);
        textViewObat1.setVisibility(View.GONE);

        int harga_obat = Integer.parseInt(total_obat) / Integer.parseInt(jumlah_obat);
        hargaObat1 = Integer.toString(harga_obat);
    }
}
