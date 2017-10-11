package bigappcompany.com.tissu.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 11 Sep 2017 at 12:40 PM
 */

public class SignupDetails {
	
	/*@SerializedName("data")
       private SignUpData data;*/
	
	@SerializedName("status")
	private String status;
	@SerializedName("msg")
	private String msg;
	
	
	//public SignUpData getData() {
		//return data;
	//}
	public String getStatus() {
		return status;
	}
	public String getMsg() {
		return msg;
	}
	
	
}
