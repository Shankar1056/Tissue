package bigappcompany.com.tissu.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import bigappcompany.com.tissu.R
import bigappcompany.com.tissu.Utilz.Download_web
import bigappcompany.com.tissu.Utilz.OnTaskCompleted
import bigappcompany.com.tissu.Utilz.Utility
import bigappcompany.com.tissu.Utilz.WebServices
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 * @author Shankar
 * @created on 08 Sep 2017 at 5:30 PM
 */

class SendOtp : AppCompatActivity(), View.OnClickListener {

    private var enterotp: EditText? = null
    private val TAG = SendOtp::class.java.simpleName
    private var isUntenetConnected: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sendotp)


        if (Build.VERSION.SDK_INT >= 23) {
            checkLocationPermission()
        }
        mapping()
    }

    private fun mapping() {
        val otptext = findViewById(R.id.otptext) as TextView
        enterotp = findViewById(R.id.enterotp) as EditText
        val submitotp = findViewById(R.id.submitotp) as Button
      //  Toast.makeText(this, "" + otptext.text, Toast.LENGTH_SHORT).show()

        otptext.typeface = Utility.font(this@SendOtp, "bold")

        submitotp.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {

                R.id.submitotp -> {
                    if (enterotp!!.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                        Toast.makeText(this, "Enter your number", Toast.LENGTH_SHORT).show()
                        return
                    } else {
                          if (Utility.isNetworkAvailable(this@SendOtp))
                            run { getdata(enterotp!!.text) }
                        else
                        {
                            Toast.makeText(this@SendOtp,resources.getString(R.string.nointernetconnection),Toast
                                    .LENGTH_SHORT).show()
                        }

                    }
                }
            }
        }
    }

    private fun getdata(number: Editable) {
        Utility.showDailog(this@SendOtp,"Please wait...")


        val nameValuePairs = ArrayList<NameValuePair>()
        val web = Download_web(this, OnTaskCompleted { response ->
            try {
                Utility.closeDialog()
                val jsonObject = JSONObject(response)
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equals("1", ignoreCase = true)) {
                        val jsonObject1 = jsonObject.getJSONObject("data")
                        savevalue(jsonObject1)
                        Utility.closeDialog()
                    } else {
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            Log.e("Response : ", response)
        })
        nameValuePairs.add(BasicNameValuePair("mobile", number.toString()))
        nameValuePairs.add(BasicNameValuePair("push_token", ""))

        web.setReqType("post")
        web.setData(nameValuePairs)
        web.execute(WebServices.OTP)
    }

    @Throws(JSONException::class)
    private fun savevalue(otpData: JSONObject) {
            val intent = Intent(this@SendOtp, GetOtp::class.java)
            intent.putExtra("otp",otpData.optString("otp"))
            intent.putExtra("session",otpData.optString("session_token"))
            intent.putExtra("user_exist",otpData.optString("user_exist"))
            intent.putExtra("user_first_name",otpData.optString("user_first_name"))
            intent.putExtra("user_email",otpData.optString("user_email"))
            intent.putExtra("user_id",otpData.optString("user_id"))
            startActivity(intent)
            finish()


    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECEIVE_SMS)) {

                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.RECEIVE_SMS),
                        MY_PERMISSIONS_REQUEST_LOCATION)


            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.RECEIVE_SMS),
                        MY_PERMISSIONS_REQUEST_LOCATION)
            }

        } else {

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {

                        /*if (mGoogleApiClient == null) {
							buildGoogleApiClient();
						}
						mMap.setMyLocationEnabled(true);*/
                    }

                } else {

                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    companion object {

        private val TAG = SendOtp::class.java.simpleName

        private val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }



}
