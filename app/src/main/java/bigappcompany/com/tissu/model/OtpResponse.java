package bigappcompany.com.tissu.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 09 Sep 2017 at 3:01 PM
 */

public class OtpResponse {
	
	public String getStatus() {
		return status;
	}
	
	public OtpData getOtpData() {
		return otpData;
	}
	
	public String getMsg() {
		return msg;
	}
	
	@SerializedName("status")
	private String status;
	@SerializedName("data")
	private OtpData otpData;
	@SerializedName("msg")
	private String msg;
}
