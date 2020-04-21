package com.example.apotikabdi.fragment.antrian;

import com.example.apotikabdi.model.Antrian;

import java.util.List;

public interface AntrianView {

    void showLoad();

    void hideLoad();

    void onGetResult(List<Antrian> antrianList);

    void onErrorLoad(String message);
}
