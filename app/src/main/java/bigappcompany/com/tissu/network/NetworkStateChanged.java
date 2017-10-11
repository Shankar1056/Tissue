package bigappcompany.com.tissu.network;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 07 Sep 2017 at 11:34 AM
 */

public class NetworkStateChanged {
	
	private boolean mIsInternetConnected;
	
	public NetworkStateChanged(boolean isInternetConnected) {
		this.mIsInternetConnected = isInternetConnected;
	}
	
	public boolean isInternetConnected() {
		return this.mIsInternetConnected;
	}
}
