package com.example.apotikabdi.fragment.pasien;

import com.example.apotikabdi.api.ApiClient;
import com.example.apotikabdi.api.ApiInterface;
import com.example.apotikabdi.model.Pasien;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class EditorPasienPresenter {

    private EditorPasienView view;

    EditorPasienPresenter(EditorPasienView view) {
        this.view = view;
    }

    void tambahPasien(final String no_rekammedis, final String nama, final String tanggal_lahir, final String jenis_kelamin, final String alamat, final String kode_pos, final String nohp, final String email) {

        view.showProgress();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Pasien> call = apiInterface.tambahPasien(no_rekammedis, nama, tanggal_lahir, jenis_kelamin, alamat, kode_pos, nohp, email);
        call.enqueue(new Callback<Pasien>() {
            @Override
            public void onResponse(@NonNull Call<Pasien> call, @NonNull Response<Pasien> response) {

                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {

                    Boolean success = response.body().getSuccess();
                    if (success) {
                        view.onRequestSuccess(response.body().getMessage());
                    } else {
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Pasien> call, @NonNull Throwable t) {

                view.hideProgress();
                view.onRequestError("Terjadi kesalahan saat menambahkan data pasien");

            }
        });
    }

    void ubahPasien(String id, String no_rekammedis, String nama, String tanggal_lahir, String jenis_kelamin, String alamat, String kode_pos, String nohp, String email) {

        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Pasien> call = apiInterface.ubahPasien(id, no_rekammedis, nama, tanggal_lahir, jenis_kelamin, alamat, kode_pos,  nohp, email);
        call.enqueue(new Callback<Pasien>() {
            @Override
            public void onResponse(@NonNull Call<Pasien> call, @NonNull Response<Pasien> response) {

                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {

                    Boolean success = response.body().getSuccess();
                    if (success) {
                        view.onRequestSuccess(response.body().getMessage());
                    } else {
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Pasien> call, @NonNull Throwable t) {

                view.hideProgress();
                view.onRequestError("Terjadi kesalahan saat mengubah data pasien");

            }
        });
    }

    void hapusPasien(String id) {

        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Pasien> call = apiInterface.hapusPasien(id);
        call.enqueue(new Callback<Pasien>() {
            @Override
            public void onResponse(@NonNull Call<Pasien> call, @NonNull Response<Pasien> response) {
                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {

                    Boolean success = response.body().getSuccess();
                    if (success) {
                        view.onRequestSuccess(response.body().getMessage());
                    } else {
                        view.onRequestSuccess(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Pasien> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onRequestError("Terjadi kesalahan saat menghapus data pasien");
            }
        });
    }
}
