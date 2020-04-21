package com.example.apotikabdi.fragment.pasien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apotikabdi.R;
import com.example.apotikabdi.model.Pasien;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PasienAdapter extends RecyclerView.Adapter<PasienAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Pasien> pasienList;
    private ItemClickListener itemClickListener;

    PasienAdapter(Context context, List<Pasien> pasienList, ItemClickListener itemClickListener) {
        this.context = context;
        this.pasienList = pasienList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_pasien, parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {

        Pasien pasien = pasienList.get(position);
        holder.textViewIDPasien.setText(pasien.getId());
        holder.textViewNamaPasien.setText(pasien.getNama());

        String textNomorRekamMedis = "Nomor Rekam Medis : " + pasien.getNo_rekammedis();
        holder.textViewNomorRekamMedis.setText(textNomorRekamMedis);
    }

    @Override
    public int getItemCount() {
        return pasienList.size();
    }

    static class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewIDPasien, textViewNamaPasien, textViewNomorRekamMedis;
        MaterialCardView materialCardView;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            textViewIDPasien = itemView.findViewById(R.id.id_pasien);
            textViewNamaPasien = itemView.findViewById(R.id.nama_pasien);
            textViewNomorRekamMedis = itemView.findViewById(R.id.nomor_rekammedis);
            materialCardView = itemView.findViewById(R.id.card_daftar_pasien);

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
