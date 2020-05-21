package com.antiklu.aplikasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.antiklu.aplikasi.settings.Client;
import com.antiklu.aplikasi.settings.Server;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

public class SpawnActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private static String TAG = "Spawn";
    private static int RC_SIGN_IN = 69;
    SharedPreferences sharedpreferences;
    Boolean session = false;

    public static final String my_shared_preferences = "ngehack_mandul_7_turunan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spawn);

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean("login", false);
        if (session) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_spawn);

        mAuth = FirebaseAuth.getInstance();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(getApplicationContext(),"Gagal Masuk akun Google . " + e.toString() , Toast.LENGTH_LONG).show();

                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            final String aidi = acct.getId();


                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            String url = Server.URL + "daftarcheck.php?id=" + aidi;

                            // Request a string response from the provided URL.
                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // Display the first 500 characters of the response string.
                                            result = response;
                                            String handleDaftarCheckResult = response;
                                            if (handleDaftarCheckResult.equals("registered")) {

                                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                                editor.putBoolean("login", true);
                                                editor.putString("fuid", aidi);
                                                editor.commit();

                                                // Memanggil main activity
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                finish();
                                                startActivity(intent);

                                            } else if (handleDaftarCheckResult.equals("notregistered") ) {


                                                //kalau belum auto ambil data dari mbah google dan tambah ke volley/backend/php . udh itu sharedpreferences

                                                String email = acct.getEmail();
                                                String name = acct.getDisplayName();
                                                String photo = acct.getPhotoUrl().toString();

                                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                                String url = Server.URL + "daftar.php?id=" + Client.bangsatkau(aidi) + "&email=" + Client.bangsatkau(email) + "&name=" + Client.bangsatkau(name) + "&photo=" + Client.bangsatkau(photo);

                                                // Request a string response from the provided URL.
                                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                // Display the first 500 characters of the response string.
                                                                Log.d(TAG,"daftar " + response);

                                                                try {
                                                                    JSONObject jObj = new JSONObject(response);
                                                                    int success = jObj.getInt("success");
                                                                    String message = jObj.getString("message");
                                                                    // Check for error node in json
                                                                    if (success == 1) {
                                                                        Toast.makeText(getApplicationContext(),
                                                                                "Pendaftaran Sukses", Toast.LENGTH_SHORT).show();
                                                                        result = "success";
                                                                    } else {
                                                                        Toast.makeText(getApplicationContext(),
                                                                                "Pendaftaran Gagal" + message , Toast.LENGTH_LONG).show();
                                                                        result = jObj.getString("message");

                                                                    }
                                                                } catch (JSONException e) {
                                                                    // JSON error
                                                                    result = e.toString();
                                                                }


                                                                String handleDaftarResult = result;
                                                                if (handleDaftarResult.equals("success")) {

                                                                    // menyimpan login ke session
                                                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                                                    editor.putBoolean("login", true);
                                                                    editor.putString("fuid", aidi);
                                                                    editor.commit();

                                                                    // Memanggil main activity
                                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                                    finish();
                                                                    startActivity(intent);

                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), "Gagal Mendaftar . " + handleDaftarResult, Toast.LENGTH_LONG).show();
                                                                }
                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        result = error.toString();
                                                    }
                                                });

                                                // Add the request to the RequestQueue.
                                                queue.add(stringRequest);



                                            } else {
                                                Toast.makeText(getApplicationContext(), "Gagal Cek Pendaftaran . " + handleDaftarCheckResult, Toast.LENGTH_LONG).show();

                                            }


                                            Log.d(TAG,"daftarcheck " + response);
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    result = error.toString();
                                }
                            });

                            // Add the request to the RequestQueue.
                            queue.add(stringRequest);



                            //redirect ke activity main




                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            // Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),"Autentikasi Gagal" , Toast.LENGTH_LONG).show();
                            // updateUI(null);
                        }

                        // ...
                    }
                });
    }
    String result = "";
}
