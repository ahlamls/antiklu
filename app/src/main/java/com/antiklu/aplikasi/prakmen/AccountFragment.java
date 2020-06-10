package com.antiklu.aplikasi.prakmen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.antiklu.aplikasi.R;
import com.antiklu.aplikasi.SpawnActivity;
import com.antiklu.aplikasi.settings.Client;
import com.antiklu.aplikasi.settings.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static com.antiklu.aplikasi.SpawnActivity.my_shared_preferences;

public class AccountFragment extends Fragment {

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String fuid;
    ImageView backImage;

    ImageView profil_iv;

    TextView nama_tv , email_tv , saldo_tv;
    Button topup_btn,alamat_btn,order_btn,history_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        return view;

    }

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


        profil_iv = getView().findViewById(R.id.imageView7);
        nama_tv = getView().findViewById(R.id.textView9);
        email_tv = getView().findViewById(R.id.textView10);
        saldo_tv = getView().findViewById(R.id.textView11);

        topup_btn = getView().findViewById(R.id.button3);
        alamat_btn = getView().findViewById(R.id.button4);
        order_btn = getView().findViewById(R.id.button5);
        history_btn = getView().findViewById(R.id.button6);



        backImage = getView().findViewById(R.id.backImage);

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

        topup_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
//                HomeFragment fragment2 = new HomeFragment();
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fl_container, fragment2,"home")
//                        .commit();
                Toast.makeText(getActivity(),"Fitur ini belum tersedia untuk saat ini",Toast.LENGTH_LONG).show();
            }
        });

        alamat_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlamatFragment fragment2 = new AlamatFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, fragment2,"backtoaccount")
                        .commit();
            }
        });

        order_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                OrderFragment fragment2 = new OrderFragment();

                Bundle bundle = new Bundle();

                bundle.putString("type", "micro");
                fragment2.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, fragment2,"backtoaccount")
                        .commit();
            }
        });

        history_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                OrderFragment fragment2 = new OrderFragment();

                Bundle bundle = new Bundle();

                bundle.putString("type", "c");
                fragment2.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, fragment2,"backtoaccount")
                        .commit();
            }
        });

        getData();




    }

    void getData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = Server.URL + "getaccount.php?id=" + Client.bangsatkau(fuid) ;

        StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("RankFrag", "Login Response: " + response.toString());

                try {
                    JSONObject jObjx = new JSONObject(response);
                    int success = jObjx.getInt("success");
                    String message = jObjx.getString("message");


                    if (success == 1) {

                        nama_tv.setText(jObjx.getString("nama"));
                        email_tv.setText(jObjx.getString("email") + " | " + jObjx.getString("nohp"));

                        saldo_tv.setText("Saldo : " + jObjx.getString("saldo"));


                        Picasso.get().load(jObjx.getString("gambar")).into(profil_iv);



                        //share_btn.setVisibility(View.VISIBLE);
                        // Check for error node in json

                        // Log.e("Successfully Login!", jObj.toString());



                        /*Toast.makeText(getActivity(),
                                jObj.getString("ReffFrag"), Toast.LENGTH_LONG).show();*/
                    } else {
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
                Log.e("RankFrag", "Gagal Mendapatkan Daftar Rank : " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Gagal Mendapatkan Daftar Rank" + error.getMessage(), Toast.LENGTH_LONG).show();

                // hideDialog();

            }
        });

        // Adding request to request queue
        queue.add(strReq);


    }
}
