package bigappcompany.com.tissu.common;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Admin on 30-01-2017.
 */

public class MyApplication extends Application {
//https://infinum.co/the-capsized-eight/top-5-android-libraries-every-android-developer-should-know-about
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        
    }
    
    
}