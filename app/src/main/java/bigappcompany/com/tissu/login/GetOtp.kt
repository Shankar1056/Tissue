package bigappcompany.com.tissu.login

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import bigappcompany.com.tissu.R
import bigappcompany.com.tissu.activity.MainActivity
import bigappcompany.com.tissu.common.ClsGeneral

/**
 * @author shankar
 * @created on 08 Sep 2017 at 7:19 PM
 */
class GetOtp : AppCompatActivity(), View.OnClickListener {


    private var input_otp: EditText? = null
    private var readsms: SmsBroadcastReceiver? = null
    private var intentFilter: IntentFilter? = null
    private var receivedotp: String? = null
    private var submitotp: Button? = null
    private var session : String? = null
    private var user_exist : String? = null
    private var user_first_name : String? = null
    private var user_email : String? = null
    private var user_id : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.getotp)

        receivedotp = intent.getStringExtra("otp")
        session = intent.getStringExtra("session")
        user_exist = intent.getStringExtra("user_exist")
        user_first_name = intent.getStringExtra("user_first_name")
        user_email = intent.getStringExtra("user_email")
        user_id = intent.getStringExtra("user_id")

        input_otp = findViewById(R.id.input_otp) as EditText
        submitotp = findViewById(R.id.submitotp) as Button

        submitotp!!.setOnClickListener(this)


        readsms = object : SmsBroadcastReceiver() {
            override fun onSmsReceived(s: String?) {
                if (s != null && s.length > 0) {
                    if (s.contains("SpotSoon")) {
                        try {
                            val splitmsg = s.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            //input_email!!.text = (splitmsg[0])
                            input_otp!!.setText(splitmsg[0])
                        } catch (e: Exception) {
                            e.printStackTrace()

                        }
                    }
                }
            }
        }



        intentFilter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        intentFilter!!.priority = 1000


        input_otp!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {

                if (input_otp!!.text.toString().length == 5) {

                    if (receivedotp!!.equals(input_otp!!.text.toString().trim { it <= ' ' }, ignoreCase = true)) {
                        if (user_email .equals("", ignoreCase = true)) {
                            ClsGeneral.setPreferences(this@GetOtp, ClsGeneral.USERID, user_id)
                            ClsGeneral.setPreferences(this@GetOtp, ClsGeneral.SESSIONTOKEN, session)
                            ClsGeneral.setPreferences(this@GetOtp, ClsGeneral.USEREXIST, user_exist)


                        } else {
                           // getsavedlocation()
                        }

                    } else {
                        Toast.makeText(this@GetOtp, "Enter Correct OTP", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0.id) {
                R.id.submitotp ->

                    if (input_otp!!.text.toString().length == 5) {


                        if (receivedotp!!.equals(input_otp!!.text.toString().trim { it <= ' ' }, ignoreCase = true)) {
                            ClsGeneral.setPreferences(this@GetOtp, ClsGeneral.USERID, user_id)
                            ClsGeneral.setPreferences(this@GetOtp, ClsGeneral.SESSIONTOKEN, session)
                            ClsGeneral.setPreferences(this@GetOtp, ClsGeneral.USEREXIST, user_exist)
                            ClsGeneral.setPreferences(this@GetOtp, ClsGeneral.USEREMAIL, user_email)
                            ClsGeneral.setPreferences(this@GetOtp, ClsGeneral.USERFIRSTNAME, user_first_name)

                            if (user_email.equals("", ignoreCase = true)) {

                                startActivity(Intent(this@GetOtp, SignUp::class.java))
                                finish()
                            } else {
                                ClsGeneral.setPreferences(this@GetOtp, ClsGeneral.USERID, user_id)

                                startActivity(Intent(this@GetOtp, MainActivity::class.java).putExtra("classname",
                                        "send_get_otp").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                finish()



                            }

                        } else {
                            Toast.makeText(this@GetOtp, "Enter Correct OTP", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }



    override fun onResume() {
        super.onResume()
        if (intentFilter != null) {
            this.registerReceiver(readsms, intentFilter)
        }

    }

    override fun onPause() {
        super.onPause()

        if (intentFilter != null) {
            this.unregisterReceiver(readsms)
        }
    }

}