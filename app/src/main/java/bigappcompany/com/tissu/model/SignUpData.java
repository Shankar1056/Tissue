package bigappcompany.com.tissu.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 11 Sep 2017 at 12:41 PM
 */

class SignUpData {
	
	public String getFirst_name() {
		return first_name;
	}
	
	public String getEmail() {
		return email;
	}
	
	@SerializedName("first_name")
	private String first_name;
	@SerializedName("email")
	private String email;
}
