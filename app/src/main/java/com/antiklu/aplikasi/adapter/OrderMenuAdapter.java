package com.antiklu.aplikasi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.antiklu.aplikasi.R;
import com.antiklu.aplikasi.model.OrderMenuModel;
import com.antiklu.aplikasi.settings.Client;

import java.util.ArrayList;

public class OrderMenuAdapter extends RecyclerView.Adapter<OrderMenuAdapter.DaftarViewHolder> {


    private ArrayList<OrderMenuModel> dataList;
    Context mContext;
    public OrderMenuAdapter(ArrayList<OrderMenuModel> dataList,Context context) {
        this.dataList = dataList;
        this.mContext= context;
    }


    @Override
    public DaftarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_menuorderlist, parent, false);
        return new DaftarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DaftarViewHolder holder, int position) {
        holder.name_tv.setText(dataList.get(position).getName());
        String descText = "Rp " + (Client.Pormat(dataList.get(position).getPrice())) + " x " + (String.valueOf(dataList.get(position).getCount()));
        holder.desc_tv.setText(descText);
        holder.info_tv.setText(dataList.get(position).getInfo());
        String finalText = "Rp " + (Client.Pormat(dataList.get(position).getPrice() * dataList.get(position).getCount()));
        holder.total_tv.setText(finalText);

        final String aidi = dataList.get(position).getId();
        //HANDLE ONCLICK

    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class DaftarViewHolder extends RecyclerView.ViewHolder{
        private TextView name_tv,info_tv,desc_tv,total_tv;
        public DaftarViewHolder(View orderView) {
            super(orderView);
            name_tv = orderView.findViewById(R.id.textView12);
            desc_tv = orderView.findViewById(R.id.textView13);
            info_tv = orderView.findViewById(R.id.textView13x);
            total_tv = orderView.findViewById(R.id.textView27);

        }
    }
}