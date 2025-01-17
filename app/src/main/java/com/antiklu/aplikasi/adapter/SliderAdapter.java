package com.antiklu.aplikasi.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.antiklu.aplikasi.R;
import com.antiklu.aplikasi.model.SliderModel;
import com.antiklu.aplikasi.prakmen.RestoListFragment;
import com.antiklu.aplikasi.settings.Server;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.DaftarViewHolder> {


    private ArrayList<SliderModel> dataList;
    Context mContext;
    public SliderAdapter(ArrayList<SliderModel> dataList,Context context) {
        this.dataList = dataList;
        this.mContext= context;
    }


    @Override
    public DaftarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_carousel, parent, false);
        return new DaftarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DaftarViewHolder holder, int position) {

        Picasso.get().load(Server.DATA_URL + "carousel/" + dataList.get(position).getGambar()).into(holder.gambar_iv);
        final String aidi = dataList.get(position).getId();
        //HANDLE ONCLICK


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("type","c");
                bundle.putString("aidi",aidi); // Put anything what you want

                RestoListFragment fragment2 = new RestoListFragment();
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
        private ImageView gambar_iv;

        public DaftarViewHolder(View sliderView) {
            super(sliderView);

            gambar_iv = (ImageView) sliderView.findViewById(R.id.imageView12);


        }
    }
}