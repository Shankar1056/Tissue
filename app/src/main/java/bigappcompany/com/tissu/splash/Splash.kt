package bigappcompany.com.tissu.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import bigappcompany.com.tissu.R
import bigappcompany.com.tissu.activity.MainActivity
import bigappcompany.com.tissu.common.ClsGeneral
import bigappcompany.com.tissu.login.SendOtp
import bigappcompany.com.tissu.login.SignUp

/**
 * @author Shankar
 * @created on 11 Sep 2017 at 2:28 PM
 */

 class Splash : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        ClsGeneral.setPreferences(this@Splash, ClsGeneral.ISLOADPRODUCT,"false")
        when {
            ClsGeneral.getPreferences(this@Splash,ClsGeneral.USERID)!!.toString().trim{ it <= ' '}.equals("",ignoreCase =
            true) -> {
                startActivity(Intent(this@Splash,SendOtp::class.java))
                finish()
            }
            ClsGeneral.getPreferences(this@Splash,ClsGeneral.USEREMAIL).toString().trim{ it <= ' '}.equals("",
                    ignoreCase = true) -> {
                startActivity(Intent(this@Splash, SignUp::class.java))
                finish()
            }
            else -> {
                startActivity(Intent(this@Splash, MainActivity::class.java))
                finish()
            }
        }
    }
}