package bigappcompany.com.tissu.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 15 Jun 2017 at 2:00 PM
 */

public abstract class SmsBroadcastReceiver extends BroadcastReceiver {
	
	/* Service to auto matically read the SMS for verifying the account of the user */
	final SmsManager sms = SmsManager.getDefault();
	
	public void onReceive(Context context, Intent intent) {
		
		// Retrieves a bigappcompany.com.vegan.map of extended data from the intent.
		final Bundle bundle = intent.getExtras();
		
		try {
			
			if (bundle != null) {
				
				final Object[] pdusObj = (Object[]) bundle.get("pdus");
				
				for (int i = 0; i < pdusObj.length; i++) {
					
					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage.getDisplayOriginatingAddress();
					
					String senderNum = phoneNumber;
					String message = currentMessage.getDisplayMessageBody();
					
					if(message != null) {
						int length = message.length();
						//message = message.substring(length - 4, length);
						Log.e("Message", "Hello" + message);
						onSmsReceived(message);
						abortBroadcast();
						//  Toast.makeText(context, " broadacast message "+message, Toast.LENGTH_SHORT).show();
					}
				} // end for loop
			} // bundle is null
			
		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" + e);
			
		}
	}
	
	protected abstract void onSmsReceived(String s);
	
}
