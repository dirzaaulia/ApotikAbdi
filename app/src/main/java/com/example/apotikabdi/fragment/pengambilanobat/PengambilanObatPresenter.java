package com.example.apotikabdi.fragment.pengambilanobat;

import com.example.apotikabdi.api.ApiClient;
import com.example.apotikabdi.api.ApiInterface;
import com.example.apotikabdi.model.PengambilanObat;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class PengambilanObatPresenter {

    private PengambilanObatView view;

    PengambilanObatPresenter(PengambilanObatView view) {
        this.view = view;
    }

    void getDaftarPengambilanObat() {

        view.showLoad();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<PengambilanObat>> call = apiInterface.getDaftarPengambilanObat();
        call.enqueue(new Callback<List<PengambilanObat>>() {
            @Override
            public void onResponse(@NonNull Call<List<PengambilanObat>> call, @NonNull Response<List<PengambilanObat>> response) {

                view.hideLoad();
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PengambilanObat>> call, @NonNull Throwable t) {

                view.hideLoad();
                view.onErrorLoad("Terjadi kesalahan saat mengambil data pengambilan obat");
            }
        });
    }
}
