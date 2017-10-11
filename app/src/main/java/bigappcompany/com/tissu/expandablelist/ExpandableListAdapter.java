package bigappcompany.com.tissu.expandablelist;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bigappcompany.com.tissu.R;
import bigappcompany.com.tissu.Utilz.Utility;
import bigappcompany.com.tissu.activity.MyOrder;
import bigappcompany.com.tissu.model.OrdersList;
import bigappcompany.com.tissu.model.TotalOrderItems;


/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 11 Jul 2017 at 7:23 PM
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
	
	private Context _context;
	//List<String> header; // header titles
	         // Child data in format of header title, child title
	//private HashMap<String, List<String>> child;
	private ArrayList<OrdersList> header;
	//private HashMap<List<OrdersList>, List<TotalOrderItems>> child;
	
	/*public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
		this._context = context;
		this.header = listDataHeader;
		this.child = listChildData;
	}*/
	
	public ExpandableListAdapter(MyOrder context, ArrayList<OrdersList> listDataHeader, HashMap<List<OrdersList>, List<TotalOrderItems>> listChildData) {
		this._context = context;
		this.header = listDataHeader;
		//this.child = listChildData;
	}
	
	@Override
	public int getGroupCount() {
		// Get header size
		return this.header.size();
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		// return children count
		//int a = this.child.get(this.header.get(groupPosition)).size();
		ArrayList<TotalOrderItems> productList = header.get(groupPosition).getOrder_items();
		try {
			return productList.size();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return productList.size();
	}
	
	@Override
	public Object getGroup(int groupPosition) {
		// Get header position
		return this.header.get(groupPosition);
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// This will return the child
		//return childPosition;
		ArrayList<TotalOrderItems> productList = header.get(groupPosition).getOrder_items();
			return productList.get(childPosition);
			
	}
	
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		
		OrdersList ordersList = header.get(groupPosition);
			
		        // Getting header title
		//String headerTitle = (String) getGroup(groupPosition);
		
		         // Inflating header layout and setting text
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.myorder_row, parent, false);
		}
		
		TextView header_text = (TextView) convertView.findViewById(R.id.order_status);
		TextView order_id = (TextView) convertView.findViewById(R.id.order_id);
		TextView order_date = (TextView) convertView.findViewById(R.id.order_dte);
		TextView order = (TextView) convertView.findViewById(R.id.order);
		TextView totalitem = (TextView) convertView.findViewById(R.id.totalitem);
		TextView order_quantity = (TextView) convertView.findViewById(R.id.order_quantty);
		TextView total = (TextView) convertView.findViewById(R.id.total);
		TextView totalamount = (TextView) convertView.findViewById(R.id.totalamount);
		TextView order_status = (TextView) convertView.findViewById(R.id.order_status);
		TextView order_amounttext = (TextView) convertView.findViewById(R.id.order_amountText);
		TextView order_amount = (TextView) convertView.findViewById(R.id.order_amount);
		
		header_text.setTypeface(Utility.font(_context,"medium"));
		order_id.setTypeface(Utility.font(_context,"regular"));
		order_date.setTypeface(Utility.font(_context,"bold"));
		order.setTypeface(Utility.font(_context,"medium"));
		totalitem.setTypeface(Utility.font(_context,"regular"));
		order_quantity.setTypeface(Utility.font(_context,"regular"));
		total.setTypeface(Utility.font(_context,"regular"));
		totalamount.setTypeface(Utility.font(_context,"regular"));
		order_status.setTypeface(Utility.font(_context,"regular"));
		order_amounttext.setTypeface(Utility.font(_context,"bold"));
		order_amount.setTypeface(Utility.font(_context,"bold"));
		
		
		header_text.setText(ordersList.getOrder_status_name());
		order_id.setText(ordersList.getOrders_id());
		order_date.setText(ordersList.getOrders_created_date());
		try {
			order_quantity.setText(String.valueOf(ordersList.getOrder_items().size()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		totalamount.setText(ordersList.getOrder_grand_total());
		order_status.setText(ordersList.getOrder_status_name());
		order_amount.setText(ordersList.getOrder_grand_total());
		
		// If group is expanded then change the text into bold and change the
		// icon
		if (isExpanded) {
			header_text.setTypeface(null, Typeface.BOLD);
			header_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_down, 0);
		} else {
			// If group is not expanded then change the text back into normal
			// and change the icon
			
			header_text.setTypeface(null, Typeface.NORMAL);
			header_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_down, 0);
		}
		
		return convertView;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		try {
			//ArrayList<TotalOrderItems> productList = header.get(groupPosition).getOrder_items();
			TotalOrderItems totalOrderItems = (TotalOrderItems) getChild(groupPosition, childPosition);
			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.childs, parent, false);
			}
				
			
			TextView productname = (TextView) convertView.findViewById(R.id.productname);
			TextView size = (TextView) convertView.findViewById(R.id.size);
			TextView size_value = (TextView) convertView.findViewById(R.id.size_value);
			TextView quantity = (TextView) convertView.findViewById(R.id.quantity);
			TextView quantity_value = (TextView) convertView.findViewById(R.id.quantity_value);
			TextView subtotal = (TextView) convertView.findViewById(R.id.subtotal);
			TextView subtotal_value = (TextView) convertView.findViewById(R.id.subtotal_value);
			
			productname.setText(totalOrderItems.getOrder_items_name());
			size_value.setText(totalOrderItems.getOrder_items_qty());
			quantity_value.setText(totalOrderItems.getOrder_items_qty());
			subtotal_value.setText(totalOrderItems.getOrder_items_price());
			
			productname.setTypeface(Utility.font(_context,"medium"));
			size.setTypeface(Utility.font(_context,"regular"));
			size_value.setTypeface(Utility.font(_context,"regular"));
			quantity.setTypeface(Utility.font(_context,"regular"));
			quantity_value.setTypeface(Utility.font(_context,"regular"));
			subtotal.setTypeface(Utility.font(_context,"bold"));
			subtotal_value.setTypeface(Utility.font(_context,"regular"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return convertView;
	}
	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
}