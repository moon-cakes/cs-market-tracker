package com.example.xiaohui.cs.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.xiaohui.cs.DatabaseAccess;
import com.example.xiaohui.cs.R;
import com.example.xiaohui.cs.Skin;
import com.example.xiaohui.cs.SteamMarketClient;
import com.example.xiaohui.cs.SteamMarketService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Xiaohui on 7/21/2016.
 */
public class SkinInfoFragment extends Fragment {

    private ListView listViewWearsAndPrice;
    private List<String> listOfWears;
    Map<String, String> wearPrice;
    SimpleAdapter adapter;
    List<Map<String, String>> listWearsAndPrices;
    public final static String WEAP_SELECTED_KEY = "weap";
    public final static String SKIN_SELECTED_KEY = "skin";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_fragment_view, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        listOfWears = new ArrayList<String>();
        listOfWears.add(getResources().getString(R.string.factory_new));
        listOfWears.add(getResources().getString(R.string.minimal_wear));
        listOfWears.add(getResources().getString(R.string.field_tested));
        listOfWears.add(getResources().getString(R.string.well_worn));
        listOfWears.add(getResources().getString(R.string.battle_scarred));

        listWearsAndPrices = new ArrayList();
        Bundle args = getArguments();
        if (args != null) {

            String weapon = args.getString(WEAP_SELECTED_KEY);
            String skin = args.getString(SKIN_SELECTED_KEY);

            for (String wear: listOfWears){
                wearPrice = new HashMap();
                wearPrice.put("Wear", wear);

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

            this.listViewWearsAndPrice = (ListView) getView().findViewById(R.id.listView);
            adapter = new SimpleAdapter(getActivity(), listWearsAndPrices, android.R.layout.simple_list_item_2,
                    new String[] {"Wear", "Price"}, new int[] {android.R.id.text1, android.R.id.text2});
            listViewWearsAndPrice.setAdapter(adapter);

        }
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
}
