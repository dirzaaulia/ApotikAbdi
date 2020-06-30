package com.example.apotikabdi.fragment.rekomendasi;

import com.example.apotikabdi.api.ApiClient;
import com.example.apotikabdi.api.ApiInterface;
import com.example.apotikabdi.model.Obat;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorRekomendasiPresenter {

    private EditorRekomendasiView view;

    EditorRekomendasiPresenter(EditorRekomendasiView view) {
        this.view = view;
    }

    void terapkanRekomendasi(String kodeobat, String perubahan_harga) {

        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Obat> call = apiInterface.terapkanRekomendasi(kodeobat , perubahan_harga);
        call.enqueue(new Callback<Obat>() {
            @Override
            public void onResponse(@NonNull Call<Obat> call, @NonNull Response<Obat> response) {

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
            public void onFailure(@NonNull Call<Obat> call, @NonNull Throwable t) {

                view.hideProgress();
                view.onRequestError("Terjadi kesalahan saat menambahkan data pasien");

            }
        });
    }
}
