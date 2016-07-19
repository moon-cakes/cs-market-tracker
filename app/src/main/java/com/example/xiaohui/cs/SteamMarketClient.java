package com.example.xiaohui.cs;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kelvin on 18/7/2016.
 */
public class SteamMarketClient {
	public static final String baseURL = "https://steamcommunity.com/";
	private static Retrofit retrofit = null;

	public static Retrofit getClient(){
		if (retrofit == null){
			retrofit = new Retrofit.Builder()
					.baseUrl(baseURL)
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		}
		return retrofit;
	}
}
