package com.example.apotikabdi.fragment.resep;

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

public class ResepAdapter extends RecyclerView.Adapter<ResepAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Resep> resepList;
    private ItemClickListener itemClickListener;

    ResepAdapter(Context context, List<Resep> resepList, ItemClickListener itemClickListener) {
        this.context = context;
        this.resepList = resepList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_resep, parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {

        Resep resep = resepList.get(position);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

        String textJumlah = "Jumlah : " + resep.getJumlah();
        String textTotalHarga = "Total Harga : " + numberFormat.format(Integer.parseInt(resep.getTotalharga()));

        holder.textViewIDResep.setText(resep.getId());
        holder.textViewKodeObat.setText(resep.getObat_id());
        holder.textViewNamaObat.setText(resep.getNama_obat());
        holder.textViewJumlahObat.setText(textJumlah);
        holder.textViewTotalHargaObat.setText(textTotalHarga);
    }

    @Override
    public int getItemCount() {
        return resepList.size();
    }

    static class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewIDResep, textViewKodeObat, textViewNamaObat, textViewJumlahObat, textViewTotalHargaObat;
        MaterialCardView materialCardView;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            textViewIDResep = itemView.findViewById(R.id.id_resep);
            textViewKodeObat = itemView.findViewById(R.id.kode_obat);
            textViewNamaObat = itemView.findViewById(R.id.nama_obat);
            textViewJumlahObat = itemView.findViewById(R.id.jumlah_obat);
            textViewTotalHargaObat = itemView.findViewById(R.id.total_harga_obat);
            materialCardView = itemView.findViewById(R.id.card_daftar_resep);


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
