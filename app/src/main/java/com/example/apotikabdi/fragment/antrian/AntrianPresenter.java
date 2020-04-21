package com.example.apotikabdi.fragment.antrian;

import com.example.apotikabdi.api.ApiClient;
import com.example.apotikabdi.api.ApiInterface;
import com.example.apotikabdi.model.Antrian;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class AntrianPresenter {

    private AntrianView view;

    AntrianPresenter(AntrianView view) {
        this.view = view;
    }

    void getDaftarAntrian() {

        view.showLoad();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Antrian>> call = apiInterface.getDaftarAntrian();
        call.enqueue(new Callback<List<Antrian>>() {
            @Override
            public void onResponse(@NonNull Call<List<Antrian>> call, @NonNull Response<List<Antrian>> response) {
                view.hideLoad();
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Antrian>> call, @NonNull Throwable t) {
                view.hideLoad();
                view.onErrorLoad("Terjadi kesalahan saat mengambil data antrian");
            }
        });
    }
}
