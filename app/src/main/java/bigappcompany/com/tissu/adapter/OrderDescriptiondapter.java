package bigappcompany.com.tissu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bigappcompany.com.tissu.R;
import bigappcompany.com.tissu.Utilz.Utility;
import bigappcompany.com.tissu.model.MyOrderDescModel;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 29 Jun 2017 at 11:45 AM
 */

public class OrderDescriptiondapter extends RecyclerView.Adapter<OrderDescriptiondapter.MyViewHolder> {
	
	private List<MyOrderDescModel> saveaddress;
	private Context _context;
	
	public class MyViewHolder extends RecyclerView.ViewHolder {
		TextView productname;
		TextView size;
		TextView size_value;
		TextView quantity;
		TextView quantity_value;
		TextView subtotal;
		TextView subtotal_value;
		ImageView productimage;
		View viewline;
		
		
		public MyViewHolder(View view) {
			super(view);
			productname = (TextView) view.findViewById(R.id.productname);
			size = (TextView) view.findViewById(R.id.size);
			size_value = (TextView) view.findViewById(R.id.size_value);
			quantity = (TextView) view.findViewById(R.id.quantity);
			quantity_value = (TextView) view.findViewById(R.id.quantity_value);
			subtotal = (TextView) view.findViewById(R.id.subtotal);
			subtotal_value = (TextView) view.findViewById(R.id.subtotal_value);
			productimage = (ImageView) view.findViewById(R.id.productimage);
			viewline = (View) view.findViewById(R.id.viewline);
			
		}
	}
	
	
	public OrderDescriptiondapter(Context context, List<MyOrderDescModel> saveaddress) {
		this._context = context;
		this.saveaddress = saveaddress;
	}
	
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
		    .inflate(R.layout.childs, parent, false);
		
		return new MyViewHolder(itemView);
	}
	
	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int position) {
		MyOrderDescModel sa = saveaddress.get(position);
		if (saveaddress.size() > 1) {
			holder.viewline.setVisibility(View.VISIBLE);
		}
		if (saveaddress.size()==position+1)
		{
			holder.viewline.setVisibility(View.GONE);
		}
		holder.productname.setTypeface(Utility.font(_context, "medium"));
		holder.size.setTypeface(Utility.font(_context, "regular"));
		holder.size_value.setTypeface(Utility.font(_context, "regular"));
		holder.quantity.setTypeface(Utility.font(_context, "regular"));
		holder.quantity_value.setTypeface(Utility.font(_context, "regular"));
		holder.subtotal.setTypeface(Utility.font(_context, "bold"));
		holder.subtotal_value.setTypeface(Utility.font(_context, "regular"));
		
		holder.productname.setText(sa.getName());
		holder.quantity_value.setText(sa.getQuantity());
		holder.subtotal_value.setText(sa.getPrice());
		Picasso.with(_context).load(sa.getImage()).into(holder.productimage);
	}
	
	@Override
	public int getItemCount() {
		return saveaddress.size();
	}
}
