package bigappcompany.com.tissu.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import bigappcompany.com.tissu.R;
import bigappcompany.com.tissu.common.ClsGeneral;


/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 04 Jul 2017 at 3:56 PM
 */

public class MyProfile extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myprofile);
		
		EditText firstname = (EditText)findViewById(R.id.firstname);
		EditText lastname = (EditText)findViewById(R.id.lastname);
		EditText email = (EditText)findViewById(R.id.email);
		EditText phone = (EditText)findViewById(R.id.phone);
		
		firstname.setText(ClsGeneral.getPreferences(MyProfile.this,ClsGeneral.USERFIRSTNAME));
		email.setText(ClsGeneral.getPreferences(MyProfile.this,ClsGeneral.USEREMAIL));
	//phone.setText(ClsGeneral.getPreferences(MyProfile.this,ClsGeneral.));
	}
}
