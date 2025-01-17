package com.antiklu.aplikasi.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.antiklu.aplikasi.R;
import com.antiklu.aplikasi.model.CategoryModel;
import com.antiklu.aplikasi.prakmen.RestoListFragment;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.DaftarViewHolder> {


    private ArrayList<CategoryModel> dataList;
    Context mContext;
    public CategoryAdapter(ArrayList<CategoryModel> dataList,Context context) {
        this.dataList = dataList;
        this.mContext= context;
    }


    @Override
    public DaftarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_kategorilist, parent, false);
        return new DaftarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DaftarViewHolder holder, int position) {
        holder.text_tv.setText(dataList.get(position).getText());
        final String aidi = dataList.get(position).getId();
        //HANDLE ONCLICK


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("type","micro");
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
        private TextView text_tv;

        public DaftarViewHolder(View itemView) {
            super(itemView);
            text_tv = (TextView) itemView.findViewById(R.id.textView26);


        }
    }
}