package com.example.apotikabdi.fragment.pengambilanobat;

public interface EditorPengambilanObatView {
    void showProgress();

    void hideProgress();

    void onRequestSuccess(String message);

    void onRequestError(String message);
}
