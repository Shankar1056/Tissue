package bigappcompany.com.tissu.expandablelist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bigappcompany.com.tissu.R;


/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 11 Jul 2017 at 7:24 PM
 */

public class ExpandMain extends AppCompatActivity {
	
	private Toolbar toolbar;
	private static ExpandableListView expandableListView;
	private static ExpandableListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expand_main);
		
		/*toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setTitle("");*/
		
		expandableListView = (ExpandableListView) findViewById(R.id.simple_expandable_listview);
		// Setting group indicator null for custom indicator
		expandableListView.setGroupIndicator(null);
		
		setItems();
	}
	
	void setItems(){
		// Array list for header
		ArrayList<String> header = new ArrayList<String>();
		
		// Array list for child items
		List<String> child1 = new ArrayList<String>();
		List<String> child2 = new ArrayList<String>();
		List<String> child3 = new ArrayList<String>();
		List<String> child4 = new ArrayList<String>();
		
		// Hash map for both header and child
		HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
		
		// Adding headers to list
		for (int i = 1; i < 5; i++) {
			header.add("Group " + i);
		}
		// Adding child data
		for (int i = 1; i < 5; i++) {
			child1.add("Group 1  " + " : Child" + i);
		}
		// Adding child data
		for (int i = 1; i < 5; i++) {
			child2.add("Group 2  " + " : Child" + i);
		}
		// Adding child data
		for (int i = 1; i < 6; i++) {
			child3.add("Group 3  " + " : Child" + i);
		}
		// Adding child data
		for (int i = 1; i < 7; i++) {
			child4.add("Group 4  " + " : Child" + i);
		}
		
		// Adding header and childs to hash map
		hashMap.put(header.get(0), child1);
		hashMap.put(header.get(1), child2);
		hashMap.put(header.get(2), child3);
		hashMap.put(header.get(3), child4);
		
		//adapter = new ExpandableListAdapter(ExpandMain.this, header, hashMap);
		expandableListView.setAdapter(adapter);
		
	}
}
