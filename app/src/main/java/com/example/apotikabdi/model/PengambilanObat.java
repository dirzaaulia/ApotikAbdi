package com.example.apotikabdi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PengambilanObat {

    @Expose
    @SerializedName("id") private String id;
    @Expose
    @SerializedName("nomor_antrian") private String nomor_antrian;
    @Expose
    @SerializedName("pasien_nama") private String pasien_nama;
    @Expose
    @SerializedName("tanggal") private String tanggal;
    @Expose
    @SerializedName("resep_id") private String resep_id;
    @Expose
    @SerializedName("totalbiaya") private String totalbiaya;
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

    public String getNomor_antrian() {
        return nomor_antrian;
    }

    public void setNomor_antrian(String nomor_antrian) {
        this.nomor_antrian = nomor_antrian;
    }

    public String getPasien_nama() {
        return pasien_nama;
    }

    public void setPasien_nama(String pasien_nama) {
        this.pasien_nama = pasien_nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getResep_id() {
        return resep_id;
    }

    public void setResep_id(String resep_id) {
        this.resep_id = resep_id;
    }

    public String getTotalbiaya() {
        return totalbiaya;
    }

    public void setTotalbiaya(String totalbiaya) {
        this.totalbiaya = totalbiaya;
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
