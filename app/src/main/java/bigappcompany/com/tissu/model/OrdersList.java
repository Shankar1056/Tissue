package bigappcompany.com.tissu.model;

import java.util.ArrayList;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 11 Jul 2017 at 4:08 PM
 */

public class OrdersList {
	
	String orders_id,orders_created_date,order_delivery_date,order_status_name,order_total,order_grand_total
	    ,coupon_details;
	
	ArrayList<TotalOrderItems> order_items;
	
	public ArrayList<TotalOrderItems> getOrder_items() {
		return order_items;
	}
	
	
	public String getOrders_id() {
		return orders_id;
	}
	
	public String getOrders_created_date() {
		return orders_created_date;
	}
	
	public String getOrder_delivery_date() {
		return order_delivery_date;
	}
	
	public String getOrder_status_name() {
		return order_status_name;
	}
	
	
	
	public String getOrder_total() {
		return order_total;
	}
	
	public String getOrder_grand_total() {
		return order_grand_total;
	}
	
	public String getCoupon_details() {
		return coupon_details;
	}
	
}
