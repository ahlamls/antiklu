package com.antiklu.aplikasi.prakmen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.antiklu.aplikasi.R;
import com.antiklu.aplikasi.SpawnActivity;
import com.antiklu.aplikasi.adapter.OrderMenuAdapter;
import com.antiklu.aplikasi.model.OrderMenuModel;
import com.antiklu.aplikasi.settings.Client;
import com.antiklu.aplikasi.settings.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.view.View.GONE;
import static com.antiklu.aplikasi.SpawnActivity.my_shared_preferences;

public class OrderStatusFragment extends Fragment {

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String fuid,aidi;
    RecyclerView slider;
    TextView title_tv;

    private OrderMenuAdapter adapter;
    private ArrayList<OrderMenuModel> mahasiswaArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orderstatus, container, false);

        Bundle bundle = this.getArguments();

        if(bundle != null){
            //Toast.makeText(getActivity(),"Aidi : " + String.valueOf(bundle.getInt("aidi")),Toast.LENGTH_SHORT).show();
            aidi = bundle.getString("aidi");

            if (aidi == "") {
                Toast.makeText(getActivity(),"Error . -2.2",Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(getActivity(),"Error . -2",Toast.LENGTH_LONG).show();
        }
        return view;

    }

    TextView textView69,textView26,textView21,textView23,textView23x,textView25,textView6969;
    Button cancel_btn;
    LinearLayout ll;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        sharedpreferences = this.getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean("login", false);
        fuid = sharedpreferences.getString("fuid",null);
        if (!session) {
            Intent intent = new Intent(getActivity(), SpawnActivity.class);
            this.getActivity().finish();
            startActivity(intent);
        }

        title_tv = getView().findViewById(R.id.textView5);
        title_tv.setText("Loading...");

        textView69 = getView().findViewById(R.id.textView69);
        textView21 = getView().findViewById(R.id.textView21);
        textView23 = getView().findViewById(R.id.textView23);
        textView23x = getView().findViewById(R.id.textView23x);
        textView25 = getView().findViewById(R.id.textView25);
        textView26 = getView().findViewById(R.id.textView26);
        textView6969 = getView().findViewById(R.id.textView6969);

        slider = getView().findViewById(R.id.rv);

        cancel_btn = getView().findViewById(R.id.cancel_btn);


        ImageView backImage = getView().findViewById(R.id.backImage);

        backImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                AccountFragment fragment2 = new AccountFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, fragment2,"backtohome")
                        .commit();
            }


        });

        ll = getView().findViewById(R.id.ll);

        ll.setVisibility(GONE);
        addDatax();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        slider.setLayoutManager(layoutManager);


    }

    void addDatax() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        mahasiswaArrayList = new ArrayList<>();
      /*  recyclerViewx.setVisibility(View.VISIBLE);
        progressBarsurvey.setVisibility(View.GONE);

        mahasiswaArrayList.add(new DaftarSurvey("Survey Tentang Asede", "100 MeterPoin", "1"));
        mahasiswaArrayList.add(new DaftarSurvey("Survey Tentang Kontol", "120 MeterPoin", "2"));
        mahasiswaArrayList.add(new DaftarSurvey("Survey Tentang Samalo", "130 MeterPoin", "3"));
        adapter = new SurveyAdapter(mahasiswaArrayList);
        recyclerViewx.setAdapter(adapter);
*/
        String url = Server.URL + "getorder.php?id=" + Client.bangsatkau(fuid) + "&oid=" + Client.bangsatkau(aidi) ;
        Log.e("OrderFrag",url);

        StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("RankFrag", "Login Response: " + response.toString());

                try {
                    JSONObject jObjx = new JSONObject(response);
                    int success = jObjx.getInt("success");
                    String message = jObjx.getString("message");
                    String list = jObjx.getString("list");

                    if (success == 1) {
                        JSONArray jObj = new JSONArray(list);
                        for (int i = 0; i < jObj.length(); i++) {
                            JSONObject row = jObj.getJSONObject(i);
                            String aidi = row.getString("id");
                            String name = row.getString("name");
                            String info = row.getString("info");
                            int count = row.getInt("count");
                            long price = row.getLong("price");
                            Log.e("RankFrag", aidi + name + info + count + price);
                            mahasiswaArrayList.add(new OrderMenuModel(aidi,name,info,count,price));
                        }

                        String id = jObjx.getString("id");
                        String waktu = jObjx.getString("waktu");
                        String driver = jObjx.getString("driver");
                        String status = jObjx.getString("status");
                        String alamat = jObjx.getString("alamat");
                        String jarak = jObjx.getString("jarak");
                        String ongkir  =jObjx.getString("ongkir");
                        String price = jObjx.getString("price");
                        int cancelable = jObjx.getInt("cancelable");
                        if (cancelable == 1) {
                            cancel_btn.setVisibility(View.VISIBLE);
                        } else {
                            cancel_btn.setVisibility(View.GONE);
                        }
                        cancel_btn.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View v) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                builder1.setMessage("Apakah kamu yakin akan membatalkan order ini?");
                                builder1.setCancelable(true);
                                builder1.setTitle("Konfirmasi Cancel");
                                builder1.setPositiveButton(
                                        "Ya",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Kensel();
                                                dialog.cancel();
                                            }
                                        });

                                builder1.setNegativeButton(
                                        "Tidak",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();


                            }
                        });
                        ///////////////////////
                        textView69.setText("Waktu : " + waktu);
                        textView26.setText("Nama Driver : " + driver);
                        textView21.setText(status);
                        textView23.setText(alamat);
                        textView23x.setText("Jarak : " + jarak + " km");
                        textView25.setText("Ongkir : Rp " +  ongkir);
                        textView6969.setText("Rp " +  price);

                        //risaykel piyu

                        adapter = new OrderMenuAdapter(mahasiswaArrayList, getActivity());
                        slider.setAdapter(adapter);
//                        content_ll.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);

                        //share_btn.setVisibility(View.VISIBLE);
                        // Check for error node in json

                        // Log.e("Successfully Login!", jObj.toString());


                       doneLoading();

                        /*Toast.makeText(getActivity(),
                                jObj.getString("ReffFrag"), Toast.LENGTH_LONG).show();*/
                    } else {
//                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),  message , Toast.LENGTH_LONG).show();
                    }

                } catch(JSONException e){
                    // JSON error

                    Toast.makeText(getActivity(), "Gagal Parsing " + e.toString(), Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RankFrag", "Gagal Mendapatkan Info Order : " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Gagal Mendapatkan Info Order" + error.getMessage(), Toast.LENGTH_LONG).show();

                // hideDialog();

            }
        });

        // Adding request to request queue
        queue.add(strReq);

    }

    void doneLoading() {
        title_tv.setText("Detail Order #" + aidi);
        ll.setVisibility(View.VISIBLE);
    }

    void Kensel() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Server.URL + "cancelorder.php?id=" + Client.bangsatkau(fuid) + "&oid=" + Client.bangsatkau(aidi);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //if banned/apdet
                        // showUpdateDialog(message,url);
                        //if gagal

                        try {
                            JSONObject jObj = new JSONObject(response);
                            int success = jObj.getInt("success");

                            String message = jObj.getString("message");

                            // Display the first 500 characters of the response string.

                            if (success == 1) {
                                Toast.makeText(getContext(),"Sukses Membatalkan Order",Toast.LENGTH_LONG).show();
                                AccountFragment fragment2 = new AccountFragment();
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fl_container, fragment2,"backtohome")
                                        .commit();

                            } else {
                                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {

                            Toast.makeText(getContext(),"Kesalahan Parsing . " + e.toString() ,Toast.LENGTH_LONG).show();

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Gagal Melakukan Cancel " + error.toString() ,Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}