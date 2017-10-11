package bigappcompany.com.tissu.model;

import java.util.ArrayList;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 11 Jul 2017 at 4:08 PM
 */

public class OrderData {
	public ArrayList<OrdersList> getOrders() {
		return orders;
	}
	
	ArrayList<OrdersList> orders;
	
	public Contributors getContributions() {
		return contributions;
	}
	
	public Contributors contributions;
	
}
