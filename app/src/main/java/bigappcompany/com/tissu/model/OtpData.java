package bigappcompany.com.tissu.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Shankar
 * @created on 09 Sep 2017 at 3:22 PM
 */

public class OtpData {
	
	public String getOtp() {
		return otp;
	}
	
	public String getUser_exist() {
		return user_exist;
	}
	
	public String getSession_token() {
		return session_token;
	}
	
	public String getUser_id() {
		return user_id;
	}
	
	public String getUser_email() {
		return user_email;
	}
	
	public String getUser_first_name() {
		return user_first_name;
	}
	
	@SerializedName("otp")
	
	private String otp;
	
	@SerializedName("user_exist")
	private String user_exist;
	@SerializedName("session_token")
	private String session_token;
	@SerializedName("user_id")
	private String user_id;
	@SerializedName("user_email")
	private String user_email;
    @SerializedName("user_first_name")
    private String user_first_name;
}
