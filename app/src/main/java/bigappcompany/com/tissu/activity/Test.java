package bigappcompany.com.tissu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import bigappcompany.com.tissu.R;

/**
 * @author Shankar
 * @created on 18 Sep 2017 at 11:34 AM
 */

public class Test extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

	private final String[] generations = {"Gen2", "Gen3", "Gen4", "Gen5", "Gen6", "Gen7"};
	
		@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
			Spinner spin = (Spinner) findViewById(R.id.genchoice);
			spin.setOnItemSelectedListener(this);
			ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, generations);
			aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin.setAdapter(aa);
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
			
			case 5:
				Intent intent6 = new Intent(this, MainActivity.class);
				startActivity(intent6);
				break;
			
		}
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
}

