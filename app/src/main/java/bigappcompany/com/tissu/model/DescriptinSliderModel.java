package bigappcompany.com.tissu.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 20 Sep 2017 at 5:29 PM
 */

public class DescriptinSliderModel {
	
	public String getProduct_image() {
		return product_image;
	}
	
	private final String product_image;
	
	public DescriptinSliderModel(JSONObject object) throws JSONException {
		product_image = object.getString("product_image");
		
	}
	
	
	
	
	
}
