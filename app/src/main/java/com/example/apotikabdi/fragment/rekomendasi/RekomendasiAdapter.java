package com.example.apotikabdi.fragment.rekomendasi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apotikabdi.R;
import com.example.apotikabdi.model.Resep;
import com.google.android.material.card.MaterialCardView;

import java.text.NumberFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RekomendasiAdapter extends RecyclerView.Adapter<RekomendasiAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Resep> resepList;
    private ItemClickListener itemClickListener;

    RekomendasiAdapter(Context context, List<Resep> resepList, ItemClickListener itemClickListener) {
        this.context = context;
        this.resepList = resepList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_rekomendasi, parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {

        Resep resep = resepList.get(position);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

        String textJumlah = "Jumlah Transaksi Obat : " + resep.getJumlah();
        String textHarga = "Harga Satuan : " + numberFormat.format(Integer.parseInt(resep.getTotalharga()));
        String textDiskon = "Perubahan Harga : " + numberFormat.format(Integer.parseInt(resep.getHargadiskon()));

        holder.textViewKodeObat.setText(resep.getObat_id());
        holder.textViewNamaObat.setText(resep.getNama_obat());
        holder.textViewJumlahObat.setText(textJumlah);
        holder.textViewTotalHargaObat.setText(textHarga);
        holder.textViewHargaDiskonObat.setText(textDiskon);
    }

    @Override
    public int getItemCount() {
        return resepList.size();
    }

    static class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewKodeObat, textViewNamaObat, textViewJumlahObat, textViewTotalHargaObat, textViewHargaDiskonObat;
        MaterialCardView materialCardView;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            textViewKodeObat = itemView.findViewById(R.id.kode_obat);
            textViewNamaObat = itemView.findViewById(R.id.nama_obat);
            textViewJumlahObat = itemView.findViewById(R.id.jumlah_obat);
            textViewTotalHargaObat = itemView.findViewById(R.id.total_harga_obat);
            textViewHargaDiskonObat = itemView.findViewById(R.id.harga_diskon_obat);
            materialCardView = itemView.findViewById(R.id.card_daftar_rekomendasi);


            this.itemClickListener = itemClickListener;
            materialCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
