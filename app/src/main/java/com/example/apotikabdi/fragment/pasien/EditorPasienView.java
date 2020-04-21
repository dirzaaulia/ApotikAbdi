package com.example.apotikabdi.fragment.pasien;

public interface EditorPasienView {

    void showProgress();

    void hideProgress();

    void onRequestSuccess(String message);

    void onRequestError(String message);
}
