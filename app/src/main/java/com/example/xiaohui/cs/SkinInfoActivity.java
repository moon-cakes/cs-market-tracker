package com.example.xiaohui.cs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SkinInfoActivity extends AppCompatActivity {

    ListView itemType;
    private TextView priceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skininfo);

        this.itemType = (ListView) findViewById(R.id.listView);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        final TextView priceView = (TextView) findViewById(R.id.priceView);

        //receive intent to match next screen view
        Intent i = getIntent();
        String skin = i.getStringExtra("skin");
        String weapon = i.getStringExtra("weaponName");

        /**
         * Get information about the lowest price for items
         * https://steamcommunity.com/market/priceoverview/?currency=1&appid=730&market_hash_name=AK-47%20|%20Predator%20%28Field-Tested%29
         * currency=1 for USD
         * currency=22 for NZD
         */

        String baseURL = "https://steamcommunity.com/market/priceoverview/?currency=1&appid=730&market_hash_name=";

        //Special case for knives as they require an extra ★ character
        if (weapon.equals("knife")) {


        } else {
            baseURL = "https://steamcommunity.com/market/priceoverview/?currency=1&appid=730&market_hash_name=";
            String retrievalURL = baseURL + weapon + "%20|%20" + skin + "%20%28Field-Tested%29";
            retrievalURL = retrievalURL.replaceAll(" ", "%20");

            RequestQueue queue = Volley.newRequestQueue(this);

            /**
             * Fetch information from URL
             * Reference: http://developer.android.com/training/volley/simple.html#manifest
             */
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, retrievalURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            priceView.setText(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    priceView.setText("That didn't work!");
                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }
}
