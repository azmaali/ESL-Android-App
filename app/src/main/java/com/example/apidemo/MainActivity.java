package com.example.apidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    String url = "https://wordsapiv1.p.rapidapi.com/words/";
    StringRequest stringRequest; // Assume this exists.
    RequestQueue requestQueue;
    EditText mSearchInput;
    ProgressBar mProgressBar;
    SearchHistory history =  SearchHistory.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mSearchInput = findViewById(R.id.searchInput);
        mSearchInput.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    sendRequest(mSearchInput.getText().toString().trim());
                    return true;
                }
                return false;
            }

        });

        mSearchInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
              stopRequest();
            }
        });


    }

    public void stopRequest () {
        mProgressBar.setVisibility(View.INVISIBLE);
        requestQueue.cancelAll(TAG);
    }
        public void sendRequest (String word){


            mProgressBar.setVisibility(View.VISIBLE);
            stringRequest = new StringRequest(Request.Method.GET, url+word,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            Log.d("Response", response);
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                GsonBuilder builder = new GsonBuilder();
                                builder.disableHtmlEscaping();
                                Gson gson = builder.setPrettyPrinting().create();

                                Word_Information wordObject = gson.fromJson(response, Word_Information.class);
                                Intent intent = new Intent(MainActivity.this, WordResultActivity.class);
                                intent.putExtra("wordObject", gson.toJson(wordObject));
                                startActivity(intent);




                            } catch (final JSONException e) {
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),
                                                "Json parsing error: " + e.getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }


                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                           mProgressBar.setVisibility(View.INVISIBLE);
                            NetworkResponse networkResponse = error.networkResponse;
                            if (networkResponse != null && networkResponse.statusCode == 404) {
                                // HTTP Status Code: 404 not found
                                Toast.makeText(MainActivity.this,
                                        "Word not found.",
                                        Toast.LENGTH_LONG).show();
                            }

                            Log.d("ERROR", "error => " + error.toString());
                            if (error.networkResponse == null) {
                                if (error.getClass().equals(TimeoutError.class)) {
                                    // Show timeout error message
                                    Toast.makeText(MainActivity.this,
                                            "Oops. Timeout error!",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }


            )

            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("x-rapidapi-host", "wordsapiv1.p.rapidapi.com");
                    params.put("x-rapidapi-key", "eb214980f7msh33eb156f8cae07ap104b3cjsn58c47e48f55a");


                    return params;
                }



                    /**@Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        //mProgressBar.setVisibility(View.INVISIBLE);
                    int mStatusCode = response.statusCode;

                        Toast.makeText(MainActivity.this,
                                Integer.toString(mStatusCode),
                                Toast.LENGTH_LONG).show();

                    return super.parseNetworkResponse(response);
                }
                    **/

            };



            stringRequest.setTag(TAG);
            requestQueue.add(stringRequest);
        }





    @Override
    protected void onStop () {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }



}
