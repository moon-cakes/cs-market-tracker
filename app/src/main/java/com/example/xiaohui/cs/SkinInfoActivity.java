package com.example.xiaohui.cs;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SkinInfoActivity extends AppCompatActivity {

    ListView itemType;
    private ListView listViewWearsAndPrice;
    private List<String> listOfWears;
    Map<String, String> wearPrice;
    SimpleAdapter adapter;
    //Map<String, String> wearPrice;
    //List<Map<String, String>> allWearsPrices;
    List<Map<String, String>> listWearsAndPrices;

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

        listWearsAndPrices = new ArrayList();


        //listWearsAndPrices contains the list of all wears and their lowest prices
        //final List<Map<String, String>> listWearsAndPrices = new ArrayList<Map<String, String>>();

        //loop through
        for (String wear: listOfWears){
            wearPrice = new HashMap();
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
                retrievePrice(weapon, skin, wear);
            }

            listWearsAndPrices.add(wearPrice);
        }

        this.listViewWearsAndPrice = (ListView) findViewById(R.id.listViewWears);
        adapter = new SimpleAdapter(this, listWearsAndPrices, android.R.layout.simple_list_item_2,
                new String[] {"Wear", "Price"}, new int[] {android.R.id.text1, android.R.id.text2});
        listViewWearsAndPrice.setAdapter(adapter);

    }

    private void retrievePrice(String weapon, String skin, final String wear){

        Map<String, String> queryMap = new HashMap();
        queryMap.put("currency", "1");
        queryMap.put("appid", "730");
        String marketHashName = weapon + " | " + skin + " (" + wear + ")";
        queryMap.put("market_hash_name", marketHashName);

        SteamMarketService sms = SteamMarketClient.getClient().create(SteamMarketService.class);
        Call<Skin> call = sms.getSkinInfo(queryMap);
        call.enqueue(new Callback<Skin>() {
            @Override
            public void onResponse(Call<Skin> call, retrofit2.Response<Skin> response) {
                int indexOfWear = listOfWears.indexOf(wear);
                Map<String, String> itemInList = listWearsAndPrices.get(indexOfWear);

                String price;
                try {
                    price = response.body().getLowest_price();
                } catch (NullPointerException e){
                    price = "Unavailable";
                }
                itemInList.put("Price", price);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Skin> call, Throwable t) {
                int indexOfWear = listOfWears.indexOf(wear);
                Map<String, String> itemInList = listWearsAndPrices.get(indexOfWear);

                itemInList.put("Price", "Unknown Error");
                adapter.notifyDataSetChanged();
            }
        });
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
