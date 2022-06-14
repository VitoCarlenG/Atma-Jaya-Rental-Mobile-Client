package com.vitocarlengiovanni.ajr.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vitocarlengiovanni.ajr.R;
import com.vitocarlengiovanni.ajr.RiwayatTransaksiDriverActivity;
import com.vitocarlengiovanni.ajr.models.Transaksi;
import com.vitocarlengiovanni.ajr.models.TransaksiResponse;

import java.util.ArrayList;
import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder> implements Filterable {

    private List<Transaksi> transaksiList, filteredTransaksiList;
    private Context context;

    public TransaksiAdapter(List<Transaksi> transaksiList, Context context) {
        this.transaksiList = transaksiList;
        filteredTransaksiList = new ArrayList<>(transaksiList);
        this.context = context;
    }

    @NonNull
    @Override
    public TransaksiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_transaksi, parent, false);

        return new TransaksiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiAdapter.ViewHolder holder, int position) {
        Transaksi transaksi = filteredTransaksiList.get(position);

        holder.tvNomorTransaksi.setText(transaksi.getFormat_nomor_transaksi()+String.valueOf(transaksi.getNomor_transaksi()));
        holder.tvTanggalTransaksi.setText(transaksi.getTanggal_transaksi());
        holder.tvNamaCustomer.setText("CUST: "+transaksi.getNama_customer());
        holder.tvNamaMobil.setText("CAR: "+transaksi.getNama_mobil());
        holder.tvNamaPegawai.setText("CS: "+transaksi.getNama_pegawai());

        if(transaksi.getKode_promo()==null) {
            holder.tvKodePromo.setText("PRO: -");
        }else {
            holder.tvKodePromo.setText("PRO: "+transaksi.getKode_promo());
        }

        if(transaksi.getNama_driver()==null) {
            holder.tvNamaDriver.setText("DRV: -");
        }else {
            holder.tvNamaDriver.setText("DRV: "+transaksi.getNama_driver());
        }

        holder.tvStatusTransaksi.setText(transaksi.getStatus_transaksi());
    }

    @Override
    public int getItemCount() {
        return filteredTransaksiList.size();
    }


    public void setTransaksiList(List<Transaksi> transaksiList) {
        this.transaksiList = transaksiList;
        filteredTransaksiList = new ArrayList<>(transaksiList);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<Transaksi> filtered = new ArrayList<>();

                if (charSequenceString.isEmpty()) {
                    filtered.addAll(transaksiList);
                } else {
                    for (Transaksi transaksi : transaksiList) {
                        if (transaksi.getFormat_nomor_transaksi().toLowerCase()
                                .contains(charSequenceString.toLowerCase()) || String.valueOf(transaksi.getNomor_transaksi()).toLowerCase()
                                .contains(charSequenceString.toLowerCase()))
                            filtered.add(transaksi);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredTransaksiList.clear();
                filteredTransaksiList.addAll((List<Transaksi>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomorTransaksi, tvTanggalTransaksi, tvNamaCustomer, tvNamaMobil, tvNamaPegawai, tvKodePromo, tvNamaDriver, tvStatusTransaksi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNomorTransaksi = itemView.findViewById(R.id.tv_nomor_transaksi);
            tvTanggalTransaksi = itemView.findViewById(R.id.tv_tanggal_transaksi);
            tvNamaCustomer = itemView.findViewById(R.id.tv_nama_customer);
            tvNamaMobil = itemView.findViewById(R.id.tv_nama_mobil);
            tvNamaPegawai = itemView.findViewById(R.id.tv_nama_pegawai);
            tvKodePromo = itemView.findViewById(R.id.tv_kode_promo);
            tvNamaDriver = itemView.findViewById(R.id.tv_nama_driver);
            tvStatusTransaksi = itemView.findViewById(R.id.tv_status_transaksi);
        }
    }

}
