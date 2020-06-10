package com.antiklu.aplikasi.prakmen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.antiklu.aplikasi.adapter.OrderAdapter;
import com.antiklu.aplikasi.model.OrderModel;
import com.antiklu.aplikasi.settings.Client;
import com.antiklu.aplikasi.settings.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.antiklu.aplikasi.SpawnActivity.my_shared_preferences;

public class OrderFragment extends Fragment {

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String fuid,type;
    RecyclerView slider;
    TextView title_tv;

    private OrderAdapter adapter;
    private ArrayList<OrderModel> mahasiswaArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restolist, container, false);

        Bundle bundle = this.getArguments();

        if(bundle != null){
            //Toast.makeText(getActivity(),"Aidi : " + String.valueOf(bundle.getInt("aidi")),Toast.LENGTH_SHORT).show();
            type = bundle.getString("type");

            if (type == "") {
                Toast.makeText(getActivity(),"Error . -2.2",Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(getActivity(),"Error . -2",Toast.LENGTH_LONG).show();
        }
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        slider = getView().findViewById(R.id.bottomrv);


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

        ImageView backImage = getView().findViewById(R.id.backImage);

        backImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                HomeFragment fragment2 = new HomeFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, fragment2,"backtoaccount")
                        .commit();
            }


        });


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
        String url = Server.URL + "getorderlist.php?id=" + Client.bangsatkau(fuid) + "&type=" + Client.bangsatkau(type) ;
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
                            String desc = row.getString("desc");
                            String time = row.getString("time");
                            String status = row.getString("status");
                            Log.e("RankFrag", aidi + name + desc + time + status);
                            mahasiswaArrayList.add(new OrderModel(aidi,name,desc,time,status));
                        }
                        adapter = new OrderAdapter(mahasiswaArrayList, getActivity());
                        slider.setAdapter(adapter);
//                        content_ll.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);

                        //share_btn.setVisibility(View.VISIBLE);
                        // Check for error node in json

                        // Log.e("Successfully Login!", jObj.toString());

        if (type == "micro") {
            title_tv.setText("Order Aktif");
        } else if (type == "c") {
            title_tv.setText("Sejarah Order");
        }

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
                Log.e("RankFrag", "Gagal Mendapatkan Daftar Order : " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Gagal Mendapatkan Daftar Order" + error.getMessage(), Toast.LENGTH_LONG).show();

                // hideDialog();

            }
        });

        // Adding request to request queue
        queue.add(strReq);

    }
}
