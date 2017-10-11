package bigappcompany.com.tissu.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bigappcompany.com.tissu.R;
import bigappcompany.com.tissu.Utilz.Utility;
import bigappcompany.com.tissu.Utilz.WebServices;
import bigappcompany.com.tissu.adapter.MyOrderAdapter;
import bigappcompany.com.tissu.common.ClsGeneral;
import bigappcompany.com.tissu.login.SendOtp;
import bigappcompany.com.tissu.model.MyOrderDescModel;
import bigappcompany.com.tissu.model.OrdersList;
import bigappcompany.com.tissu.model.TotalOrderItems;
import bigappcompany.com.tissu.model.UserOrderModel;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 17 Jun 2017 at 2:03 PM
 */

public class MyOrder extends AppCompatActivity implements View.OnClickListener {
	ArrayList<OrdersList> ordersLists = new ArrayList<>();
	ArrayList<TotalOrderItems> tOI = new ArrayList<>();
	private static ExpandableListView expandableListView;
	private static MyOrderAdapter adapter;
	private RecyclerView myorder_recyc;
	
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myorder);
		domapping();
	}
	
	private void domapping() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setTitle("");
		TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
		TextView ordernow = (TextView) findViewById(R.id.ordernow);
		toolbartext.setText("My Order");
		
		
		expandableListView = (ExpandableListView) findViewById(R.id.simple_expandable_listview);
		expandableListView.setGroupIndicator(null);
		
		LinearLayout cartlayout = (LinearLayout) findViewById(R.id.cartlayout);
		cartlayout.setVisibility(View.INVISIBLE);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		ordernow.setTypeface(Utility.font(MyOrder.this, "medium"));
		toolbartext.setTypeface(Utility.font(MyOrder.this, "medium"));
		
		
		ordernow.setOnClickListener(this);
		
		myorder_recyc = (RecyclerView) findViewById(R.id.myorder_recyc);
		myorder_recyc.setHasFixedSize(true);
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
		myorder_recyc.setLayoutManager(mLayoutManager);
		myorder_recyc.setItemAnimator(new DefaultItemAnimator());
		myorder_recyc.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), myorder_recyc, new RecyclerTouchListener.ClickListener() {
			@Override
			public void onClick(View view, int position) {
				
			}
			
			@Override
			public void onLongClick(View view, int position) {
				
			}
		}));
		
		
		callapi();
		
	}
	
	private void callapi() {
		String s = ClsGeneral.getPreferences(MyOrder.this, ClsGeneral.SESSIONTOKEN);
		String user = ClsGeneral.getPreferences(MyOrder.this, ClsGeneral.USERID);
		if (Utility.isNetworkAvailable(MyOrder.this)) {
			new UserOrder().execute(WebServices.MYORDER + s + "/" + user);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ordernow:
				startActivity(new Intent(MyOrder.this, MainActivity.class));
				finish();
				break;
		}
		
	}
	
	public void pos(int position) {
		
		ArrayList<MyOrderDescModel> modm = new ArrayList<>();
		for (int j = 0; j < ordersLists.get(position).getOrder_items().size(); j++) {
			TotalOrderItems a = ordersLists.get(position).getOrder_items().get(j);
			if (ordersLists.get(position).getOrders_id().equals(a.getOrder_items_order_id())) {
				modm.add(new MyOrderDescModel(a.getOrder_items_name(), a.getOrder_items_qty(),
				    a.getOrder_items_price(), a.getOrders_items_image()));
			}
		}
		
		
		startActivity(new Intent(MyOrder.this, OrderDescription.class).
		    putParcelableArrayListExtra("list", modm).putExtra("id", ordersLists.get(position)
		    .getOrders_id()).putExtra("date", ordersLists.get(position).getOrders_created_date()).putExtra
		    ("status", ordersLists.get(position).getOrder_status_name()).putExtra("total", ordersLists.get
		    (position).getOrder_grand_total()).putExtra("quantity", ordersLists.get(position).getOrder_items().size()));
	}
	
	private class UserOrder extends AsyncTask<String, Void, String> {
		String result;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Utility.showDailog(MyOrder.this, getResources().getString(R.string.pleasewait));
		}
		
		@Override
		protected String doInBackground(String... params) {
			try {
				result = Utility.executeHttpGet(params[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			Utility.closeDialog();
			try {
				UserOrderModel userOrderModel = null;
				
				Gson gson = new Gson();
				try {
					userOrderModel = gson.fromJson(s, UserOrderModel.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if (userOrderModel.getStatus().equalsIgnoreCase("1")) {
					ordersLists = userOrderModel.getData().getOrders();
					/*myOrderAdapter  = new MyOrderAdapter(MyOrder.this,ordersLists,R.layout.myorder_row,MyOrder
					    .this);
					myorder_rec.setAdapter(myOrderAdapter);*/
					
					setvalue(ordersLists);
					
				} else if (userOrderModel.getStatus().equalsIgnoreCase("0")) {
					RelativeLayout myordershow = (RelativeLayout) findViewById(R.id.myordershow);
					myordershow.setVisibility(View.VISIBLE);
				} else if (userOrderModel.getStatus().equalsIgnoreCase("2")) {
					startActivity(new Intent(MyOrder.this, SendOtp.class));
					finish();
				} else {
					Toast.makeText(MyOrder.this, "" + userOrderModel.getMsg(), Toast.LENGTH_SHORT).show();
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		
	}
	
	
	private void setvalue(ArrayList<OrdersList> oL) {
		
		
		HashMap<List<OrdersList>, List<TotalOrderItems>> hashMap = new HashMap<List<OrdersList>, List<TotalOrderItems>>();
		for (int i = 0; i < oL.size(); i++) {
			ArrayList<TotalOrderItems> a = oL.get(i).getOrder_items();
			try {
				for (int j = 0; j < a.size(); j++) {
					
					tOI.add(new TotalOrderItems(a.get(j).getOrder_items_id(), a.get(j).getOrder_items_name(),
					    a.get(j).getOrder_items_price(), a.get(j).getOrder_items_qty(), a.get(j)
					    .getOrders_items_image(), a.get(j).getProduct_avalibel_qty(), a.get(j).getUnit_name(),
					    a.get(j).getUnit_value(), a.get(j).getBusiness_name(), a.get(j)
					    .getOrders_items_discount_type(), a.get(j).getOrders_items_discount(), a.get(j)
					    .getOrder_item_orginal_price(),
					    a.get(j).getOrder_item_discount_price(), a.get(j).getOrder_items_product_id()
					    , a.get(j).getOrder_items_order_id()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			hashMap.put(oL, tOI);
			
		}
		/*adapter = new ExpandableListAdapter(MyOrder.this, oL, hashMap);
		expandableListView.setAdapter(adapter);*/
		adapter = new MyOrderAdapter(MyOrder.this, oL, MyOrder.this);
		myorder_recyc.setAdapter(adapter);
		
		
	}
}
