package com.example.apotikabdi.fragment.antrian;

import com.example.apotikabdi.api.ApiClient;
import com.example.apotikabdi.api.ApiInterface;
import com.example.apotikabdi.model.Antrian;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class EditorAntrianPresenter {

    private EditorAntrianView view;

    EditorAntrianPresenter(EditorAntrianView view) {
        this.view = view;
    }

    void tambahAntrian(final String nama_pasien, final String keluhan) {

        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Antrian> call = apiInterface.tambahAntrian(nama_pasien, keluhan);
        call.enqueue(new Callback<Antrian>() {
            @Override
            public void onResponse(@NonNull Call<Antrian> call, @NonNull Response<Antrian> response) {

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
            public void onFailure(@NonNull Call<Antrian> call, @NonNull Throwable t) {

                view.hideProgress();
                view.onRequestError("Terjadi kesalahan saat menambahkan data antrian");
            }
        });
    }

    void ubahAntrian(String id, String nama_pasien, String keluhan) {

        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Antrian> call = apiInterface.ubahAntrian(id, nama_pasien, keluhan);
        call.enqueue(new Callback<Antrian>() {
            @Override
            public void onResponse(@NonNull Call<Antrian> call, @NonNull Response<Antrian> response) {

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
            public void onFailure(@NonNull Call<Antrian> call, @NonNull Throwable t) {

                view.hideProgress();
                view.onRequestError("Terjadi kesalahan saat mengubah data antrian");

            }
        });
    }

    void hapusAntrian(String id) {

        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Antrian> call = apiInterface.hapusAntrian(id);
        call.enqueue(new Callback<Antrian>() {
            @Override
            public void onResponse(@NonNull Call<Antrian> call, @NonNull Response<Antrian> response) {
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
            public void onFailure(@NonNull Call<Antrian> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onRequestError("Terjadi kesalahan saat menghapus data antrian");
            }
        });
    }
}
