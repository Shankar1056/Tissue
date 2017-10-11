package bigappcompany.com.tissu.model;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 11 Jul 2017 at 4:17 PM
 */

public class TotalOrderItems {
	public String getOrder_items_id() {
		return order_items_id;
	}
	
	public String getOrder_items_name() {
		return order_items_name;
	}
	
	public String getOrder_items_price() {
		return order_items_price;
	}
	
	public String getOrder_items_qty() {
		return order_items_qty;
	}
	
	public String getOrders_items_image() {
		return orders_items_image;
	}
	
	public String getProduct_avalibel_qty() {
		return product_avalibel_qty;
	}
	
	public String getUnit_name() {
		return unit_name;
	}
	
	public String getUnit_value() {
		return unit_value;
	}
	
	public String getBusiness_name() {
		return business_name;
	}
	
	public String getOrders_items_discount_type() {
		return orders_items_discount_type;
	}
	
	public String getOrders_items_discount() {
		return orders_items_discount;
	}
	
	public String getOrder_item_orginal_price() {
		return order_item_orginal_price;
	}
	
	public String getOrder_item_discount_price() {
		return order_item_discount_price;
	}
	
	public String getOrder_items_product_id() {
		return order_items_product_id;
	}
	
	String order_items_id;
	String order_items_name;
	String order_items_price;
	String order_items_qty;
	String orders_items_image;
	String product_avalibel_qty;
	String unit_name;
	String unit_value;
	String business_name;
	String orders_items_discount_type;
	String orders_items_discount;
	String order_item_orginal_price;
	String order_item_discount_price;
	String order_items_product_id;
	
	public String getOrder_items_order_id() {
		return order_items_order_id;
	}
	
	String order_items_order_id;
	
	public TotalOrderItems(String order_items_id, String order_items_name, String order_items_price,
	                       String order_items_qty, String orders_items_image, String product_avalibel_qty,
	                       String unit_name, String unit_value, String business_name, String orders_items_discount_type,
	                       String orders_items_discount, String order_item_orginal_price, String
	                           order_item_discount_price, String order_items_product_id,String
	                           order_items_order_id) {
		this.order_items_id = order_items_id;
		this.order_items_name = order_items_name;
		this.order_items_price = order_items_price;
		this.order_items_qty = order_items_qty;
		this.orders_items_image = orders_items_image;
		this.product_avalibel_qty = product_avalibel_qty;
		this.unit_name = unit_name;
		this.unit_value = unit_value;
		this.business_name = business_name;
		this.orders_items_discount_type = orders_items_discount_type;
		this.orders_items_discount = orders_items_discount;
		this.order_item_orginal_price = order_item_orginal_price;
		this.order_item_discount_price = order_item_discount_price;
		this.order_items_product_id = order_items_product_id;
		this.order_items_order_id = order_items_order_id;
	}
}
