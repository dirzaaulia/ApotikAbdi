package com.example.apotikabdi.fragment.pasien;

import com.example.apotikabdi.model.Pasien;

import java.util.List;

public interface PasienView {

    void showLoad();

    void hideLoad();

    void onGetResult(List<Pasien> pasienList);

    void onErrorLoad(String message);
}
