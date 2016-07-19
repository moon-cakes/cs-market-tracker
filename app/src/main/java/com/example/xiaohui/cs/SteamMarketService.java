package com.example.xiaohui.cs;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Kelvin on 13/7/2016.
 */
public interface SteamMarketService {
	@GET("market/priceoverview")
	Call<Skin> getSkinInfo(@QueryMap Map<String, String> options);
}
