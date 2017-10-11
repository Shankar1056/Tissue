package bigappcompany.com.tissu.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 22 Sep 2017 at 11:12 AM
 */

public class MyOrderDescModel  implements Parcelable{
	
	public String getImage() {
		return image;
	}
	
	protected MyOrderDescModel(Parcel in) {
		name = in.readString();
		quantity = in.readString();
		price = in.readString();
		image = in.readString();
	}
	
	public static final Creator<MyOrderDescModel> CREATOR = new Creator<MyOrderDescModel>() {
		@Override
		public MyOrderDescModel createFromParcel(Parcel in) {
			return new MyOrderDescModel(in);
		}
		
		@Override
		public MyOrderDescModel[] newArray(int size) {
			return new MyOrderDescModel[size];
		}
	};
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getQuantity() {
		return quantity;
	}
	
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	private String name,quantity,price,image;
	
	public MyOrderDescModel(String name, String quantity, String price, String image)
	{
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.image = image;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel parcel, int i) {
		
		parcel.writeString(name);
		parcel.writeString(quantity);
		parcel.writeString(price);
		parcel.writeString(image);
	}
}
