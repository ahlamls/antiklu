package com.antiklu.aplikasi.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.antiklu.aplikasi.R;
import com.antiklu.aplikasi.model.MenuModel;
import com.antiklu.aplikasi.settings.Server;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.DaftarViewHolder> {


    private ArrayList<MenuModel> dataList;
    Context mContext;
    public MenuAdapter(ArrayList<MenuModel> dataList, Context context,LayoutInflater inflater) {
        this.dataList = dataList;
        this.mContext= context;
        this.mInflater = inflater;
    }



    @Override
    public DaftarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_menulist, parent, false);
        return new DaftarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DaftarViewHolder holder, int position) {
        holder.name_tv.setText(dataList.get(position).getName());
        final String name = dataList.get(position).getName();
        holder.desc_tv.setText(dataList.get(position).getDesc());
        long promo = dataList.get(position).getPromoprice();
        long price = dataList.get(position).getPrice();
        if (promo == 0L) {
            holder.promo_tv.setText("Rp " + String.valueOf(price));
            holder.price_tv.setVisibility(View.GONE);
        } else {
            holder.promo_tv.setText("Rp " + String.valueOf(promo));
            holder.price_tv.setText("Rp " + String.valueOf(price));
        }
        Picasso.get().load(Server.DATA_URL + "restoitem/" + dataList.get(position).getGambar()).into(holder.gambar_iv);


        final String aidi = dataList.get(position).getId();
        //HANDLE ONCLICK
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog = new AlertDialog.Builder(mContext);
                inflater = mInflater;
                dialogView = inflater.inflate(R.layout.form_menulist, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
                dialog.setTitle(name);

//                txt_nama    = (EditText) dialogView.findViewById(R.id.txt_nama);
//                txt_usia    = (EditText) dialogView.findViewById(R.id.txt_usia);
//                txt_alamat  = (EditText) dialogView.findViewById(R.id.txt_alamat);
//                txt_status = (EditText) dialogView.findViewById(R.id.txt_status);


                dialog.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    AlertDialog.Builder dialog;
    LayoutInflater inflater,mInflater;
    View dialogView;
    EditText txt_nama, txt_usia, txt_alamat, txt_status;
    TextView txt_hasil;
    String nama, usia, alamat, status;

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class DaftarViewHolder extends RecyclerView.ViewHolder{
        private TextView name_tv,desc_tv,price_tv,promo_tv;
        ImageView gambar_iv;
        public DaftarViewHolder(View orderView) {
            super(orderView);
            name_tv = orderView.findViewById(R.id.textView12);
            desc_tv = orderView.findViewById(R.id.textView17);
            price_tv = orderView.findViewById(R.id.original_price);
            promo_tv = orderView.findViewById(R.id.promo_tv);
            gambar_iv = orderView.findViewById(R.id.imageView8);
        }
    }
}