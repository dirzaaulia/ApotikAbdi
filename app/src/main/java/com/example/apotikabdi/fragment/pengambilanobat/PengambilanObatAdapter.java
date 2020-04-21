package com.example.apotikabdi.fragment.pengambilanobat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apotikabdi.R;
import com.example.apotikabdi.model.PengambilanObat;
import com.google.android.material.card.MaterialCardView;

import java.text.NumberFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PengambilanObatAdapter extends RecyclerView.Adapter<PengambilanObatAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<PengambilanObat> pengambilanObatList;
    private ItemClickListener itemClickListener;

    PengambilanObatAdapter(Context context, List<PengambilanObat> pengambilanObatList, ItemClickListener itemClickListener) {
        this.context = context;
        this.pengambilanObatList = pengambilanObatList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_pengambilan_obat, parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {

        PengambilanObat pengambilanObat = pengambilanObatList.get(position);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

        String textNomorResep = "Nomor Resep : " + pengambilanObat.getResep_id();
        String textTotalBiaya = "Total Biaya : " + numberFormat.format(Integer.parseInt(pengambilanObat.getTotalbiaya()));

        holder.textViewIDPengambilanObat.setText(pengambilanObat.getId());
        holder.textViewNamaPasien.setText(pengambilanObat.getPasien_nama());
        holder.textViewIDResep.setText(textNomorResep);
        holder.textViewTanggal.setText(pengambilanObat.getTanggal());
        holder.textViewTotalBiaya.setText(textTotalBiaya);
    }

    @Override
    public int getItemCount() {
        return pengambilanObatList.size();
    }

    static class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewIDPengambilanObat, textViewNamaPasien, textViewIDResep, textViewTanggal, textViewTotalBiaya;
        MaterialCardView materialCardView;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            textViewIDPengambilanObat = itemView.findViewById(R.id.id_pengambilan_obat);
            textViewNamaPasien = itemView.findViewById(R.id.nama_pasien_pengambilan_obat);
            textViewIDResep = itemView.findViewById(R.id.resep_id);
            textViewTanggal = itemView.findViewById(R.id.tanggal);
            textViewTotalBiaya = itemView.findViewById(R.id.total_biaya);
            materialCardView = itemView.findViewById(R.id.card_daftar_pengambilan_obat);

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
