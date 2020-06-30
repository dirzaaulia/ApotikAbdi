package com.example.apotikabdi.api;

import com.example.apotikabdi.model.Antrian;
import com.example.apotikabdi.model.Obat;
import com.example.apotikabdi.model.Pasien;
import com.example.apotikabdi.model.PengambilanObat;
import com.example.apotikabdi.model.Resep;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("pasien/tambahdatapasien.php")
    Call<Pasien> tambahPasien(
            @Field("no_rekammedis") String no_rekammedis,
            @Field("nama") String nama,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("alamat") String alamat,
            @Field("nohp") String nohp
    );

    @GET("pasien/ambildatapasien.php")
    Call<List<Pasien>> getDaftarPasien(
            @Query("key") String keyword
    );

    @FormUrlEncoded
    @POST("pasien/ubahdatapasien.php")
    Call<Pasien> ubahPasien(
            @Field("id") String id,
            @Field("no_rekammedis") String no_rekammedis,
            @Field("nama") String nama,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("alamat") String alamat,
            @Field("nohp") String nohp
    );

    @FormUrlEncoded
    @POST("pasien/hapusdatapasien.php")
    Call<Pasien> hapusPasien(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("antrian/tambahdataantrian.php")
    Call<Antrian> tambahAntrian(
            @Field("nama_pasien") String nama_pasien,
            @Field("keluhan") String keluhan
    );

    @GET("antrian/ambildataantrian.php")
    Call<List<Antrian>> getDaftarAntrian();

    @FormUrlEncoded
    @POST("antrian/ubahdataantrian.php")
    Call<Antrian> ubahAntrian(
            @Field("id") String id,
            @Field("nama_pasien") String nama_pasien,
            @Field("keluhan") String keluhan
    );

    @FormUrlEncoded
    @POST("antrian/hapusdataantrian.php")
    Call<Antrian> hapusAntrian(
            @Field("id") String id
    );

    @GET("obat/ambildataobat.php")
    Call<List<Obat>> getDaftarObat();

    @GET("resep/ambilidresep.php")
    Call<List<Resep>> getDaftarIDResep();

    @GET("resep/ambildataresep.php")
    Call<List<Resep>> getDaftarResep();

    @FormUrlEncoded
    @POST("resep/tambahdataresep.php")
    Call<Resep> tambahResep(
            @Field("id") String id,
            @Field("obat_id") String obat_id,
            @Field("jumlah") String jumlah,
            @Field("totalharga") String totalharga
    );

    @FormUrlEncoded
    @POST("resep/ubahdataresep.php")
    Call<Resep> ubahResep(
            @Field("id") String id,
            @Field("obat_id_lama") String obat_id_lama,
            @Field("obat_id") String obat_id,
            @Field("jumlah") String jumlah,
            @Field("totalharga") String totalharga
    );

    @FormUrlEncoded
    @POST("resep/hapusdataresep.php")
    Call<Resep> hapusResep(
            @Field("id") String id,
            @Field("obat_id") String obat_id
    );

    @FormUrlEncoded
    @POST("pengambilanobat/tambahpengambilanobat.php")
    Call<PengambilanObat> tambahPengambilanObat(
            @Field("nomor_antrian") String nomor_antrian,
            @Field("pasien_nama") String pasien_nama,
            @Field("resep_id") String resep_id,
            @Field("totalbiaya") String totalbiaya
    );

    @GET("pengambilanobat/ambildatapengambilanobat.php")
    Call<List<PengambilanObat>> getDaftarPengambilanObat();

    @GET("rekomendasi/rekomendasiharga.php")
    Call<List<Resep>> getDaftarRekomendasi();

    @FormUrlEncoded
    @POST("rekomendasi/terapkanrekomendasi.php")
    Call<Obat> terapkanRekomendasi(
            @Field("kodeobat") String kodeobat,
            @Field("perubahan_harga") String perubahan_harga
    );
}
