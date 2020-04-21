package com.example.apotikabdi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Antrian {

    @Expose
    @SerializedName("id") private String id;
    @Expose
    @SerializedName("nama_pasien") private String nama_pasien;
    @Expose
    @SerializedName("keluhan") private String keluhan;
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

    public String getNama_pasien() {
        return nama_pasien;
    }

    public void setNama_pasien(String nama_pasien) {
        this.nama_pasien = nama_pasien;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
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
