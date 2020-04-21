package com.example.apotikabdi.fragment.resep;

import com.example.apotikabdi.api.ApiClient;
import com.example.apotikabdi.api.ApiInterface;
import com.example.apotikabdi.model.PengambilanObat;
import com.example.apotikabdi.model.Resep;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class EditorResepPresenter {

    private EditorResepView view;

    EditorResepPresenter(EditorResepView view) {
        this.view = view;
    }

    void tambahResep(final String id, final String obat_id, final String jumlah, final String totalharga) {

        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Resep> call = apiInterface.tambahResep(id, obat_id, jumlah, totalharga);
        call.enqueue(new Callback<Resep>() {
            @Override
            public void onResponse(@NonNull Call<Resep> call, @NonNull Response<Resep> response) {
                view.hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<Resep> call, @NonNull Throwable t) {
                view.hideProgress();
            }
        });
    }

    void tambahPengambilanObat(final String nomor_antrian, final String pasien_nama, final String resep_id, final String totalbiaya) {

        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<PengambilanObat> call = apiInterface.tambahPengambilanObat(nomor_antrian, pasien_nama, resep_id, totalbiaya);
        call.enqueue(new Callback<PengambilanObat>() {
            @Override
            public void onResponse(@NonNull Call<PengambilanObat> call, @NonNull Response<PengambilanObat> response) {

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
            public void onFailure(@NonNull Call<PengambilanObat> call, @NonNull Throwable t) {

                view.hideProgress();
                view.onRequestError("Terjadi kesalahan saat menambahkan data pasien");

            }
        });
    }

    void ubahResep(String id, String obat_id_lama, String kode_obat, String jumlah, String totalharga) {

        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Resep> call = apiInterface.ubahResep(id, obat_id_lama, kode_obat, jumlah, totalharga);
        call.enqueue(new Callback<Resep>() {
            @Override
            public void onResponse(@NonNull Call<Resep> call, @NonNull Response<Resep> response) {

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
            public void onFailure(@NonNull Call<Resep> call, @NonNull Throwable t) {

                view.hideProgress();
                view.onRequestError("Terjadi kesalahan saat menambahkan data pasien");

            }
        });
    }

    void hapusResep(String id, String kode_obat) {

        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Resep> call = apiInterface.hapusResep(id, kode_obat);
        call.enqueue(new Callback<Resep>() {
            @Override
            public void onResponse(@NonNull Call<Resep> call, @NonNull Response<Resep> response) {

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
            public void onFailure(@NonNull Call<Resep> call, @NonNull Throwable t) {

                view.hideProgress();
                view.onRequestError("Terjadi kesalahan saat menambahkan data pasien");

            }
        });
    }
}
