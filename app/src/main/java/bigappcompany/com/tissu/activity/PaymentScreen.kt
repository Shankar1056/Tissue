package bigappcompany.com.tissu.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import bigappcompany.com.tissu.R
import bigappcompany.com.tissu.Utilz.StaticContacts.timislot
import bigappcompany.com.tissu.Utilz.Utility
import bigappcompany.com.tissu.Utilz.WebServices
import bigappcompany.com.tissu.adapter.PaymentOptionAdapter
import bigappcompany.com.tissu.common.ClsGeneral
import bigappcompany.com.tissu.login.SendOtp
import bigappcompany.com.tissu.model.PaymentMethodModel
import bigappcompany.com.vegan.model.ProductListModel
import com.instamojo.android.Instamojo
import com.instamojo.android.activities.PaymentDetailsActivity
import com.instamojo.android.callbacks.OrderRequestCallBack
import com.instamojo.android.helpers.Constants
import com.instamojo.android.models.Errors
import com.instamojo.android.models.Order
import com.instamojo.android.network.Request
import okhttp3.*
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*


/**
 * @author Samuel Robert <sam></sam>@spotsoon.com>
 * @created on 01 Jul 2017 at 10:30 AM
 */

class PaymentScreen : AppCompatActivity(), View.OnClickListener {
    private var paymentrecycle: RecyclerView? = null
    internal var list = ArrayList<PaymentMethodModel>()
    private var subtotalprice: TextView? = null
    private var subtotal_price: TextView? = null
    private var cart_id: String? = null
    private var paymentmethodid: String? = null
    private var paymenttype = ""
    private var dialog: ProgressDialog? = null
    private var accessToken: String? = null
    private val myCartModellist = ArrayList<ProductListModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment_screen)
        domapping()
    }

    private fun domapping() {
        myCartModellist.clear()

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.title = ""
        val toolbartext = findViewById(R.id.toolbartext) as TextView
        toolbartext.text = "Payment"
        toolbartext.typeface = Utility.font(this@PaymentScreen, "medium")

        toolbar.setNavigationOnClickListener { finish() }

        val cartlayout = findViewById(R.id.cartlayout) as LinearLayout
        cartlayout.visibility = View.INVISIBLE

        findViewById(R.id.paylayout).setOnClickListener(this)
        val summary = findViewById(R.id.summary) as TextView
        val subtotaltext = findViewById(R.id.subtotaltext) as TextView
        val subtotal = findViewById(R.id.subtotal) as TextView
        subtotalprice = findViewById(R.id.subtotalprice) as TextView
        subtotal_price = findViewById(R.id.subtotal_price) as TextView
        val deliveryaddress = findViewById(R.id.deliveryaddress) as TextView
        val addresstype = findViewById(R.id.addresstype) as TextView
        val addressdesc = findViewById(R.id.addressdesc) as TextView
        val paymentmethod = findViewById(R.id.paymentmethod) as TextView
        val pay = findViewById(R.id.pay) as TextView

        summary.typeface = Utility.font(this@PaymentScreen, "bold")
        subtotaltext.typeface = Utility.font(this@PaymentScreen, "bold")
        subtotal.typeface = Utility.font(this@PaymentScreen, "bold")
        subtotalprice!!.typeface = Utility.font(this@PaymentScreen, "bold")
        subtotal_price!!.typeface = Utility.font(this@PaymentScreen, "bold")
        deliveryaddress.typeface = Utility.font(this@PaymentScreen, "bold")
        paymentmethod.typeface = Utility.font(this@PaymentScreen, "bold")
        addresstype.typeface = Utility.font(this@PaymentScreen, "bold")
        addressdesc.typeface = Utility.font(this@PaymentScreen, "medium")
        pay.typeface = Utility.font(this@PaymentScreen, "medium")

        paymentrecycle = findViewById(R.id.paymentrecycle) as RecyclerView
        paymentrecycle!!.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        paymentrecycle!!.layoutManager = mLayoutManager
        //savedaddress_rec.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        paymentrecycle!!.itemAnimator = DefaultItemAnimator()
        paymentrecycle!!.addOnItemTouchListener(RecyclerTouchListener(applicationContext, paymentrecycle, object : RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {

            }

            override fun onLongClick(view: View, position: Int) {

            }
        }))

        try {
            addressdesc.text = intent.getStringExtra("address")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        getpaymentmethodapi()


        dialog = ProgressDialog(this)
        dialog!!.isIndeterminate = true
        dialog!!.setMessage(resources.getString(R.string.pleasewait))
        dialog!!.setCancelable(false)
        Instamojo.setBaseUrl(productionkey)

    }

    private fun getpaymentmethodapi() {
        val a = ClsGeneral.getPreferences(this@PaymentScreen, ClsGeneral.USERID)
        val b = ClsGeneral.getPreferences(this@PaymentScreen, ClsGeneral.SESSIONTOKEN)
        GetPaymentMethod().execute(WebServices.PAYMENTMETHOD + b + "/" + a)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.paylayout -> {
                if (paymenttype.equals("", ignoreCase = true)) {
                    Toast.makeText(this, "Select Payment Method", Toast.LENGTH_SHORT).show()
                    return
                }

                if (paymenttype.equals("offline", ignoreCase = true)) {
                    val session_token = ClsGeneral.getPreferences(this@PaymentScreen, ClsGeneral
                            .SESSIONTOKEN)
                    val address_id = intent.getStringExtra("address_id")
                    DoCod().execute(WebServices.PREPAYMENT, session_token, cart_id, address_id,
                            paymentmethodid, "offline")
                } else if (paymenttype.equals("online", ignoreCase = true)) {

                    val session_token = ClsGeneral.getPreferences(this@PaymentScreen, ClsGeneral
                            .SESSIONTOKEN)
                    val address_id = intent.getStringExtra("address_id")
                    DoCod().execute(WebServices.PREPAYMENT, session_token, cart_id,
                            address_id, paymentmethodid,  "online")


                }
            }
        }

    }

    fun sendpos(mSelectedItem: Int) {
        if (list[mSelectedItem].paymentmethod_type.equals("OFFLINE", ignoreCase = true)) {
            paymenttype = "offline"
            paymentmethodid = list[mSelectedItem].paymentmehtod_id
        }
        if (list[mSelectedItem].paymentmethod_type.equals("ONLINE", ignoreCase = true)) {
            paymenttype = "online"
            paymentmethodid = list[mSelectedItem].paymentmehtod_id
        }

    }

    private inner class GetPaymentMethod : AsyncTask<String, Void, String>() {
        internal var result: String = ""

        override fun onPreExecute() {
            super.onPreExecute()
            //Utility.showDailog(PaymentScreen.this, getResources().getString(R.string.pleasewait),);

        }


        override fun doInBackground(vararg params: String): String {

            try {
                result = Utility.executeHttpGet(params[0])
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            Utility.closeDialog()
            try {
                val jsonObject = JSONObject(s)
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equals("1", ignoreCase = true)) {
                        val jsonObject1 = jsonObject.getJSONObject("data")
                        val jsonObject2 = jsonObject1.getJSONObject("customer_cart_data")
                        cart_id = jsonObject2.getString("cart_id")
                        val jsonArray = jsonObject1.getJSONArray("payment_methods")
                        for (i in 0..jsonArray.length() - 1) {
                            val jo = jsonArray.getJSONObject(i)
                            val paymentmodel = PaymentMethodModel(jo.getString("paymentmehtod_id"), jo.getString("paymentmethods_name"), jo.getString("paymentmethod_type"),
                                    jo.getString("paymentmethod_logo"))
                            list.add(paymentmodel)
                        }

                        val adapter = PaymentOptionAdapter(this@PaymentScreen,
                                list, this@PaymentScreen)
                        paymentrecycle!!.adapter = adapter

                        settext(jsonObject2)
                        settimeslot(jsonObject1.getJSONObject("time_slots"))


                    } else if (jsonObject.getString("status").equals("2", ignoreCase = true)) {
                        ClsGeneral.setPreferences(this@PaymentScreen, ClsGeneral.USERID, "")
                        startActivity(Intent(this@PaymentScreen, SendOtp::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@PaymentScreen, "" + jsonObject.getString("msg"), Toast
                                .LENGTH_SHORT).show()
                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    @Throws(JSONException::class)
    private fun settimeslot(time_slots: JSONObject) {
        val jsonArray = time_slots.getJSONArray("timeslots")
        for (i in 0..jsonArray.length() - 1) {
            val jsonObject = jsonArray.getJSONObject(i)
            timislot.add(jsonObject)
        }


    }

    private inner class DoCod : AsyncTask<String, Void, String>() {
        internal var result: String=""
        internal var nameValuePairs = ArrayList<NameValuePair>()
        internal  var onlineoffline: String=""

        override fun onPreExecute() {
            super.onPreExecute()
            Utility.showDailog(this@PaymentScreen, getResources().getString(R.string.pleasewait));

        }


        override fun doInBackground(vararg params: String): String {
            onlineoffline = params[5]
            nameValuePairs.add(BasicNameValuePair("session_token", params[1]))
            nameValuePairs.add(BasicNameValuePair("cart_id", params[2]))
            nameValuePairs.add(BasicNameValuePair("user_selected_address_id", params[3]))
            nameValuePairs.add(BasicNameValuePair("user_selected_payment_method_id", params[4]))
            nameValuePairs.add(BasicNameValuePair("user_id", ClsGeneral.getPreferences(this@PaymentScreen, ClsGeneral.USERID)))
            try {
                result = Utility.executeHttpPost(params[0], nameValuePairs)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            try {
                Utility.closeDialog()
                val jsonObject = JSONObject(s)
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equals("1", ignoreCase = true)) {
                        val jsonObject1 = jsonObject.getJSONObject("data")
                        val jo = jsonObject1.getJSONObject("order_info")
                        val ci = jsonObject1.getJSONObject("customer_info")
                        val pi = jsonObject1.getJSONObject("payment_info")
                        getorderinfo(jo, ci, pi, onlineoffline)


                    } else if (jsonObject.getString("status").equals("2", ignoreCase = true)) {
                        ClsGeneral.setPreferences(this@PaymentScreen, ClsGeneral.USERID, "")
                        startActivity(Intent(this@PaymentScreen, SendOtp::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@PaymentScreen, "" + jsonObject.getString("msg"), Toast
                                .LENGTH_SHORT).show()
                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }


    @Throws(JSONException::class)
    private fun getorderinfo(jo: JSONObject, ci: JSONObject, pi: JSONObject, msg: String) {
        val send_order_email_to_customer = jo.getString("send_order_email_to_customer")
        val order_id = jo.getString("order_id")
        val order_total = jo.getString("order_total")
        val name = ci.getString("name")
        val email = ci.getString("email")
        val mobile = ci.getString("mobile")

        accessToken = pi.getString("acces_token")
        val redirect_payment_gateway = pi.getString("redirect_payment_gateway")
        if (msg == "offline") {
            startActivity(Intent(this@PaymentScreen, SuccessPage::class.java).putExtra("order_total", order_total))
            finishAffinity()
        } else if (msg.equals("online", ignoreCase = true)) {
            if (redirect_payment_gateway.equals("1", ignoreCase = true)) {
                /*startActivity(new Intent(PaymentScreen.this, PaymentActivity.class).putExtra("accesstoken",
				    acces_token).putExtra("orderid", order_id).putExtra("order_total",order_total)
				    .putExtra("name",name).putExtra("email",email).putExtra("mobile",mobile));*/
                createOrder(accessToken, order_id, name, email, mobile, order_total, "Vegan")

            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }


        }
    }

    @Throws(JSONException::class)
    private fun settext(jsonObject2: JSONObject) {
        subtotalprice!!.text = jsonObject2.getString("cart_total")
        subtotal_price!!.text = jsonObject2.getString("cart_total")
    }

    private fun createOrder(accessToken: String?, transactionID: String, name: String, email: String, mobile: String, order_total: String, vegan: String) {
        //Create the Order
        val order = Order(accessToken!!, transactionID, name, email, mobile, order_total, vegan)

        //set webhook
        order.webhook = "http://your.server.com/webhook/"

        //Validation is successful. Proceed
        dialog!!.show()
        val request = Request(order, OrderRequestCallBack { order, error ->
            runOnUiThread(Runnable {
                dialog!!.dismiss()
                if (error != null) {
                    if (error is Errors.ConnectionError) {
                        showToast("No internet connection")
                    } else if (error is Errors.ServerError) {
                        showToast("Server Error. Try again")
                    } else if (error is Errors.AuthenticationError) {
                        showToast("Access token is invalid or expired. Please Update the token!!")

                    } else {
                        showToast(error.message)
                    }
                    return@Runnable
                }

                startPreCreatedUI(order)
            })
        })

        request.execute()
    }

    private fun showToast(message: String?) {
        runOnUiThread { Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show() }
    }

    private fun startPreCreatedUI(order: Order) {

        val intent = Intent(baseContext, PaymentDetailsActivity::class.java)
        intent.putExtra(Constants.ORDER, order)
        startActivityForResult(intent, Constants.REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST_CODE && data != null) {
            val orderID = data.getStringExtra(Constants.ORDER_ID)
            val transactionID = data.getStringExtra(Constants.TRANSACTION_ID)
            val paymentID = data.getStringExtra(Constants.PAYMENT_ID)

            // Check transactionID, orderID, and orderID for null before using them to check the Payment status.
            if (transactionID != null || paymentID != null) {
                checkPaymentStatus(transactionID, orderID)
            } else {
                showToast("Oops!! Payment was cancelled")
            }
        }
    }

    private fun checkPaymentStatus(transactionID: String?, orderID: String?) {
        if (accessToken == null || transactionID == null && orderID == null) {

            return
        }

        if (dialog != null && !dialog!!.isShowing) {
            dialog!!.show()
        }

        showToast("checking transaction status")
        val client = OkHttpClient()
        val builder = httpURLBuilder
        builder.addPathSegment("status")
        if (transactionID != null) {
            builder.addQueryParameter("transaction_id", transactionID)
        } else {
            builder.addQueryParameter("id", orderID)
        }
        builder.addQueryParameter("env", productionkey.toLowerCase())
        val url = builder.build()

        val request = okhttp3.Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken!!)
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    if (dialog != null && dialog!!.isShowing) {
                        dialog!!.dismiss()
                    }
                    showToast("Failed to fetch the Transaction status")
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val responseString = response.body().string()
                response.body().close()
                var status: String? = null
                var paymentID: String? = null
                var amount: String? = null
                var errorMessage: String? = null

                try {
                    val responseObject = JSONObject(responseString)
                    val payment = responseObject.getJSONArray("payments").getJSONObject(0)
                    status = payment.getString("status")
                    paymentID = payment.getString("id")
                    amount = responseObject.getString("amount")

                } catch (e: JSONException) {
                    errorMessage = "Failed to fetch the Transaction status"
                }

                val finalStatus = status
                val finalErrorMessage = errorMessage
                val finalPaymentID = paymentID
                val finalAmount = amount
                runOnUiThread(Runnable {
                    if (dialog != null && dialog!!.isShowing) {
                        dialog!!.dismiss()
                    }
                    if (finalStatus == null) {
                        showToast(finalErrorMessage)
                        return@Runnable
                    }

                    if (!finalStatus.equals("successful", ignoreCase = true)) {
                        showToast("Transaction still pending")
                        return@Runnable
                    }

                    showToast("Transaction Successful for id - " + finalPaymentID!!)
                    startActivity(Intent(this@PaymentScreen, SuccessPage::class.java).putExtra("order_total", amount))
                    finishAffinity()
                })
            }
        })

    }

    private val httpURLBuilder: HttpUrl.Builder
        get() = HttpUrl.Builder()
                .scheme("https")
                .host("sample-sdk-server.instamojo.com")




    companion object {
        val productionkey = "https://api.instamojo.com/"
    }

}
	

