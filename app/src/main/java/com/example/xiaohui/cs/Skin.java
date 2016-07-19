package com.example.xiaohui.cs;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kelvin on 13/7/2016.
 */

public class Skin {
	@SerializedName("success")
	private boolean success;
	@SerializedName("lowest_price")
	private String lowest_price;
	@SerializedName("volume")
	private String volume;
	@SerializedName("median_price")
	private String median_price;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getLowest_price() {
		return lowest_price;
	}

	public void setLowest_price(String lowest_price) {
		this.lowest_price = lowest_price;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getMedian_price() {
		return median_price;
	}

	public void setMedian_price(String median_price) {
		this.median_price = median_price;
	}
}
