package com.example.apotikabdi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resep {
    @Expose
    @SerializedName("id") private String id;
    @Expose
    @SerializedName("obat_id") private String obat_id;
    @Expose
    @SerializedName("nama_obat") private String nama_obat;
    @Expose
    @SerializedName("jumlah") private String jumlah;
    @Expose
    @SerializedName("totalharga") private String totalharga;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObat_id() {
        return obat_id;
    }

    public void setObat_id(String obat_id) {
        this.obat_id = obat_id;
    }

    public String getNama_obat() {
        return nama_obat;
    }

    public void setNama_obat(String nama_obat) {
        this.nama_obat = nama_obat;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTotalharga() {
        return totalharga;
    }

    public void setTotalharga(String totalharga) {
        this.totalharga = totalharga;
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
