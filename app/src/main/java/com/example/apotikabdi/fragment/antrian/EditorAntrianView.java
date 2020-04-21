package com.example.apotikabdi.fragment.antrian;

public interface EditorAntrianView {

    void showProgress();

    void hideProgress();

    void onRequestSuccess(String message);

    void onRequestError(String message);
}
