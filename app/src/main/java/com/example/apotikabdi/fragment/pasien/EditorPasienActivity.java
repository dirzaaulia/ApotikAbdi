package com.example.apotikabdi.fragment.pasien;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.apotikabdi.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditorPasienActivity extends AppCompatActivity implements EditorPasienView {

    TextInputEditText editTextRekamMedis, editTextNamaPasien, editTextTanggalLahir, editTextAlamat, editTextNomorHandphone;
    RadioGroup radioGroupJenisKelamin;
    RadioButton radioButtonJenisKelamin, radioButtonLakiLaki, radioButtonPerempuan;
    ProgressDialog progressDialog;
    Menu actionMenu;
    Toolbar toolbar;

    String id, no_rekammedis, nama, tanggal_lahir, jenis_kelamin, alamat, nohp;

    EditorPasienPresenter editorPasienPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_pasien);

        bindViews();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Tambah Data Pasien");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        setDataFromIntentExtra();

        editTextTanggalLahir.setOnLongClickListener(v -> {
            showCalendar();
            return false;
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon tunggu, data sedang ditambahkan...");

        editorPasienPresenter = new EditorPasienPresenter(this);
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

        int idJenisKelamin = radioGroupJenisKelamin.getCheckedRadioButtonId();
        radioButtonJenisKelamin = findViewById(idJenisKelamin);
        no_rekammedis = Objects.requireNonNull(editTextRekamMedis.getText()).toString().trim();
        nama = Objects.requireNonNull(editTextNamaPasien.getText()).toString().trim();
        tanggal_lahir = Objects.requireNonNull(editTextTanggalLahir.getText()).toString().trim();
        jenis_kelamin = radioButtonJenisKelamin.getText().toString().trim();
        alamat = Objects.requireNonNull(editTextAlamat.getText()).toString().trim();
        nohp = Objects.requireNonNull(editTextNomorHandphone.getText()).toString().trim();

        switch (item.getItemId()) {
            case R.id.edit:

                editMode();
                actionMenu.findItem(R.id.edit).setVisible(false);
                actionMenu.findItem(R.id.delete).setVisible(false);
                actionMenu.findItem(R.id.save).setVisible(false);
                actionMenu.findItem(R.id.update).setVisible(true);

                return true;

            case R.id.save:

                if (no_rekammedis.isEmpty()) {
                    editTextRekamMedis.setError("Nomor rekam medis tidak boleh kosong");
                } else if (nama.isEmpty()) {
                    editTextNamaPasien.setError("Nama pasien tidak boleh kosong");
                } else if (tanggal_lahir.isEmpty()) {
                    editTextTanggalLahir.setError("Tanggal lahir pasien tidak boleh kosong");
                } else if (alamat.isEmpty()) {
                    editTextAlamat.setError("Alamat pasien tidak boleh kosong");
                } else if (nohp.isEmpty()) {
                    editTextNomorHandphone.setError("Nomor handphone pasien tidak boleh kosong");
                } else {
                    editorPasienPresenter.tambahPasien(no_rekammedis, nama, tanggal_lahir, jenis_kelamin, alamat, nohp);
                }

                return true;

            case R.id.update:

                if (no_rekammedis.isEmpty()) {
                    editTextRekamMedis.setError("Nomor rekam medis tidak boleh kosong");
                } else if (nama.isEmpty()) {
                    editTextNamaPasien.setError("Nama pasien tidak boleh kosong");
                } else if (tanggal_lahir.isEmpty()) {
                    editTextTanggalLahir.setError("Tanggal lahir pasien tidak boleh kosong");
                } else if (alamat.isEmpty()) {
                    editTextAlamat.setError("Alamat pasien tidak boleh kosong");
                } else if (nohp.isEmpty()) {
                    editTextNomorHandphone.setError("Nomor handphone pasien tidak boleh kosong");
                } else {
                    editorPasienPresenter.ubahPasien(id, no_rekammedis, nama, tanggal_lahir, jenis_kelamin, alamat, nohp);
                }

                return true;

            case R.id.delete:

                new MaterialAlertDialogBuilder(this)
                        .setTitle("Hapus Data Pasien")
                        .setMessage("Apakah anda yakin untuk menghapus data pasien ini?")
                        .setPositiveButton("Ya", (dialog, which) -> {
                            dialog.dismiss();
                            editorPasienPresenter.hapusPasien(id);
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

    private void setDataFromIntentExtra() {

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        nama = intent.getStringExtra("nama");
        no_rekammedis = intent.getStringExtra("no_rekammedis");
        tanggal_lahir = intent.getStringExtra("tanggal_lahir");
        jenis_kelamin = intent.getStringExtra("jenis_kelamin");
        alamat = intent.getStringExtra("alamat");
        nohp = intent.getStringExtra("nohp");

        if (id != null) {

            editTextRekamMedis.setText(no_rekammedis);
            editTextNamaPasien.setText(nama);
            editTextTanggalLahir.setText(tanggal_lahir);
            editTextAlamat.setText(alamat);
            editTextNomorHandphone.setText(nohp);

            if (jenis_kelamin.equals("laki-laki")) {
                radioGroupJenisKelamin.clearCheck();
                radioGroupJenisKelamin.check(R.id.laki_laki);
            } else if (jenis_kelamin.equals("perempuan")) {
                radioGroupJenisKelamin.clearCheck();
                radioGroupJenisKelamin.check(R.id.perempuan);
            }

            Objects.requireNonNull(getSupportActionBar()).setTitle("Data Pasien");
            readMode();

        }
    }

    private void editMode() {

        Objects.requireNonNull(getSupportActionBar()).setTitle("Ubah Data Pasien");
        editTextRekamMedis.setEnabled(true);
        editTextNamaPasien.setEnabled(true);
        editTextTanggalLahir.setEnabled(true);
        editTextAlamat.setEnabled(true);
        editTextNomorHandphone.setEnabled(true);
        radioButtonLakiLaki.setClickable(true);
        radioButtonPerempuan.setClickable(true);
    }

    private void readMode() {

        editTextRekamMedis.setEnabled(false);
        editTextNamaPasien.setEnabled(false);
        editTextTanggalLahir.setEnabled(false);
        editTextAlamat.setEnabled(false);
        editTextNomorHandphone.setEnabled(false);
        radioButtonLakiLaki.setClickable(false);
        radioButtonPerempuan.setClickable(false);
    }

    private void bindViews() {

        editTextRekamMedis = findViewById(R.id.nomor_rekammedis);
        editTextNamaPasien = findViewById(R.id.nama_pasien);
        editTextTanggalLahir = findViewById(R.id.tanggal_lahir);
        editTextAlamat = findViewById(R.id.alamat);
        editTextNomorHandphone = findViewById(R.id.nomor_handphone);

        radioGroupJenisKelamin = findViewById(R.id.jenis_kelamin);
        radioButtonLakiLaki = findViewById(R.id.laki_laki);
        radioButtonPerempuan = findViewById(R.id.perempuan);

        toolbar = findViewById(R.id.toolbar_editor_pasien);
    }

    private void showCalendar() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Pilih Tanggal Lahir Pasien");
        builder.setSelection(Calendar.getInstance().getTimeInMillis());

        MaterialDatePicker<Long> picker = builder.build();
        picker.show(getSupportFragmentManager(), picker.toString());
        picker.addOnPositiveButtonClickListener(selection -> editTextTanggalLahir.setText(picker.getHeaderText()));
    }
}
