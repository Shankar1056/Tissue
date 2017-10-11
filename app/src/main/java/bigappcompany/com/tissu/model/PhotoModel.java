package bigappcompany.com.tissu.model;

import org.json.JSONException;
import org.json.JSONObject;


public class PhotoModel {
	private final String id, title, sl_image;
	//For DBACCess
	private String date;

	public PhotoModel(JSONObject object) throws JSONException {
		id = object.getString("slider_id");
		sl_image = object.getString("slider_image");
		title = object.getString("slider_title");

	}
	


	public String getId() {
		return id;
	}
	public String getDate() {
		return date;
	}

	public String getTitle() {
		return title;
	}

	public String getPhoto() {
		return sl_image;
	}

	
}
