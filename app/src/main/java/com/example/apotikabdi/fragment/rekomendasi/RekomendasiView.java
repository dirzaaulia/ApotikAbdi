package com.example.apotikabdi.fragment.rekomendasi;

import com.example.apotikabdi.model.Resep;

import java.util.List;

public interface RekomendasiView {
    void showLoad();

    void hideLoad();

    void onGetResult(List<Resep> resepList);

    void onErrorLoad(String message);
}
