package bigappcompany.com.tissu.dummy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import bigappcompany.com.tissu.R;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 19 Sep 2017 at 3:09 PM
 */

public class MainActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
}
