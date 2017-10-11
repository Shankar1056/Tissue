package bigappcompany.com.tissu.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import bigappcompany.com.tissu.R
import bigappcompany.com.tissu.Utilz.Download_web
import bigappcompany.com.tissu.Utilz.OnTaskCompleted
import bigappcompany.com.tissu.Utilz.Utility
import bigappcompany.com.tissu.Utilz.WebServices
import bigappcompany.com.tissu.adapter.ImageDotAdapter
import bigappcompany.com.tissu.adapter.ImageSliderCustomAdapter
import bigappcompany.com.tissu.common.ClsGeneral
import bigappcompany.com.tissu.login.SendOtp
import bigappcompany.com.tissu.model.DescriptinSliderModel
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

/**
 * @author Shankar
 * @created on 16 Sep 2017 at 3:48 PM
 */

class ProductDescription : AppCompatActivity(), View.OnClickListener {

    private var isIntenetConnected: Boolean? = null
    private var mViewPager: ViewPager? = null
    private var dots: RecyclerView? = null
    private var previousPage: Int? = null
    private var previousState: Int? = null
    private var dotsAdapter: ImageDotAdapter? = null
    private var mImageSliderCustomAdapter: ImageSliderCustomAdapter? = null
    private var geasturesEnable: Boolean? = null
    private var snackbar: Snackbar? = null
    private var coordinatorLayout: CoordinatorLayout? = null
    private var cartcount: TextView? = null
    private var addcartlayout: LinearLayout? = null
    private var plusminuslayout: LinearLayout? = null
    private var addingtext: TextView? = null
    private var totalcount: Int = 0
    private var count: TextView? = null
    private val models = java.util.ArrayList<DescriptinSliderModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.productdescription)
        mapping()
    }

    private fun mapping() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.title = ""
        cartcount = findViewById(R.id.cartcount) as TextView
        mViewPager = findViewById(R.id.pager_zoom) as ViewPager
        dots = findViewById(R.id.dots) as RecyclerView
        cartcount = findViewById(R.id.cartcount) as TextView
        addcartlayout = findViewById(R.id.addcartlayout) as LinearLayout
        val plusbutton = findViewById(R.id.plusbutton) as ImageView
        val minusbutton = findViewById(R.id.minusbutton) as ImageView
        val toolbartext = findViewById(R.id.toolbartext) as TextView
        count = findViewById(R.id.count) as TextView
        addingtext = findViewById(R.id.addingtext) as TextView
        plusminuslayout = findViewById(R.id.plusminuslayout) as LinearLayout
        findViewById(R.id.addtocart).setOnClickListener(this)
        findViewById(R.id.cartlayout).setOnClickListener(this)


        mViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //Log.e("onPageScrool","position "+position+" , positionoffset "+positionOffset+" , positionoffsetPixels"+positionOffsetPixels);
            }

            override fun onPageSelected(position: Int) {
                Log.e("onPageSelected", "position " + position)
                try {


                    if (previousPage != position) {
                        //changed

                        dotsAdapter!!.setSelected(position)
                        dotsAdapter!!.notifyDataSetChanged()
                    }
                    previousPage = position
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.e("onScrollStateChanged", "state " + state)

                previousState = state

            }
        })


        toolbartext.typeface = Utility.font(this@ProductDescription, "medium")
        count!!.typeface = Utility.font(this@ProductDescription, "medium")
        toolbartext.text = intent.getStringExtra("prod_name")
        count!!.text = intent.getStringExtra("itemcount")


        plusbutton.setOnClickListener(this)
        minusbutton.setOnClickListener(this)
        getSlidder()

        toolbar.setNavigationOnClickListener { finish() }

        try {
            totalcount = Integer.parseInt(count!!.text.toString())

        } catch (e: NumberFormatException) {
            e.printStackTrace()
            totalcount = 1
        } catch (e: Exception) {
            e.printStackTrace()
        }
        settext()

    }

    fun sendpos(pos: Int) {
        startActivity(Intent(this@ProductDescription, ImageSlide::class.java).putExtra("dotlist",models
        ).putExtra("pos",pos))
    }


    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.addcarttext ->
                addcartmethod("plus")
            R.id.plusbutton ->
                addcartmethod("plus")
            R.id.minusbutton ->
                addcartmethod("minus")
            R.id.addtocart -> {
                startActivity(Intent(this@ProductDescription, MyCart::class.java))
                finish()
            }
            R.id.cartlayout->
                startActivity(Intent(this@ProductDescription, MyCart::class.java))

        }

    }

    private fun getSlidder() {
        //Utility.showDailog(this@MainActivity, resources.getString(R.string.pleasewait))
        val web = Download_web(this, OnTaskCompleted { response ->
            try {
                Utility.closeDialog()
                if (snackbar != null && snackbar!!.isShown) {
                    snackbar!!.dismiss()
                }
                val jsonObject = JSONObject(response)
                val msg = jsonObject.getString("msg")
                val status = jsonObject.getString("status")
                cartcount!!.text = jsonObject.optString("cart_items_count")
                ClsGeneral.setPreferences(this@ProductDescription, ClsGeneral.CARTCOUNT, jsonObject
                        .optString("cart_items_count"))


                if (status.equals("1", ignoreCase = true)) {

                    val jsonArray = jsonObject.getJSONObject("data").getJSONArray("products")
                    val sliderArray = jsonObject.getJSONObject("data").getJSONArray("product_image")
                    (0 until sliderArray.length()).mapTo(models) {
                        DescriptinSliderModel(
                                sliderArray.getJSONObject(it))
                    }
                    mImageSliderCustomAdapter = ImageSliderCustomAdapter()
                    mImageSliderCustomAdapter!!.setData(models, applicationContext, this@ProductDescription)
                    mImageSliderCustomAdapter!!.isDynamic(true)
                    mImageSliderCustomAdapter!!.setVP(mViewPager)
                    mViewPager!!.adapter = mImageSliderCustomAdapter
                    geasturesEnable = true


                    if (models.size > 0) {
                        //Dots

                        with(dots!!, {
                            setHasFixedSize(true)
                            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL,
                                    false)
                        })
                        dotsAdapter = ImageDotAdapter(this@ProductDescription, models, models.size, this@ProductDescription)
                        dots!!.adapter = dotsAdapter
                    }

                    setvalue(jsonArray)

                } else if (jsonObject.getString("status").equals("2", ignoreCase = true)) {
                    ClsGeneral.setPreferences(this@ProductDescription, ClsGeneral.USERID, "")
                    startActivity(Intent(this@ProductDescription, SendOtp::class.java))
                    finish()
                } else {
                    Toast.makeText(this@ProductDescription, "" + msg, Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            Log.e("Response : ", response)

        })
        web.setReqType("get")
        web.execute(WebServices.PRODUCTLIST + ClsGeneral.getPreferences(this@ProductDescription,
                ClsGeneral.SESSIONTOKEN) + "/" + ClsGeneral.getPreferences(this@ProductDescription, ClsGeneral.USERID) + "/" +
                intent.getStringExtra("cat_id") + "/" + intent.getStringExtra("prod_id"))
    }

    private fun addcartmethod(operation: String) {
        ClsGeneral.setPreferences(this@ProductDescription, ClsGeneral.ISLOADPRODUCT, "true")

        if (operation.equals("plus")) {

            if (totalcount == 20) {
                Toast.makeText(this, "yu can order max 20 plate ", Toast.LENGTH_SHORT).show()
            } else {
                totalcount += 1
                addingtext!!.visibility = View.VISIBLE
                addingtext!!.text = "Adding"
            }
        }
        if (operation.equals("minus")) {
            totalcount -= 1
            addingtext!!.visibility = View.VISIBLE
            addingtext!!.text = "Deleting"
        }

        val nameValuePairs = ArrayList<NameValuePair>()
        val web = Download_web(this, OnTaskCompleted { response ->
            try {
                Utility.closeDialog()
                addingtext!!.visibility = View.INVISIBLE
                if (snackbar != null && snackbar!!.isShown) {
                    snackbar!!.dismiss()
                }

                val jsonObject = JSONObject(response)
                cartcount!!.text = jsonObject.optString("cart_items_count")
                ClsGeneral.setPreferences(this@ProductDescription, ClsGeneral.CARTCOUNT, jsonObject
                        .optString("cart_items_count"))
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equals("1", ignoreCase = true)) {
                        settext()
                    } else if (jsonObject.getString("status").equals("2", ignoreCase = true)) {
                        ClsGeneral.setPreferences(this@ProductDescription, ClsGeneral.USERID, "")
                        startActivity(Intent(this@ProductDescription, SendOtp::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@ProductDescription, "" + jsonObject.getString("msg"), Toast
                                .LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Utility.closeDialog()
                addingtext!!.visibility = View.INVISIBLE
            }
            Utility.closeDialog()
            addingtext!!.visibility = View.INVISIBLE

        })

        nameValuePairs.add(BasicNameValuePair("session_token", ClsGeneral.getPreferences(this@ProductDescription, ClsGeneral.SESSIONTOKEN)))
        nameValuePairs.add(BasicNameValuePair("user_id", ClsGeneral.getPreferences(this@ProductDescription, ClsGeneral.USERID)))
        nameValuePairs.add(BasicNameValuePair("id_product", intent.getStringExtra("prod_id")))
        nameValuePairs.add(BasicNameValuePair("qty", totalcount.toString()))


        web.setReqType("post")
        web.setData(nameValuePairs)
        web.execute(WebServices.ADDTOCART)
    }

    private fun deletecartmethod() {

        totalcount -= 1
        // productListModelList.get(pos).user_selected_qty = "" + totalcount

        ClsGeneral.setPreferences(this@ProductDescription, ClsGeneral.ISLOADPRODUCT, "true")
        /* UpdatePlusMinus().execute(WebServices.ADDTOCART, productListModelList.get(pos)
                 .id_products, "" + totalcount, "minus")*/
    }


    private fun setvalue(jsonArray: JSONArray?) {
        val productname = findViewById(R.id.productname) as TextView
        val productdetails = findViewById(R.id.productdetails) as TextView
        val amount = findViewById(R.id.amount) as TextView
        val desc = findViewById(R.id.desc) as TextView
        val addcarttext = findViewById(R.id.addcarttext) as TextView

        productname.typeface = Utility.font(this@ProductDescription, "medium")
        productdetails.typeface = Utility.font(this@ProductDescription, "medium")
        cartcount!!.typeface = Utility.font(this@ProductDescription, "bold")
        addcarttext.typeface = Utility.font(this@ProductDescription, "medium")
        desc.typeface = Utility.font(this@ProductDescription, "medium")
        amount.typeface = Utility.font(this@ProductDescription, "bold")

        addcarttext.setOnClickListener(this)

        productname.text = jsonArray!!.getJSONObject(0).optString("product_name")
        productdetails.text = (Html.fromHtml(jsonArray.getJSONObject(0).optString("prod_short_desc")))
        amount.text = jsonArray.getJSONObject(0).optString("price_with_tax")
        desc.text = Html.fromHtml(jsonArray.getJSONObject(0).optString("prod_desc"))


    }


    fun pos(position: Int) {

        if (previousPage != position) {
            //changed

            dotsAdapter!!.setSelected(position)
            dotsAdapter!!.notifyDataSetChanged()
        }
        previousPage = position
        (if (mViewPager != null) mViewPager else throw NullPointerException("Expression 'mViewPager' must not be null"))!!.currentItem = position
    }

    private fun settext() {
        if (totalcount == 0) {
            try {
                addcartlayout!!.setVisibility(View.VISIBLE)
                plusminuslayout!!.setVisibility(View.GONE)
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }

        } else if (totalcount >= 1) {
            try {
                addcartlayout!!.setVisibility(View.GONE)
                plusminuslayout!!.setVisibility(View.VISIBLE)
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }
        count!!.setText("" + totalcount)
    }
}
