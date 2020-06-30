package com.example.apotikabdi.fragment.rekomendasi;

public interface EditorRekomendasiView {
    void showProgress();

    void hideProgress();

    void onRequestSuccess(String message);

    void onRequestError(String message);
}
