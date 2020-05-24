package com.antiklu.aplikasi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.antiklu.aplikasi.prakmen.HomeFragment;
import com.antiklu.aplikasi.settings.Client;
import com.antiklu.aplikasi.settings.Server;

import org.json.JSONException;
import org.json.JSONObject;

import static com.antiklu.aplikasi.SpawnActivity.my_shared_preferences;

public class MainActivity extends AppCompatActivity {

        SharedPreferences sharedpreferences;
        Boolean session = false;


        String fuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean("login", false);
        fuid = sharedpreferences.getString("fuid",null);
        if (!session) {
            Intent intent = new Intent(getApplicationContext(), SpawnActivity.class);
            finish();
            startActivity(intent);
        }

        handlePostRegistration();
    }

    void initFragment() {
        Fragment fragment = null;
        fragment = new HomeFragment();
        loadFragment(fragment);
    }

    void handlePostRegistration() {

        final TextView textView = (TextView) findViewById(R.id.text);
        // ...

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Server.URL + "handlepr.php?id=" + Client.bangsatkau(fuid);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            int success = jObj.getInt("success");
                            int registered = jObj.getInt("registered");
                            String message = jObj.getString("message");
                            // Display the first 500 characters of the response string.

                            if (success == 1) {

                                if (registered == 1) {
                                    handleUpdateCheck();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                                    finish();
                                    startActivity(intent);
                                }

                            } else {
                                showExitDialog("Gagal Cek Pendaftaran . " + message);
                            }


                        } catch (JSONException e) {
                            showExitDialog("Kesalahan Parsing . " + e.toString());
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showExitDialog("Gagal Mengakses Server  . 20 " + error.toString());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    void handleUpdateCheck() {
        final TextView textView = (TextView) findViewById(R.id.text);
        // cek apdet dan banned

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Server.URL + "update.php?id=" + Client.bangsatkau(fuid) + "&v=" + Client.bangsatkau(Client.APP_VERSION);

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
                            String url = jObj.getString("url");
                            int uptodate = jObj.getInt("uptodate");
                            String message = jObj.getString("message");

                            // Display the first 500 characters of the response string.

                            if (success == 1) {

                                if (uptodate == 1) {
                                    initFragment();
                                } else {
                                    showUpdateDialog(message,url);
                                }

                            } else {
                                showExitDialog("Gagal Cek Update . " + message);
                            }


                        } catch (JSONException e) {
                            showExitDialog("Kesalahan Parsing . " + e.toString());
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showExitDialog("Gagal Mengakses Server . 21 " + error.toString());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void showUpdateDialog(final String message,final String url){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle(R.string.app_name);

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(message)
                .setIcon(R.drawable.antiklunotification)
                .setCancelable(false)
                .setPositiveButton("Update",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        //intent ke url update
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                }).setNegativeButton("Tutup",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                // jika tombol diklik, maka akan menutup activity ini
                System.exit(0);
            }
        });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void showExitDialog(final String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle(R.string.app_name);

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(message)
                .setIcon(R.drawable.antiklunotification)
                .setCancelable(false)
                .setPositiveButton("Tutup Aplikasi",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        System.exit(1);
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment,"home")
                    .commit();
            return true;
        }
        return false;
    }

    public void onBackPressed() {
//        if(USER_IS_GOING_TO_EXIT) {
//            if(backtoast!=null&&backtoast.getView().getWindowToken()!=null) {
//                finish();
//            } else {
//                backtoast = Toast.makeText(this, "Press back to exit", Toast.LENGTH_SHORT);
//                backtoasdt.show();
//            }
//        } else {
//            //other stuff...
//            super.onBackPressed();
//        }
//
//
        Fragment myFragment = getSupportFragmentManager().findFragmentByTag("home");
        if (myFragment != null && myFragment.isVisible()) {
            finish();
        }
        Fragment myFragment2 = getSupportFragmentManager().findFragmentByTag("account");
        if (myFragment2 != null && myFragment2.isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, new HomeFragment(), "home").commit();
        }
//        Fragment myFragment3 = getSupportFragmentManager().findFragmentByTag("refferalnews");
//        if (myFragment3 != null && myFragment3.isVisible()) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, new AccountFragment(), "home").commit();
//        }


        //super.onBackPressed();
    }

}
