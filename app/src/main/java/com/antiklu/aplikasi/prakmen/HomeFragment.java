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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.antiklu.aplikasi.adapter.CategoryAdapter;
import com.antiklu.aplikasi.adapter.SliderAdapter;
import com.antiklu.aplikasi.model.CategoryModel;
import com.antiklu.aplikasi.model.SliderModel;
import com.antiklu.aplikasi.settings.Client;
import com.antiklu.aplikasi.settings.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.antiklu.aplikasi.SpawnActivity.my_shared_preferences;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout content_ll;

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String fuid;
    private SliderAdapter adapter;
    private ArrayList<SliderModel> mahasiswaArrayList;

    private CategoryAdapter adapterx;
    private ArrayList<CategoryModel> mahasiswaArrayListx;
    RecyclerView slider,categories;

    ImageView news_btn,account_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        slider = getView().findViewById(R.id.slider_rv);
        categories = getView().findViewById(R.id.category_rv);

        sharedpreferences = this.getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean("login", false);
        fuid = sharedpreferences.getString("fuid",null);
        if (!session) {
            Intent intent = new Intent(getActivity(), SpawnActivity.class);
            this.getActivity().finish();
            startActivity(intent);
        }

        addDatax();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        slider.setLayoutManager(layoutManager);
        addDataxz();
        RecyclerView.LayoutManager layoutManagerx = new GridLayoutManager(getActivity(),3,RecyclerView.VERTICAL,false);
        categories.setLayoutManager(layoutManagerx);

        news_btn = getView().findViewById(R.id.imageView5);
        news_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                NewsFragment fragment2 = new NewsFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, fragment2,"account")
                        .commit();
            }


        });

        account_btn = getView().findViewById(R.id.imageView4);
        account_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                AccountFragment fragment2 = new AccountFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, fragment2,"account")
                        .commit();
            }


        });

    }
    void addDatax(){
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
        String url = Server.URL + "carousel.php?id=" + Client.bangsatkau(fuid) ;

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
                            String gambar = row.getString("gambar");
                            Log.e("RankFrag", aidi + gambar);
                            mahasiswaArrayList.add(new SliderModel(aidi,gambar));
                        }
                        adapter = new SliderAdapter(mahasiswaArrayList, getActivity());
                        slider.setAdapter(adapter);
//                        content_ll.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);

                        //share_btn.setVisibility(View.VISIBLE);
                        // Check for error node in json

                        // Log.e("Successfully Login!", jObj.toString());



                        /*Toast.makeText(getActivity(),
                                jObj.getString("ReffFrag"), Toast.LENGTH_LONG).show();*/
                    } else {
//                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),  message , Toast.LENGTH_LONG).show();
                    }

                } catch(JSONException e){
                    // JSON error
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Gagal Parsing " + e.toString(), Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.e("RankFrag", "Gagal Mendapatkan Daftar Gambar : " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Gagal Mendapatkan Daftar Gambar" + error.getMessage(), Toast.LENGTH_LONG).show();

                // hideDialog();

            }
        });

        // Adding request to request queue
        queue.add(strReq);


    }

    void addDataxz(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        mahasiswaArrayListx = new ArrayList<>();
      /*  recyclerViewx.setVisibility(View.VISIBLE);
        progressBarsurvey.setVisibility(View.GONE);

        mahasiswaArrayList.add(new DaftarSurvey("Survey Tentang Asede", "100 MeterPoin", "1"));
        mahasiswaArrayList.add(new DaftarSurvey("Survey Tentang Kontol", "120 MeterPoin", "2"));
        mahasiswaArrayList.add(new DaftarSurvey("Survey Tentang Samalo", "130 MeterPoin", "3"));
        adapter = new SurveyAdapter(mahasiswaArrayList);
        recyclerViewx.setAdapter(adapter);
*/
        String url = Server.URL + "category.php?id=" + Client.bangsatkau(fuid) ;

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
                            String text = row.getString("text");
                            Log.e("RankFrag", aidi + text);
                            mahasiswaArrayListx.add(new CategoryModel(aidi,text));
                        }
                        adapterx = new CategoryAdapter(mahasiswaArrayListx, getActivity());
                        categories.setAdapter(adapterx);
//                        content_ll.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);

                        //share_btn.setVisibility(View.VISIBLE);
                        // Check for error node in json

                        // Log.e("Successfully Login!", jObj.toString());



                        /*Toast.makeText(getActivity(),
                                jObj.getString("ReffFrag"), Toast.LENGTH_LONG).show();*/
                    } else {
//                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),  message , Toast.LENGTH_LONG).show();
                    }

                } catch(JSONException e){
                    // JSON error
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Gagal Parsing " + e.toString(), Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.e("RankFrag", "Gagal Mendapatkan Daftar Kategori : " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Gagal Mendapatkan Daftar Kategori" + error.getMessage(), Toast.LENGTH_LONG).show();

                // hideDialog();

            }
        });

        // Adding request to request queue
        queue.add(strReq);


    }

}
