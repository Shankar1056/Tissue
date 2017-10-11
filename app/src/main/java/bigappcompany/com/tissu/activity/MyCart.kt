package bigappcompany.com.tissu.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import bigappcompany.com.tissu.R
import bigappcompany.com.tissu.Utilz.Download_web
import bigappcompany.com.tissu.Utilz.OnTaskCompleted
import bigappcompany.com.tissu.Utilz.Utility
import bigappcompany.com.tissu.Utilz.WebServices
import bigappcompany.com.tissu.adapter.MyCartAdapter
import bigappcompany.com.tissu.common.ClsGeneral
import bigappcompany.com.tissu.login.SendOtp
import bigappcompany.com.tissu.model.MyCartModel
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import org.json.JSONObject

/**
 * @author Shankar
 * @created on 18 Sep 2017 at 11:20 AM
 */

 class MyCart : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.checkout -> {
                ClsGeneral.setPreferences(this@MyCart, "from", "cart")
                startActivity(Intent(this@MyCart, MyAddress::class.java))
            }
            R.id.mycart ->
                startActivity(Intent(this@MyCart, MyCart::class.java))
        }
    }

    private var rec_cartlist: RecyclerView? = null
    private var myordershow: RelativeLayout? = null
    private var ordernow: TextView? = null
    private var checkout: LinearLayout? = null
    private var checkouttext: TextView? = null
    private var checkoutprice: TextView? = null
    private var cartcount: TextView? = null
    private var snackbar: Snackbar? = null
    private var coordinatorLayout: CoordinatorLayout? = null
    private var cartlist = ArrayList<MyCartModel>()
    private var myAdapter: MyCartAdapter? = null
    private var cart_id: String? = null
    private var pos: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mycart)



        domapping()

    }

    private fun domapping() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.title = ""
        val toolbartext = findViewById(R.id.toolbartext) as TextView
        findViewById(R.id.checkout).setOnClickListener(this)
        rec_cartlist = findViewById(R.id.rec_cartlist) as RecyclerView?
        rec_cartlist!!.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        rec_cartlist!!.setLayoutManager(mLayoutManager)
        rec_cartlist!!.setItemAnimator(DefaultItemAnimator())
        rec_cartlist!!.addOnItemTouchListener(RecyclerTouchListener(applicationContext, rec_cartlist, object :
                RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {

            }

            override fun onLongClick(view: View, position: Int) {

            }
        }))

        coordinatorLayout = findViewById(R.id.coordinatorLayout) as CoordinatorLayout
        myordershow = findViewById(R.id.myordershow) as RelativeLayout?
        ordernow = findViewById(R.id.ordernow) as TextView?
        checkout = findViewById(R.id.checkout) as LinearLayout?
        checkouttext = findViewById(R.id.checkouttext) as TextView?
        checkoutprice = findViewById(R.id.checkoutprice) as TextView?
        cartcount = findViewById(R.id.cartcount) as TextView?

        ordernow!!.typeface = Utility.font(this@MyCart, "medium")
        checkouttext!!.typeface = Utility.font(this@MyCart, "medium")
        checkoutprice!!.typeface = Utility.font(this@MyCart, "medium")
        toolbartext!!.typeface = Utility.font(this@MyCart, "medium")

        toolbartext.text = "MY CART"

        if (cartlist!!.size == 0) {
            try {
                if (Utility.isNetworkAvailable(this@MyCart)) {

                    Utility.showDailog(this@MyCart, "Please wait")
                    getCartapi()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            showsnakebar()
        }

        ordernow!!.setOnClickListener {
            startActivity(Intent(this@MyCart, MainActivity::class.java))
        }

        toolbar.setNavigationOnClickListener { finish() }

    }

    override fun onResume() {
        super.onResume()
        cartcount!!.setText(ClsGeneral.getPreferences(this@MyCart, ClsGeneral.CARTCOUNT))
        if (Integer.parseInt(cartcount!!.getText().toString()) >= 1) {
            checkout!!.setVisibility(View.VISIBLE)
        }
    }

    private fun getCartapi() {
        val web = Download_web(this, OnTaskCompleted { response ->
            try {
                Utility.closeDialog()

                if (snackbar != null && snackbar!!.isShown()) {
                    snackbar!!.dismiss()
                }
                val jsonObject = JSONObject(response)
                val msg = jsonObject.getString("msg")
                val status = jsonObject.getString("status")
                cartcount!!.setText(jsonObject.optString("cart_items_count"))
                ClsGeneral.setPreferences(this@MyCart, ClsGeneral.CARTCOUNT, jsonObject
                        .optString("cart_items_count"))


                if (status.equals("1", ignoreCase = true)) {
                    cartlist.clear()
                    val jsonObject1 = jsonObject.getJSONObject("data")
                    cart_id = jsonObject1.getString("cart_id")
                    val jsonArray = jsonObject1.getJSONArray("cart_items")
                    for (i in 0..jsonArray.length() - 1) {
                        val jo = jsonArray.getJSONObject(i)
                        val productListModel = MyCartModel(jo.getString("cart_products_id_products"), jo.getString("cart_product_image"), jo.getString("cart_product_name"), jo
                                .getString("cart_products_id"), jo.getString("cart_products_qty"),
                                jo.getString("cart_product_price"), jo.getString("product_total"));

                        cartlist.add(productListModel)
                    }

                    myAdapter = MyCartAdapter(this@MyCart, cartlist!!,
                            this@MyCart)
                    rec_cartlist!!.setAdapter(myAdapter)
                    if (cartlist.size >= 1) {
                        checkout!!.setVisibility(View.VISIBLE)
                    }

                    settotalprice()

                } else if (jsonObject.getString("status").equals("0", ignoreCase = true)) run {

                    val myordershow = findViewById(R.id
                            .myordershow) as RelativeLayout
                    myordershow.visibility = View.VISIBLE
                } else if (jsonObject.getString("status").equals("2", ignoreCase = true)) {
                    ClsGeneral.setPreferences(this@MyCart, ClsGeneral.USERID, "")
                    startActivity(Intent(this@MyCart, SendOtp::class.java))
                    finish()
                } else {
                    Toast.makeText(this@MyCart, "" + msg, Toast.LENGTH_SHORT).show()
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

        })
        web.setReqType("get")
        web.execute(WebServices.MYCART + ClsGeneral.getPreferences(this@MyCart, ClsGeneral
                .SESSIONTOKEN) + "/" + ClsGeneral.getPreferences(this@MyCart, ClsGeneral.USERID))
    }

    private fun showsnakebar() {
        snackbar = Snackbar
                .make(this!!.coordinatorLayout!!, "No internet connection!", Snackbar.LENGTH_LONG)
                .setAction("RETRY") {
                    if (Utility.isNetworkAvailable(this@MyCart) && cartlist!!.size == 0) {
                        if (snackbar != null && snackbar!!.isShown()) {
                            snackbar!!.dismiss()
                        }
                        getCartapi()
                    } else {
                        if (cartlist!!.size == 0)
                            Handler().postDelayed({ showsnakebar() }, 2000)

                    }
                }

        // Changing message text color
        snackbar!!.setActionTextColor(Color.RED)
        // Changing action button text color
        val sbView = snackbar!!.getView()
        val textView = sbView.findViewById(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.YELLOW)
        snackbar!!.setDuration(Snackbar.LENGTH_INDEFINITE)
        snackbar!!.show()
    }

    fun updateplusminus(position: Int, qty: String) {

        ClsGeneral.setPreferences(this@MyCart, ClsGeneral.ISLOADPRODUCT, "true")
        Utility.showDailog(this@MyCart, resources.getString(R.string.pleasewait))

        var nameValuePairs = ArrayList<NameValuePair>()
        val web = Download_web(this, OnTaskCompleted { response ->
            try {
                Utility.closeDialog()

                if (snackbar != null && snackbar!!.isShown()) {
                    snackbar!!.dismiss()
                }
                val jsonObject = JSONObject(response)
                cartcount!!.setText(jsonObject.optString("cart_items_count"))
                ClsGeneral.setPreferences(this@MyCart, ClsGeneral.CARTCOUNT, jsonObject
                        .optString("cart_items_count"))

                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equals("1", ignoreCase = true)) {
                        myAdapter!!.notifyDataSetChanged()
                        settotalprice()

                    } else if (jsonObject.getString("status").equals("2", ignoreCase = true)) {
                        ClsGeneral.setPreferences(this@MyCart, ClsGeneral.USERID, "")
                        startActivity(Intent(this@MyCart, SendOtp::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@MyCart, "" + jsonObject.getString("msg"), Toast
                                .LENGTH_SHORT).show()
                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

        })
        web.setReqType("post")
        nameValuePairs.add(BasicNameValuePair("session_token", ClsGeneral.getPreferences(this@MyCart, ClsGeneral.SESSIONTOKEN)))
        nameValuePairs.add(BasicNameValuePair("user_id", ClsGeneral.getPreferences(this@MyCart, ClsGeneral.USERID)))
        nameValuePairs.add(BasicNameValuePair("id_product", cartlist!!.get(position)
                .cart_products_id_products))
        nameValuePairs.add(BasicNameValuePair("qty", qty))
        web.setData(nameValuePairs)
        web.execute(WebServices.ADDTOCART)

    }

    fun detele(position: Int) {
        Utility.showDailog(this@MyCart, resources.getString(R.string.pleasewait))
        ClsGeneral.setPreferences(this@MyCart, ClsGeneral.ISLOADPRODUCT, "true")
        pos = position
        val url = "session_token=" + ClsGeneral.getPreferences(this@MyCart, ClsGeneral
                .SESSIONTOKEN) + "&user_id=" + ClsGeneral
                .getPreferences(this@MyCart, ClsGeneral.USERID) + "&cart_id=" + cart_id + "&cart_item_id=" + cartlist
                .get(position).cart_products_id + "&cart_item_product_id=" + cartlist.get(position)
                .cart_products_id_products

        val web = Download_web(this, OnTaskCompleted { response ->
            try {
                Utility.closeDialog()

                if (snackbar != null && snackbar!!.isShown()) {
                    snackbar!!.dismiss()
                }
                val jsonObject = JSONObject(response)
                cartcount!!.setText(jsonObject.optString("cart_items_count"))
                if (cartcount!!.text.toString().equals("0", ignoreCase = true)) {

                    val myordershow = findViewById(R.id
                            .myordershow) as RelativeLayout
                    myordershow.visibility = View.VISIBLE
                }

                ClsGeneral.setPreferences(this@MyCart, ClsGeneral.CARTCOUNT, jsonObject
                        .optString("cart_items_count"))

                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equals("1", ignoreCase = true)) {
                        cartlist.remove(cartlist.get(pos!!))
                        if (cartlist.size >= 1) {
                            checkout!!.setVisibility(View.VISIBLE)
                        } else if (cartlist.size == 0) {
                            checkout!!.setVisibility(View.GONE)
                        }
                        myAdapter!!.notifyDataSetChanged()
                        settotalprice()

                    } else if (jsonObject.getString("status").equals("2", ignoreCase = true)) {
                        ClsGeneral.setPreferences(this@MyCart, ClsGeneral.USERID, "")
                        startActivity(Intent(this@MyCart, SendOtp::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@MyCart, "" + jsonObject.getString("msg"), Toast
                                .LENGTH_SHORT).show()
                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

        })
        web.setReqType("delete")
        web.setPutdata(url)
        web.execute(WebServices.DELETECART)
    }

    private fun settotalprice() {
        var total = 0
        var price = 0
        var quantity = 0
        for (i in cartlist.indices) {
            try {

                price = Integer.parseInt(cartlist.get(i).cart_product_price)
                quantity = Integer.parseInt(cartlist.get(i).cart_products_qty)

                if (total == 0) {
                    total = getallprice(price, quantity)
                } else {
                    total = total + getallprice(price, quantity)
                }
            } catch (e: NumberFormatException) {

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        checkoutprice!!.setText("" + total)
    }

    private fun getallprice(price: Int, quantity: Int): Int {
        return price * quantity
    }
}