package com.antiklu.aplikasi.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.antiklu.aplikasi.R;
import com.antiklu.aplikasi.model.AlamatModel;
import com.antiklu.aplikasi.prakmen.AlamatFragment;
import com.antiklu.aplikasi.settings.Client;
import com.antiklu.aplikasi.settings.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.antiklu.aplikasi.SpawnActivity.my_shared_preferences;

public class AlamatAdapter  extends RecyclerView.Adapter<AlamatAdapter.DaftarViewHolder>{

    FloatingActionButton fab;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_nama, txt_alamat;
    TextView txt_hasil;
    String nama, usia, alamat, status;
    AlamatFragment prakmen;

    private ArrayList<AlamatModel> dataList;
    Context mContext;
    public AlamatAdapter(ArrayList<AlamatModel> dataList, Context context, AlamatFragment prakmen) {
        this.dataList = dataList;
        this.mContext= context;
        this.prakmen = prakmen;
    }


    @Override
    public AlamatAdapter.DaftarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_alamatlist, parent, false);
        return new AlamatAdapter.DaftarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AlamatAdapter.DaftarViewHolder holder,final int position) {
        holder.title_tv.setText(dataList.get(position).getJudul());
        holder.desc_tv.setText(dataList.get(position).getAlamat());
        final String aidi = dataList.get(position).getId();

        //HANDLE ONCLICK

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( final View v) {
                // Do what you want to do atfer a long click here
                dialog = new AlertDialog.Builder(v.getContext());
                inflater = LayoutInflater.from(v.getContext());
                dialogView = inflater.inflate(R.layout.dialog_alamat, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
                dialog.setIcon(R.drawable.antikluapplogo);
                dialog.setTitle("Edit Alamat");



                txt_nama    = (EditText) dialogView.findViewById(R.id.nama_tv);
                txt_alamat  = (EditText) dialogView.findViewById(R.id.alamat_tv);

                txt_nama.setText(dataList.get(position).getJudul());
                txt_alamat.setText(dataList.get(position).getAlamat());


                dialog.setPositiveButton("Ubah Alamat", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nama    = txt_nama.getText().toString();
                        alamat  = txt_alamat.getText().toString();
                        editAlamat(aidi,nama,alamat,v);

                        dialog.dismiss();
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
    SharedPreferences sharedpreferences;
    void editAlamat(final String id, final String nama ,final  String alamat, final View view) {
        sharedpreferences = view.getContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        String fuid = sharedpreferences.getString("fuid",null);


        Log.e("RegActx",id + nama + alamat);

        RequestQueue queue = Volley.newRequestQueue(view.getContext());
        String url = Server.URL + "doeditalamat.php?id=" + Client.bangsatkau(fuid) + "&aid=" + id + "&nama=" + Client.bangsatkau(nama) + "&alamat=" + Client.bangsatkau(alamat);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.e("RegAct",response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            int success = jObj.getInt("success");
                            String message = jObj.getString("message");
                            // Display the first 500 characters of the response string.

                            if (success == 1) {
                                Toast.makeText(view.getContext(),
                                        "Ubah Alamat Sukses!", Toast.LENGTH_LONG).show();
                                prakmen.addDatax();

                            } else {
                                Toast.makeText(view.getContext(),
                                        message, Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            Log.e("RegAct",e.toString());
                            Toast.makeText(view.getContext(),
                                    "Kesalahan Parsing . " + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view.getContext(),
                        "Gagal Ubah Alamat" + error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class DaftarViewHolder extends RecyclerView.ViewHolder{
        private TextView title_tv,desc_tv,id_tv;
        public DaftarViewHolder(View alamatView) {
            super(alamatView);
            title_tv = alamatView.findViewById(R.id.judul);
            desc_tv = alamatView.findViewById(R.id.alamat);
            id_tv = alamatView.findViewById(R.id.id_tv);
        }
    }

}
