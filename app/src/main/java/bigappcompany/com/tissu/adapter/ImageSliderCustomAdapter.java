package bigappcompany.com.tissu.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import bigappcompany.com.tissu.R;
import bigappcompany.com.tissu.activity.ProductDescription;
import bigappcompany.com.tissu.model.DescriptinSliderModel;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 20 Sep 2017 at 11:43 AM
 */

public class ImageSliderCustomAdapter extends PagerAdapter {
	
	Context mContext;
	ProductDescription activity;
	LayoutInflater mLayoutInflater;
	ArrayList<DescriptinSliderModel> models;
	Timer timer;
	int page = 1;
	private boolean isDynamic = false;
	private ViewPager viewPager;
	
	@Override
	public int getCount() {
		return models.size();
	}
	
	public void setData(ArrayList<DescriptinSliderModel> res, Context context, ProductDescription activity) {
		this.models = res;
		this.mContext = context;
		this.activity = activity;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void isDynamic(boolean isAnimatable) {
		this.isDynamic = isAnimatable;
	}
	
	public void setVP(ViewPager viewPager) {
		this.viewPager = viewPager;
	}
	
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
		
		final ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager);
		try {
			
			Picasso.with(activity).load(models.get(position).getProduct_image()).into(imageView);
			
			
		} catch (Exception e) {
			Log.e("image Exception", e.getMessage());
		}
		
		container.addView(itemView);
		if (isDynamic) {
			if (timer == null) {
				pageSwitcher(3);
			}
		}
		
		itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				activity.sendpos(position);
			}
		});
		
		return itemView;
		
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((LinearLayout) object);
	}
	
	public void pageSwitcher(int seconds) {
		timer = new Timer(); // At this line a new Thread will be created
		timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
		// in
		// milliseconds
	}
	
	public void stopTimer() {
		if (timer != null)
			timer.cancel();
	}
	
	// this is an inner class...
	class RemindTask extends TimerTask {
		
		@Override
		public void run() {
			
			// As the TimerTask run on a seprate thread from UI thread we have
			// to call runOnUiThread to do work on UI thread.
			activity.runOnUiThread(new Runnable() {
				public void run() {
					viewPager.setCurrentItem(++page % models.size());
				}
			});
			
		}
	}
	
}

