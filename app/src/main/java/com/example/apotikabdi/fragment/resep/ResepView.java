package com.example.apotikabdi.fragment.resep;

import com.example.apotikabdi.model.Resep;

import java.util.List;

public interface ResepView {

    void showLoad();

    void hideLoad();

    void onGetResult(List<Resep> resepList);

    void onErrorLoad(String message);
}
