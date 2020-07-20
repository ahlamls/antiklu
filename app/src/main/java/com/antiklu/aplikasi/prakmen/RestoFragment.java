package com.antiklu.aplikasi.prakmen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
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
import com.antiklu.aplikasi.adapter.MenuAdapter;
import com.antiklu.aplikasi.model.MenuModel;
import com.antiklu.aplikasi.settings.Client;
import com.antiklu.aplikasi.settings.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.antiklu.aplikasi.SpawnActivity.my_shared_preferences;

public class RestoFragment extends Fragment {

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String fuid,aidi;
    RecyclerView slider;
    TextView title_tv;

    private MenuAdapter adapter;
    private ArrayList<MenuModel> mahasiswaArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resto, container, false);

        Bundle bundle = this.getArguments();

        if(bundle != null){
            //Toast.makeText(getActivity(),"Aidi : " + String.valueOf(bundle.getInt("aidi")),Toast.LENGTH_SHORT).show();
            aidi = bundle.getString("aidi");

            if (aidi.equals("")) {
                Toast.makeText(getActivity(),"Error . -2.2",Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(getActivity(),"Error . -2",Toast.LENGTH_LONG).show();
        }
        return view;

    }

    TextView textView5,restodesc,desc;
    float longitude,latitude;
    ImageView imageView10,imageView6;
    NestedScrollView nsv;
    Button button2;
    ProgressBar progressBar;
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

        progressBar = getView().findViewById(R.id.progressBar);
        restodesc = getView().findViewById(R.id.restodesc);
        restodesc.setText("");
        desc = getView().findViewById(R.id.desc);


        slider = getView().findViewById(R.id.menurv);

        button2 = getView().findViewById(R.id.button2);
        button2.setVisibility(View.GONE);


        ImageView backImage = getView().findViewById(R.id.backImage);

        backImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Bundle bundle = new Bundle();


                HomeFragment fragment2 = new HomeFragment();
                fragment2.setArguments(bundle);

                ((AppCompatActivity) getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, fragment2,"home")
                        .commit();
            }


        });
        imageView6 = getView().findViewById(R.id.imageView6);
        imageView10 = getView().findViewById(R.id.imageView10);


        nsv = getView().findViewById(R.id.nsv);

        nsv.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);
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
        String url = Server.URL + "getmenulist.php?id=" + Client.bangsatkau(fuid) + "&rid=" + Client.bangsatkau(aidi) ;
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
                            String info = row.getString("desc");
                            String gambar = row.getString("gambar");
                            long promoprice = row.getLong("promoprice");
                            long price = row.getLong("price");
                            Log.e("RankFrag", aidi + name + info + gambar + price + promoprice);
                            mahasiswaArrayList.add(new MenuModel(aidi,name,info,gambar,price,promoprice));
                        }

                        String name= jObjx.getString("name");
                        String deskripsi = jObjx.getString("desc");
                        String gambar = jObjx.getString("gambar");
                        longitude = jObjx.getLong("longitude");
                        latitude = jObjx.getLong("latitude");
                        float jarak = jObjx.getInt("jarak");
                        long ongkir = jObjx.getLong("ongkir");

                        ///////////////////////
                       title_tv.setText(name);
                       restodesc.setText("Jarak : "+jarak + "km | Estimisasi Ongkir Rp " + ongkir);
                       desc.setText(deskripsi);
                        Picasso.get().load(Server.DATA_URL + "resto/" + gambar).into(imageView6);
                        imageView10.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View view){
                                Uri gmmIntentUri = Uri.parse("geo:" +  String.valueOf(latitude) +"," + String.valueOf(longitude));
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
                                    startActivity(mapIntent);
                                } else {
                                    Toast.makeText(getContext(),"Gagal Membuka Maps . pastikan Google Maps terpasang di hp anda",Toast.LENGTH_SHORT).show();
                                }
                            }


                        });
                        //risaykel piyu

                        adapter = new MenuAdapter(mahasiswaArrayList, getActivity(),getLayoutInflater());
                        slider.setAdapter(adapter);
//                        content_ll.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        nsv.setVisibility(VISIBLE);


                        //share_btn.setVisibility(View.VISIBLE);
                        // Check for error node in json

                        // Log.e("Successfully Login!", jObj.toString());


                        postLoading();

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

    void postLoading() {
//load tombol
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