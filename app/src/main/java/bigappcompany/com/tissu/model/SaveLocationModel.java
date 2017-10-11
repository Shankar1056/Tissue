package bigappcompany.com.tissu.model;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 26 Jun 2017 at 11:47 AM
 */

public class SaveLocationModel {
	public String getLatitute() {
		return latitute;
	}
	
	public String getLongotute() {
		return longotute;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getState() {
		return state;
	}
	
	public String getContry() {
		return contry;
	}
	
	public String getAddress() {
		return address;
	}
	
	String latitute, longotute, zipcode, city, state, contry,address;
	
	public SaveLocationModel(String latitute, String longotute, String zipcode, String city, String state,
	                         String contry, String address)
	{
		this.latitute = latitute;
		this.longotute = longotute;
		this.zipcode = zipcode;
		this.city = city;
		this.state = state;
		this.contry = contry;
		this.address = address;
	}
}
