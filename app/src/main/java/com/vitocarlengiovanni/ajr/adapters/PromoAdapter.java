package com.vitocarlengiovanni.ajr.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.vitocarlengiovanni.ajr.R;
import com.vitocarlengiovanni.ajr.models.Promo;

import java.util.ArrayList;
import java.util.List;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.ViewHolder> implements Filterable {

    private List<Promo> promoList, filteredPromoList;
    private Context context;

    public PromoAdapter(List<Promo> promoList, Context context) {
        this.promoList = promoList;
        filteredPromoList = new ArrayList<>(promoList);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_promo, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Promo promo = filteredPromoList.get(position);

        holder.tvKodePromo.setText(promo.getKode_promo());
        holder.tvJenisPromo.setText(promo.getJenis_promo());
        holder.tvKeteranganPromo.setText(promo.getKeterangan_promo());
    }

    @Override
    public int getItemCount() {
        return filteredPromoList.size();
    }


    public void setPromoList(List<Promo> promoList) {
        this.promoList = promoList;
        filteredPromoList = new ArrayList<>(promoList);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<Promo> filtered = new ArrayList<>();

                if (charSequenceString.isEmpty()) {
                    filtered.addAll(promoList);
                } else {
                    for (Promo promo : promoList) {
                        if (promo.getKode_promo().toLowerCase()
                                .contains(charSequenceString.toLowerCase()))
                            filtered.add(promo);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredPromoList.clear();
                filteredPromoList.addAll((List<Promo>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvKodePromo, tvJenisPromo, tvKeteranganPromo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvKodePromo = itemView.findViewById(R.id.tv_kode_promo);
            tvJenisPromo = itemView.findViewById(R.id.tv_jenis_promo);
            tvKeteranganPromo = itemView.findViewById(R.id.tv_keterangan_promo);
        }
    }
}
