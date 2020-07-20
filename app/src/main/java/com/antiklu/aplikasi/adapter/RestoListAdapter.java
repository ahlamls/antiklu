package com.antiklu.aplikasi.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.antiklu.aplikasi.R;
import com.antiklu.aplikasi.model.RestoListModel;
import com.antiklu.aplikasi.prakmen.RestoFragment;
import com.antiklu.aplikasi.settings.Server;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestoListAdapter extends RecyclerView.Adapter<RestoListAdapter.DaftarViewHolder> {


    private ArrayList<RestoListModel> dataList;
    Context mContext;
    public RestoListAdapter(ArrayList<RestoListModel> dataList,Context context) {
        this.dataList = dataList;
        this.mContext= context;
    }


    @Override
    public DaftarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_restolist, parent, false);
        return new DaftarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DaftarViewHolder holder, int position) {
        holder.name_tv.setText(dataList.get(position).getName());
        holder.desc_tv.setText(dataList.get(position).getDesc());
        holder.range_tv.setText(dataList.get(position).getRange());
        Picasso.get().load(Server.DATA_URL + "resto/" + dataList.get(position).getGambar()).into(holder.gambar_iv);


        final String aidi = dataList.get(position).getId();
        //HANDLE ONCLICK
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putString("aidi",aidi); // Put anything what you want

                RestoFragment fragment2 = new RestoFragment();
                fragment2.setArguments(bundle);

                ((AppCompatActivity) mContext).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, fragment2,"backtohome")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class DaftarViewHolder extends RecyclerView.ViewHolder{
        private TextView name_tv,desc_tv,range_tv;
        ImageView gambar_iv;
        public DaftarViewHolder(View orderView) {
            super(orderView);
            name_tv = orderView.findViewById(R.id.textView12);
            desc_tv = orderView.findViewById(R.id.textView13);
            range_tv = orderView.findViewById(R.id.textView14);
            gambar_iv = orderView.findViewById(R.id.imageView8);
        }
    }
}