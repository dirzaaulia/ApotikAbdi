package com.example.apotikabdi.fragment.antrian;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apotikabdi.R;
import com.example.apotikabdi.model.Antrian;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AntrianAdapter extends RecyclerView.Adapter<AntrianAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Antrian> antrianList;
    private ItemClickListener itemClickListener;

    AntrianAdapter(Context context, List<Antrian> antrianList, ItemClickListener itemClickListener) {
        this.context = context;
        this.antrianList = antrianList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_antrian, parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {

        Antrian antrian = antrianList.get(position);

        String keluhan = "Keluhan : " + antrian.getKeluhan();
        String status = "Status : " + antrian.getStatus();

        holder.textViewIDAntrian.setText(antrian.getId());
        holder.textViewNamaPasien.setText(antrian.getNama_pasien());
        holder.textViewKeluhan.setText(keluhan);
        holder.textViewStatus.setText(status);
    }

    @Override
    public int getItemCount() {
        return antrianList.size();
    }

    static class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewIDAntrian, textViewNamaPasien, textViewKeluhan, textViewStatus;
        MaterialCardView materialCardView;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            textViewIDAntrian = itemView.findViewById(R.id.id_antrian);
            textViewNamaPasien = itemView.findViewById(R.id.nama_pasien_antrian);
            textViewKeluhan = itemView.findViewById(R.id.keluhan);
            textViewStatus = itemView.findViewById(R.id.status_antrian);
            materialCardView = itemView.findViewById(R.id.card_daftar_antrian);

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
