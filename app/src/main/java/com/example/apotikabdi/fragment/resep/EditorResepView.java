package com.example.apotikabdi.fragment.resep;

public interface EditorResepView {

    void showProgress();

    void hideProgress();

    void onRequestSuccess(String message);

    void onRequestError(String message);
}
