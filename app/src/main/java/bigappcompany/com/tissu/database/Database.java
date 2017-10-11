package bigappcompany.com.tissu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import bigappcompany.com.tissu.model.SaveLocationModel;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 26 Jun 2017 at 12:30 PM
 */

public class Database extends SQLiteOpenHelper {
	private static final int VERSION = 1;
	private static final String DATABASE = "savelocationDatabase";
	
	// Table Name
	private static final String SAVE_LOCATION = "save_location";
	private static final String SAVE_CATEGORY = "save_category";
	private static final String SAVE_PRODUCT = "save_product";
	private static final String SAVE_CART = "save_cart";
	
	//save_location_field
	private static final String KEY_LATITUTE = "_latitute";
	private static final String KEY_LONGITUTE = "_longitute";
	private static final String KEY_ZIPCODE = "_zipcode";
	private static final String KEY_CITY = "_city";
	private static final String KEY_STATE = "_state";
	private static final String KEY_COUNTRY = "_country";
	private static final String KEY_ADDRESS = "_address";
	
	//save_category_field
	private static final String KEY_CATEGORYID = "_categoryid";
	private static final String KEY_CATEGORYNAME = "_categoryname";
	private static final String KEY_HASSUBCATEGORY = "_hassubcategory";
	private static final String KEY_CATEGORYDESCRIPTION = "_categorydescription";
	private static final String KEY_SUBCATEGORYNUMBER = "_subcategorynumber";
	private static final String KEY_CATEGORYIMAGE = "_categoryimage";
	
	//save_product_List
	private static final String KEY_PRODUCTID = "_productid";
	private static final String KEY_PRODUCTNAME = "_productname";
	private static final String KEY_CATEGORY_ID = "_category_id";
	private static final String KEY_CATEGORY_NAME = "_category_name";
	private static final String KEY_PRICEWITHTAX = "_pricewithtax";
	private static final String KEY_PRODUCTIMAGE = "_productimage";
	private static final String KEY_PRODUCTSHORTDESC = "_productshortdesc";
	private static final String KEY_PRODDESC = "_proddesc";
	private static final String KEY_USERSELECTEDQTY = "_userselectedqty";
	
	//save_cart_List
	private static final String KEY_PRODUCTID_CART = "_productid_cart";
	private static final String KEY_PRODUCTNAME_CART = "_productname_cart";
	private static final String KEY_CATEGORY_ID_CART = "_category_id_cart";
	private static final String KEY_CATEGORY_NAME_CART = "_category_name_cart";
	private static final String KEY_PRICEWITHTAX_CART = "_pricewithtax_cart";
	private static final String KEY_PRODUCTIMAGE_CART = "_productimage_cart";
	private static final String KEY_PRODUCTSHORTDESC_CART = "_productshortdesc_cart";
	private static final String KEY_PRODDESC_CART = "_proddesc_cart";
	private static final String KEY_USERSELECTEDQTY_CART = "_userselectedqty_cart";
	
	public Database(Context context) {
		super(context, DATABASE, null, VERSION);
		// TODO Auto-generated constructor stub
	}
	
	private static final String save_location = "create table " + SAVE_LOCATION + " (" + KEY_LATITUTE
	    + " text, " + KEY_LONGITUTE + " text, " + KEY_ZIPCODE + " text, " + KEY_CITY + " text, " +
	    KEY_STATE + " text, " + KEY_COUNTRY + " text, " + KEY_ADDRESS + " text " + ")";
	
	private static final String save_category = "create table " + SAVE_CATEGORY + " (" + KEY_CATEGORYID
	    + " text, " + KEY_CATEGORYNAME + " text, " + KEY_HASSUBCATEGORY + " text, " + KEY_CATEGORYDESCRIPTION + " text, " +
	    KEY_SUBCATEGORYNUMBER + " text, " + KEY_CATEGORYIMAGE + " text " + ")";
	
	private static final String save_productlist = "create table " + SAVE_PRODUCT + " (" + KEY_PRODUCTID
	    + " text, " + KEY_PRODUCTNAME + " text, " + KEY_CATEGORY_ID + " text, " + KEY_CATEGORY_NAME + " text, " +
	    KEY_PRICEWITHTAX + " text, " + KEY_PRODUCTIMAGE + " text, " + KEY_PRODUCTSHORTDESC + " text, " + KEY_PRODDESC + " text, " +
	    KEY_USERSELECTEDQTY + " text " + ")";
	
	private static final String save_cartlist = "create table " + SAVE_CART + " (" + KEY_PRODUCTID_CART
	    + " text, " + KEY_PRODUCTNAME_CART + " text, " + KEY_CATEGORY_ID_CART + " text, " + KEY_CATEGORY_NAME_CART + " text, " +
	    KEY_PRICEWITHTAX_CART + " text, " + KEY_PRODUCTIMAGE_CART + " text, " + KEY_PRODUCTSHORTDESC_CART + " text, " + KEY_PRODDESC_CART + " text, " +
	    KEY_USERSELECTEDQTY_CART + " text " + ")";
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(save_location);
		db.execSQL(save_category);
		db.execSQL(save_productlist);
		db.execSQL(save_cartlist);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + SAVE_LOCATION);
		db.execSQL("DROP TABLE IF EXISTS " + SAVE_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS " + SAVE_PRODUCT);
		db.execSQL("DROP TABLE IF EXISTS " + SAVE_CART);
		
	}
	
	public void insertLocation(String latitute, String longitute, String zipcode, String city, String state,
	                           String country, String address) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_LATITUTE, latitute);
		values.put(KEY_LONGITUTE, longitute);
		values.put(KEY_ZIPCODE, zipcode);
		values.put(KEY_CITY, city);
		values.put(KEY_STATE, state);
		values.put(KEY_COUNTRY, country);
		values.put(KEY_ADDRESS, address);
		
		// Inserting Row
		try {
			db.insert(SAVE_LOCATION, null, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close(); // Closing database connection
	}
	
	public void insertCategory(String categoryid, String categoryname, String hassubcategory, String categorydescription, String subcategorynumber,
	                           String categoryimage) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORYID, categoryid);
		values.put(KEY_CATEGORYNAME, categoryname);
		values.put(KEY_HASSUBCATEGORY, hassubcategory);
		values.put(KEY_CATEGORYDESCRIPTION, categorydescription);
		values.put(KEY_SUBCATEGORYNUMBER, subcategorynumber);
		values.put(KEY_CATEGORYIMAGE, categoryimage);
		
		// Inserting Row
		try {
			db.insert(SAVE_CATEGORY, null, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close(); // Closing database connection
	}
	
	
	public void insertproductlist(String productid, String productname, String categoryid, String categoryname, String
	    pricewithtax, String productimage, String productshortdesc, String proddesc, String userselectedqty) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_PRODUCTID, productid);
		values.put(KEY_PRODUCTNAME, productname);
		values.put(KEY_CATEGORY_ID, categoryid);
		values.put(KEY_CATEGORY_NAME, categoryname);
		values.put(KEY_PRICEWITHTAX, pricewithtax);
		values.put(KEY_PRODUCTIMAGE, productimage);
		values.put(KEY_PRODUCTSHORTDESC, productshortdesc);
		values.put(KEY_PRODDESC, proddesc);
		values.put(KEY_USERSELECTEDQTY, userselectedqty);
		try {
			db.insert(SAVE_PRODUCT, null, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close(); // Closing database connection
	}
	
	public void insertcartlist(String productid, String productname, String categoryid, String categoryname, String
	    pricewithtax, String productimage, String productshortdesc, String proddesc, String userselectedqty) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_PRODUCTID_CART, productid);
		values.put(KEY_PRODUCTNAME_CART, productname);
		values.put(KEY_CATEGORY_ID_CART, categoryid);
		values.put(KEY_CATEGORY_NAME_CART, categoryname);
		values.put(KEY_PRICEWITHTAX_CART, pricewithtax);
		values.put(KEY_PRODUCTIMAGE_CART, productimage);
		values.put(KEY_PRODUCTSHORTDESC_CART, productshortdesc);
		values.put(KEY_PRODDESC_CART, proddesc);
		values.put(KEY_USERSELECTEDQTY_CART, userselectedqty);
		values.put(KEY_USERSELECTEDQTY_CART, userselectedqty);
		try {
			db.insert(SAVE_CART, null, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close(); // Closing database connection
	}
	
	
	
	public ArrayList<SaveLocationModel> getlocation() {
		ArrayList<SaveLocationModel> msgModel = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		String s = "Select * from " + SAVE_LOCATION;
		
		Cursor c = db.rawQuery(s, null);
		
		if (c.moveToFirst()) {
			do {
				SaveLocationModel item = new SaveLocationModel(c.getString(0), c.getString(1), c.getString(2)
				    , c.getString(3), c.getString(4), c.getString(5), c.getString(6));
				/*item.(c.getString(0));
				item.setCity_id(c.getString(1));
				item.setLocation(c.getString(2));
				*/
				msgModel.add(item);
			}
			while (c.moveToNext());
		}
		c.close();
		db.close();
		
		
		return msgModel;
	}
	
	
}
