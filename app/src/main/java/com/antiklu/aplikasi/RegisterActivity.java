package com.antiklu.aplikasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.antiklu.aplikasi.settings.Client;
import com.antiklu.aplikasi.settings.Server;

import org.json.JSONException;
import org.json.JSONObject;

import static com.antiklu.aplikasi.SpawnActivity.my_shared_preferences;

public class RegisterActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    Boolean session = false;

    String fuid;
    EditText nohp_et,reff_et;
    Button submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
            session = sharedpreferences.getBoolean("login", false);
            fuid = sharedpreferences.getString("fuid",null);
            if (!session) {
                Intent intent = new Intent(getApplicationContext(), SpawnActivity.class);
                finish();
                startActivity(intent);
            }

            nohp_et = findViewById(R.id.editText2);
            reff_et = findViewById(R.id.editText3);
            submit_btn = findViewById(R.id.button7);

            submit_btn.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    handlePostReg();
                }
            });
        }

        void handlePostReg() {
            final String nohp = nohp_et.getText().toString();
            final String refferal = reff_et.getText().toString();
            Log.e("RegAct",nohp + refferal);

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = Server.URL + "dopr.php?id=" + Client.bangsatkau(fuid) + "&nohp=" + Client.bangsatkau(nohp) + "&alamat=" + Client.bangsatkau(refferal);

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
                                    Toast.makeText(getApplicationContext(),
                                            "Purna Daftar Sukses!", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    finish();
                                    startActivity(intent);


                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            message, Toast.LENGTH_LONG).show();
                                }


                            } catch (JSONException e) {
                                Log.e("RegAct",e.toString());
                                Toast.makeText(getApplicationContext(),
                                        "Kesalahan Parsing . " + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),
                            "Gagal Purna daftar" + error.getMessage(), Toast.LENGTH_LONG).show();

                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

