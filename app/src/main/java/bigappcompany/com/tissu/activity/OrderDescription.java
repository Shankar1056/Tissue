package bigappcompany.com.tissu.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bigappcompany.com.tissu.R;
import bigappcompany.com.tissu.Utilz.Utility;
import bigappcompany.com.tissu.adapter.OrderDescriptiondapter;
import bigappcompany.com.tissu.model.MyOrderDescModel;

import static bigappcompany.com.tissu.R.id.order_dte;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 21 Sep 2017 at 7:33 PM
 */

public class OrderDescription extends AppCompatActivity {
	private RecyclerView orderdesc;
	ArrayList<MyOrderDescModel> myOrderDescModels = new ArrayList<>();
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_desc);
		domapping();
	}
	
	private void domapping() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setTitle("");
		TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
		toolbartext.setText("Order Description");
		LinearLayout cartlayout = (LinearLayout)findViewById(R.id.cartlayout);
		cartlayout.setVisibility(View.INVISIBLE);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		TextView header_text = (TextView) findViewById(R.id.order_status);
		TextView order_id = (TextView) findViewById(R.id.order_id);
		TextView order_date = (TextView) findViewById(order_dte);
		TextView order = (TextView) findViewById(R.id.order);
		TextView totalitem = (TextView) findViewById(R.id.totalitem);
		TextView order_quantity = (TextView) findViewById(R.id.order_quantty);
		TextView total = (TextView) findViewById(R.id.total);
		TextView totalamount = (TextView) findViewById(R.id.totalamount);
		TextView order_status = (TextView) findViewById(R.id.order_status);
		TextView order_amounttext = (TextView) findViewById(R.id.order_amountText);
		TextView order_amount = (TextView) findViewById(R.id.order_amount);
		
		header_text.setTypeface(Utility.font(OrderDescription.this,"medium"));
		order_id.setTypeface(Utility.font(OrderDescription.this,"regular"));
		order_date.setTypeface(Utility.font(OrderDescription.this,"bold"));
		order.setTypeface(Utility.font(OrderDescription.this,"medium"));
		totalitem.setTypeface(Utility.font(OrderDescription.this,"regular"));
		order_quantity.setTypeface(Utility.font(OrderDescription.this,"regular"));
		total.setTypeface(Utility.font(OrderDescription.this,"regular"));
		totalamount.setTypeface(Utility.font(OrderDescription.this,"regular"));
		order_status.setTypeface(Utility.font(OrderDescription.this,"regular"));
		order_amounttext.setTypeface(Utility.font(OrderDescription.this,"bold"));
		order_amount.setTypeface(Utility.font(OrderDescription.this,"bold"));
		
		String a = getIntent().getStringExtra("total");
		order_id.setText(getIntent().getStringExtra("id"));
		order_date.setText(getIntent().getStringExtra("date"));
		order_quantity.setText(""+getIntent().getIntExtra("quantity",0));
		order_amount.setText(getIntent().getStringExtra("total"));
		totalamount.setText(getIntent().getStringExtra("total"));
		order_status.setText(getIntent().getStringExtra("status"));
		
		
		orderdesc = (RecyclerView)findViewById(R.id.orderdesc);
		orderdesc.setHasFixedSize(true);
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
		orderdesc.setLayoutManager(mLayoutManager);
		orderdesc.setItemAnimator(new DefaultItemAnimator());
		orderdesc.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), orderdesc, new RecyclerTouchListener.ClickListener() {
			@Override
			public void onClick(View view, int position) {
				
			}
			
			@Override
			public void onLongClick(View view, int position) {
				
			}
		}));
		
		myOrderDescModels = getIntent().getParcelableArrayListExtra("list");
		
		OrderDescriptiondapter orderDescriptiondapter = new OrderDescriptiondapter(this,myOrderDescModels);
		orderdesc.setAdapter(orderDescriptiondapter);
	}
}
