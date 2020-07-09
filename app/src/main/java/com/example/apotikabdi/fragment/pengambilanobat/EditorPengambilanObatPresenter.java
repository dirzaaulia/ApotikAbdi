package com.example.apotikabdi.fragment.pengambilanobat;

import com.example.apotikabdi.api.ApiClient;
import com.example.apotikabdi.api.ApiInterface;
import com.example.apotikabdi.model.PengambilanObat;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorPengambilanObatPresenter {
    private EditorPengambilanObatView view;

    EditorPengambilanObatPresenter(EditorPengambilanObatView view) {
        this.view = view;
    }

    void ubahPengambilanObat(String id) {

        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<PengambilanObat> call = apiInterface.ubahPengambilanObat(id, "Dibayar");
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
                view.onRequestError("Terjadi kesalahan saat memperbaharui data pengambilan obat");

            }
        });
    }
}
