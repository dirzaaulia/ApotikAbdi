package com.example.apotikabdi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pasien {

    @Expose
    @SerializedName("id") private String id;
    @Expose
    @SerializedName("no_rekammedis") private String no_rekammedis;
    @Expose
    @SerializedName("nama") private String nama;
    @Expose
    @SerializedName("tanggal_lahir") private String tanggal_lahir;
    @Expose
    @SerializedName("jenis_kelamin") private String jenis_kelamin;
    @Expose
    @SerializedName("alamat") private String alamat;
    @Expose
    @SerializedName("kode_pos") private String kode_pos;
    @Expose
    @SerializedName("nohp") private String nohp;
    @Expose
    @SerializedName("email") private String email;
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

    public String getNo_rekammedis() {
        return no_rekammedis;
    }

    public void setNo_rekammedis(String no_rekammedis) {
        this.no_rekammedis = no_rekammedis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKode_pos() {
        return kode_pos;
    }

    public void setKode_pos(String kode_pos) {
        this.kode_pos = kode_pos;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
