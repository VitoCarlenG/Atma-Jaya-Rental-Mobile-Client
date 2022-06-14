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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.vitocarlengiovanni.ajr.BrosurActivity;
import com.vitocarlengiovanni.ajr.R;
import com.vitocarlengiovanni.ajr.ViewBrosurActivity;
import com.vitocarlengiovanni.ajr.models.Brosur;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BrosurAdapter extends RecyclerView.Adapter<BrosurAdapter.ViewHolder> implements Filterable {

    private List<Brosur> brosurList, filteredBrosurList;
    private Context context;

    public BrosurAdapter(List<Brosur> brosurList, Context context) {
        this.brosurList = brosurList;
        filteredBrosurList = new ArrayList<>(brosurList);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_brosur, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Brosur brosur = filteredBrosurList.get(position);

        holder.tvNama.setText(brosur.getNama_mobil());

        DecimalFormat rupiahFormat = (DecimalFormat) DecimalFormat
                .getCurrencyInstance(new Locale("in", "ID"));
        holder.tvHarga.setText(rupiahFormat.format(brosur.getHarga_sewa_mobil()));

        Glide.with(context)
                .load(brosur.getGambar_mobil())
                .placeholder(R.drawable.no_image)
                .into(holder.ivGambar);

        holder.cvBrosur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ViewBrosurActivity.class);
                i.putExtra("id_mobil", brosur.getId_mobil());

                if (context instanceof BrosurActivity)
                    ((BrosurActivity) context).startActivityForResult(i,
                            BrosurActivity.LAUNCH_ADD_ACTIVITY);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredBrosurList.size();
    }


    public void setBrosurList(List<Brosur> brosurList) {
        this.brosurList = brosurList;
        filteredBrosurList = new ArrayList<>(brosurList);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<Brosur> filtered = new ArrayList<>();

                if (charSequenceString.isEmpty()) {
                    filtered.addAll(brosurList);
                } else {
                    for (Brosur brosur : brosurList) {
                        if (brosur.getNama_mobil().toLowerCase()
                                .contains(charSequenceString.toLowerCase()))
                            filtered.add(brosur);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredBrosurList.clear();
                filteredBrosurList.addAll((List<Brosur>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNama, tvHarga;
        ImageView ivGambar;
        CardView cvBrosur;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tv_nama);
            tvHarga = itemView.findViewById(R.id.tv_harga);
            ivGambar = itemView.findViewById(R.id.iv_gambar);
            cvBrosur = itemView.findViewById(R.id.cv_brosur);
        }
    }
}
