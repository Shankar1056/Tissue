package bigappcompany.com.tissu.dummy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.SeekBar;
import android.widget.Toast;

import bigappcompany.com.tissu.R;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 19 Sep 2017 at 3:06 PM
 */

public class ListView extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
	SeekBar seekBar1;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab2);
		
		seekBar1=(SeekBar)findViewById(R.id.seekBar1);
		seekBar1.setOnSeekBarChangeListener(this);
		
	}
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
	                              boolean fromUser) {
		Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
