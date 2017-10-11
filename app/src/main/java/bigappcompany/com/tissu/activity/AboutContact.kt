package bigappcompany.com.tissu.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import bigappcompany.com.tissu.R
import bigappcompany.com.tissu.Utilz.Utility


/**
 * @author Samuel Robert <sam></sam>@spotsoon.com>
 * @created on 03 Jul 2017 at 1:18 PM
 */

class AboutContact : AppCompatActivity() {
    val MY_PERMISSIONS_REQUEST_CALL_PHONE = 11;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.getStringExtra("abtcontact").equals("contact")) {
            setContentView(R.layout.contact_us)
        } else {
            setContentView(R.layout.about_us)
        }

        requestpermission()
        domapping()
    }

    private fun requestpermission() {
        if (ContextCompat.checkSelfPermission(this@AboutContact,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this@AboutContact,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    MY_PERMISSIONS_REQUEST_CALL_PHONE)

        } else {
            //You already have permission

        }
    }

    private fun domapping() {
        val toolbartext = findViewById(R.id.toolbartext) as TextView
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.title = ""
        toolbar.setNavigationIcon(R.mipmap.back_black)
        toolbar.setNavigationOnClickListener { finish() }

        if (intent.getStringExtra("abtcontact").equals("contact")) {
            toolbartext.text = "Contact Us"
            val contacttext = findViewById(R.id.contacttext) as TextView
            val callus = findViewById(R.id.callus) as TextView
            val emailus = findViewById(R.id.emailus) as TextView
            contacttext.typeface = Utility.font(this@AboutContact, "medium")
            callus.typeface = Utility.font(this@AboutContact, "medium")
            emailus.typeface = Utility.font(this@AboutContact, "medium")


            callus.setOnClickListener({
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:8002877277")
                startActivity(callIntent)
                try {
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }

            })

            emailus.setOnClickListener(View.OnClickListener {
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.type = "text/plain"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, "dayashankargupta86@gmail.com")
                startActivity(emailIntent)
            })
        } else {
            toolbartext.text = "About Us"
        }

        toolbartext.typeface = Utility.font(this@AboutContact, "medium")
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CALL_PHONE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }
}
