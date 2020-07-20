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
import com.antiklu.aplikasi.adapter.RestoListAdapter;
import com.antiklu.aplikasi.model.RestoListModel;
import com.antiklu.aplikasi.settings.Client;
import com.antiklu.aplikasi.settings.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.antiklu.aplikasi.SpawnActivity.my_shared_preferences;

public class RestoListFragment extends Fragment {

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String fuid,type,aidi,category_title;
    RecyclerView slider;
    TextView title_tv;

    private RestoListAdapter adapter;
    private ArrayList<RestoListModel> mahasiswaArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restolist, container, false);

        Bundle bundle = this.getArguments();

        if(bundle != null){
            //Toast.makeText(getActivity(),"Aidi : " + String.valueOf(bundle.getInt("aidi")),Toast.LENGTH_SHORT).show();
            type = bundle.getString("type");
            aidi = bundle.getString("aidi");

            if (type.equals("") || aidi.equals("")) {
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
        category_title = "Daftar Restoran";


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
                        .replace(R.id.fl_container, fragment2,"home")
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
        String url = Server.URL + "getrestolist.php?id=" + Client.bangsatkau(fuid) + "&type=" + Client.bangsatkau(type) + "&cid=" + Client.bangsatkau(aidi);
        Log.e("OrderFrag",url);

        StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("RankFrag", "Login Response: " + response.toString());

                try {
                    JSONObject jObjx = new JSONObject(response);
                    int success = jObjx.getInt("success");
                    String message = jObjx.getString("message");
                    String list = "";
                    try {
                        list = jObjx.getString("list");
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "Tidak ada Restoran", Toast.LENGTH_LONG).show();
                        doneLoading();
                    }
                    if (success == 1 && !list.equals("")) {
                        JSONArray jObj = new JSONArray(list);
                        for (int i = 0; i < jObj.length(); i++) {
                            JSONObject row = jObj.getJSONObject(i);
                            String aidi = row.getString("id");
                            String name = row.getString("name");
                            String desc = row.getString("desc");
                            String range = row.getString("range");
                            String gambar = row.getString("gambar");
                            Log.e("RankFrag", aidi + name + desc + range + gambar);
                            mahasiswaArrayList.add(new RestoListModel(aidi,name,desc,range,gambar));
                        }
                        adapter = new RestoListAdapter(mahasiswaArrayList, getActivity());
                        slider.setAdapter(adapter);

                        category_title = jObjx.getString("name");
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
                Log.e("RankFrag", "Gagal Mendapatkan Daftar Resto : " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Gagal Mendapatkan Daftar Resto" + error.getMessage(), Toast.LENGTH_LONG).show();

                // hideDialog();

            }
        });

        // Adding request to request queue
        queue.add(strReq);

    }

    void doneLoading() {
       title_tv.setText(category_title);
    }

    }