package com.example.apotikabdi.fragment.rekomendasi;

import com.example.apotikabdi.api.ApiClient;
import com.example.apotikabdi.api.ApiInterface;
import com.example.apotikabdi.model.Resep;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekomendasiPresenter {

    private RekomendasiView view;

    RekomendasiPresenter(RekomendasiView view) {
        this.view = view;
    }

    void getDaftarRekomendasi() {

        view.showLoad();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Resep>> call = apiInterface.getDaftarRekomendasi();
        call.enqueue(new Callback<List<Resep>>() {
            @Override
            public void onResponse(@NonNull Call<List<Resep>> call, @NonNull Response<List<Resep>> response) {

                view.hideLoad();
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Resep>> call, @NonNull Throwable t) {

                view.hideLoad();
                view.onErrorLoad("Terjadi kesalahan saat mengambil data pasien");
            }
        });
    }
}
