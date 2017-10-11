package bigappcompany.com.tissu.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import bigappcompany.com.tissu.R
import bigappcompany.com.tissu.Utilz.Download_web
import bigappcompany.com.tissu.Utilz.OnTaskCompleted
import bigappcompany.com.tissu.Utilz.Utility
import bigappcompany.com.tissu.Utilz.WebServices
import bigappcompany.com.tissu.activity.MainActivity
import bigappcompany.com.tissu.common.ClsGeneral
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import org.json.JSONObject
import java.util.*

/**
 * @author Shankar
 * @created on 09 Sep 2017 at 7:20 PM
 */
class SignUp : AppCompatActivity(), View.OnClickListener {


    private var input_name: EditText? = null
    private var input_email: EditText? = null
    private var submit: Button? = null
    private var TAG: String? = null
    private var isUntenetConnected: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)



        mapping()
    }

    private fun mapping() {

        input_name = findViewById(R.id.input_name) as EditText
        input_email = findViewById(R.id.input_email) as EditText
        submit = findViewById(R.id.submit) as Button
        submit!!.setOnClickListener (this)

    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0.id) {
                R.id.submit -> {

                    if (input_name!!.text.toString().trim{ it <= ' ' }.equals("",ignoreCase = true))
                    {
                        Toast.makeText(this@SignUp,"Enter your name",Toast.LENGTH_SHORT).show()
                        return
                    }
                    if (input_email!!.text.toString().trim{it <=' '}.equals("",ignoreCase = true))
                    {
                        Toast.makeText(this@SignUp,"Enter your email",Toast.LENGTH_SHORT).show()
                        return
                    }
                    if (!Utility.isValidEmail1(input_email!!.text))
                    {
                        Toast.makeText(this@SignUp,"Enter correct email",Toast.LENGTH_SHORT).show()
                        return
                    }
                    else {
                        if (Utility.isNetworkAvailable(this@SignUp))
                            run { submitdetails(input_name!!.text, input_email!!.text); }
                        else {
                            Toast.makeText(this@SignUp, resources.getString(R.string.nointernetconnection), Toast
                                    .LENGTH_SHORT).show()
                        }
                    }

                }

            }
        }
    }

    private fun submitdetails(name: Editable?, email: Editable?) {
        Utility.showDailog(this@SignUp,"Please wait...")


        val nameValuePairs = ArrayList<NameValuePair>()
        val web = Download_web(this, OnTaskCompleted { response ->
            try {
                Utility.closeDialog()
                val jsonObject = JSONObject(response)
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equals("1", ignoreCase = true)) {
                        ClsGeneral.setPreferences(this@SignUp,ClsGeneral.USEREMAIL,email.toString())
                        ClsGeneral.setPreferences(this@SignUp,ClsGeneral.USERFIRSTNAME,name.toString())
                        startActivity(Intent(this@SignUp, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        finish()

                        Utility.closeDialog()
                    } else if (jsonObject.getString("status").equals("2", ignoreCase = true)) {
                        ClsGeneral.setPreferences(this@SignUp, ClsGeneral.USERID, "")
                        startActivity(Intent(this@SignUp, SendOtp::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@SignUp, "" + jsonObject.getString("msg"), Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            Log.e("Response : ", response)
        })
        nameValuePairs.add(BasicNameValuePair("first_name", name.toString()))
        nameValuePairs.add(BasicNameValuePair("email", email.toString()))
        nameValuePairs.add(BasicNameValuePair("session_token", ClsGeneral.getPreferences(this@SignUp, ClsGeneral.SESSIONTOKEN)))
        nameValuePairs.add(BasicNameValuePair("user_id", ClsGeneral.getPreferences(this@SignUp, ClsGeneral.USERID)))

        web.setReqType("post")
        web.setData(nameValuePairs)
        web.execute(WebServices.SIGNUP)
    }


}