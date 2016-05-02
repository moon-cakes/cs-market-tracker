package com.example.xiaohui.cs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkinInfoActivity extends AppCompatActivity {

    ListView itemType;
    private TextView priceView;
    private ListView listViewWearsAndPrice;
    private List<String> listOfWears;
    Map<String, String> wearPrice;
    SimpleAdapter adapter;
    //Map<String, String> wearPrice;
    //List<Map<String, String>> allWearsPrices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skininfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //receive intent to match next screen view
        //Intent i = getIntent();
        String skin = getIntent().getStringExtra("skin");
        String weapon = getIntent().getStringExtra("weaponName");
        setTitle(skin + " - (" + weapon + ")");

        this.itemType = (ListView) findViewById(R.id.listViewWears);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        //add the different types of list of wears
        listOfWears = new ArrayList<String>();
        listOfWears.add(getResources().getString(R.string.factory_new));
        listOfWears.add(getResources().getString(R.string.minimal_wear));
        listOfWears.add(getResources().getString(R.string.field_tested));
        listOfWears.add(getResources().getString(R.string.well_worn));
        listOfWears.add(getResources().getString(R.string.battle_scarred));


        //listWearsAndPrices contains the list of all wears and their lowest prices
        final List<Map<String, String>> listWearsAndPrices = new ArrayList<Map<String, String>>();

        //loop through
        for (final String wear: listOfWears){
            wearPrice = new HashMap<String, String>();
            wearPrice.put("Wear", wear);

            /**
             * Get information about the lowest price for items
             * https://steamcommunity.com/market/priceoverview/?currency=1&appid=730&market_hash_name=AK-47%20|%20Predator%20%28Field-Tested%29
             * currency=1 for USD
             * currency=22 for NZD
             */

            String baseURL = "https://steamcommunity.com/market/priceoverview/?currency=1&appid=730&market_hash_name=";

            //Special case for knives as they require an extra ★ character
            if (weapon.equals("knife")) {
                /**
                 * TODO
                 * Implement
                 */

            } else {
                baseURL = "https://steamcommunity.com/market/priceoverview/?currency=1&appid=730&market_hash_name=";
                String retrievalURL = baseURL + weapon + "%20|%20" + skin + "%20%28" + wear + "%29";
                retrievalURL = retrievalURL.replaceAll(" ", "%20");

                /**
                 * Fetch information from URL
                 * Reference: http://developer.android.com/training/volley/simple.html#manifest
                 */
                RequestQueue queue = Volley.newRequestQueue(this);

                // Request a string response from the provided URL.
                JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, retrievalURL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                   int indexOfWear = listOfWears.indexOf(wear);
                                    Map<String, String> itemInList = listWearsAndPrices.get(indexOfWear);
                                    if (response.getBoolean("success")) {
                                        itemInList.put("Price", response.getString("lowest_price"));
                                    } else {
                                        // If price is not possible
                                        itemInList.put("Price", "Item Unavailable");
                                        Log.e("tag", "Item unavailable unreached");
                                    }
                                    // Update view
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        /**
                         * TODO
                         * CHECK FOR INTERNET CONNECTION
                         */
                        int indexOfWear = listOfWears.indexOf(wear);
                        Map<String, String> itemInList = listWearsAndPrices.get(indexOfWear);
                        itemInList.put("Price", "Item Unavailable");
                        adapter.notifyDataSetChanged();
                    }
                });
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
            listWearsAndPrices.add(wearPrice);
        }

        this.listViewWearsAndPrice = (ListView) findViewById(R.id.listViewWears);
        adapter = new SimpleAdapter(this, listWearsAndPrices, android.R.layout.simple_list_item_2,
                new String[] {"Wear", "Price"}, new int[] {android.R.id.text1, android.R.id.text2});
        listViewWearsAndPrice.setAdapter(adapter);

    }

    private class RetrieveData extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            List<Map<String, String>> listWearsAndPrices = new ArrayList<Map<String, String>>();
            for (String wear: listOfWears){
                wearPrice = new HashMap<String, String>();
                wearPrice.put("Wear", wear);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
