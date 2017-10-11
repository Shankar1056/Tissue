package bigappcompany.com.tissu.model;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 07 Jul 2017 at 12:01 PM
 */

public class UserOrderModel {
	public String getMsg() {
		return msg;
	}
	
	public String getStatus() {
		return status;
	}
	
	public OrderData getData() {
		return data;
	}
	
	String msg,status;
	public OrderData data;
	
}
