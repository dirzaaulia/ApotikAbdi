package com.example.apotikabdi.fragment.pengambilanobat;

import com.example.apotikabdi.model.PengambilanObat;

import java.util.List;

public interface PengambilanObatView {

    void showLoad();

    void hideLoad();

    void onGetResult(List<PengambilanObat> pengambilanObatList);

    void onErrorLoad(String message);
}
