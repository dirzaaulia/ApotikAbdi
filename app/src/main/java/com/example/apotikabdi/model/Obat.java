package com.example.apotikabdi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Obat {
    @Expose
    @SerializedName("kodeobat") private String kodeobat;
    @Expose
    @SerializedName("namaobat") private String namaobat;
    @Expose
    @SerializedName("satuan") private String satuan;
    @Expose
    @SerializedName("harga") private String harga;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;

    public String getKodeobat() {
        return kodeobat;
    }

    public void setKodeobat(String kodeobat) {
        this.kodeobat = kodeobat;
    }

    public String getNamaobat() {
        return namaobat;
    }

    public void setNamaobat(String namaobat) {
        this.namaobat = namaobat;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
