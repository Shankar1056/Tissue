package bigappcompany.com.tissu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import bigappcompany.com.tissu.R;
import bigappcompany.com.tissu.Utilz.Utility;
import bigappcompany.com.tissu.activity.MyOrder;
import bigappcompany.com.tissu.model.OrdersList;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 22 Sep 2017 at 11:21 AM
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolder> {
	
	private List<OrdersList> saveaddress;
	private Context _context;
	private MyOrder myOrder;
	private static CheckBox lastChecked = null;
	private static int lastCheckedPos = 0;
	
	public class MyViewHolder extends RecyclerView.ViewHolder {
		TextView header_text;
		TextView order_id ;
		TextView order_date ;
		TextView order ;
		TextView totalitem ;
		TextView order_quantity ;
		TextView total;
		TextView totalamount ;
		TextView order_status ;
		TextView order_amounttext ;
		TextView order_amount;
		
		
		
		public MyViewHolder(View view) {
			super(view);
			 header_text = (TextView) view.findViewById(R.id.order_status);
			 order_id = (TextView) view.findViewById(R.id.order_id);
			 order_date = (TextView) view.findViewById(R.id.order_dte);
			 order = (TextView) view.findViewById(R.id.order);
			 totalitem = (TextView) view.findViewById(R.id.totalitem);
			 order_quantity = (TextView) view.findViewById(R.id.order_quantty);
			 total = (TextView) view.findViewById(R.id.total);
			 totalamount = (TextView) view.findViewById(R.id.totalamount);
			 order_status = (TextView) view.findViewById(R.id.order_status);
			 order_amounttext = (TextView) view.findViewById(R.id.order_amountText);
			 order_amount = (TextView) view.findViewById(R.id.order_amount);
			
			
		}
	}
	
	
	public MyOrderAdapter(Context context, List<OrdersList> saveaddress, MyOrder myOrder) {
		this._context = context;
		this.saveaddress = saveaddress;
		this.myOrder = myOrder;
	}
	
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
		    .inflate(R.layout.myorder_row, parent, false);
		
		return new MyViewHolder(itemView);
	}
	
	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int position) {
		OrdersList sa = saveaddress.get(position);
		holder.header_text.setTypeface(Utility.font(_context,"medium"));
		holder.order_id.setTypeface(Utility.font(_context,"regular"));
		holder.order_date.setTypeface(Utility.font(_context,"bold"));
		holder.order.setTypeface(Utility.font(_context,"medium"));
		holder.totalitem.setTypeface(Utility.font(_context,"regular"));
		holder.order_quantity.setTypeface(Utility.font(_context,"regular"));
		holder.total.setTypeface(Utility.font(_context,"regular"));
		holder.totalamount.setTypeface(Utility.font(_context,"regular"));
		holder.order_status.setTypeface(Utility.font(_context,"regular"));
		holder.order_amounttext.setTypeface(Utility.font(_context,"bold"));
		holder.order_amount.setTypeface(Utility.font(_context,"bold"));
		
		
		holder.header_text.setText(sa.getOrder_status_name());
		holder.order_id.setText(sa.getOrders_id());
		holder.order_date.setText(sa.getOrders_created_date());
		try {
			holder.order_quantity.setText(String.valueOf(sa.getOrder_items().size()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		holder.totalamount.setText(sa.getOrder_grand_total());
		holder.order_status.setText(sa.getOrder_status_name());
		holder.order_amount.setText(sa.getOrder_grand_total());
		
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				myOrder.pos(position);
			}
		});
		
	}
	
	@Override
	public int getItemCount() {
		return saveaddress.size();
	}
}

