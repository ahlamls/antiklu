package com.antiklu.aplikasi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.antiklu.aplikasi.R;
import com.antiklu.aplikasi.model.OrderModel;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.DaftarViewHolder> {


    private ArrayList<OrderModel> dataList;
    Context mContext;
    public OrderAdapter(ArrayList<OrderModel> dataList,Context context) {
        this.dataList = dataList;
        this.mContext= context;
    }


    @Override
    public DaftarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_orderlist, parent, false);
        return new DaftarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DaftarViewHolder holder, int position) {
        holder.title_tv.setText(dataList.get(position).getName());
        holder.desc_tv.setText(dataList.get(position).getDesc());
        holder.time_tv.setText(dataList.get(position).getTime());
        holder.status_tv.setText(dataList.get(position).getStatus());

        String aidi = dataList.get(position).getId();
        //HANDLE ONCLICK
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class DaftarViewHolder extends RecyclerView.ViewHolder{
private TextView title_tv,desc_tv,time_tv,status_tv;
        public DaftarViewHolder(View orderView) {
            super(orderView);
            title_tv = orderView.findViewById(R.id.textView12);
            desc_tv = orderView.findViewById(R.id.textView13);
            time_tv = orderView.findViewById(R.id.textView19);
            status_tv = orderView.findViewById(R.id.textView18);

        }
    }
}