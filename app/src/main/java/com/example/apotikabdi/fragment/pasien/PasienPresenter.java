package com.example.apotikabdi.fragment.pasien;

import com.example.apotikabdi.api.ApiClient;
import com.example.apotikabdi.api.ApiInterface;
import com.example.apotikabdi.model.Pasien;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class PasienPresenter {

    private PasienView view;

    PasienPresenter(PasienView view) {
        this.view = view;
    }

    void getDaftarPasien(String key) {

        view.showLoad();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Pasien>> call = apiInterface.getDaftarPasien(key);
        call.enqueue(new Callback<List<Pasien>>() {
            @Override
            public void onResponse(@NonNull Call<List<Pasien>> call, @NonNull Response<List<Pasien>> response) {

                view.hideLoad();
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Pasien>> call, @NonNull Throwable t) {

                view.hideLoad();
                view.onErrorLoad("Terjadi kesalahan saat mengambil data pasien");
            }
        });
    }
}
