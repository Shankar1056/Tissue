package bigappcompany.com.tissu.activity

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.util.Log
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
import bigappcompany.com.tissu.adapter.CustomPagerAdapter
import bigappcompany.com.tissu.adapter.DotsAdapter
import bigappcompany.com.tissu.adapter.HomeCategoryAdapter
import bigappcompany.com.tissu.common.ClsGeneral
import bigappcompany.com.tissu.login.SendOtp
import bigappcompany.com.tissu.model.PhotoModel
import bigappcompany.com.vegan.model.ProductListModel
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import org.json.JSONObject
import java.util.*

/**
 * @author Shankar
 * @created on 07 Sep 2017 at 11:37 AM
 */

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private var recycler: RecyclerView? = null
    private var snackbar: Snackbar? = null
    private var coordinatorLayout: CoordinatorLayout? = null
    private val cml = ArrayList<ProductListModel>()
    private var cartcount: TextView? = null
    private var cartlayout: LinearLayout? = null
    private var myAdapter: HomeCategoryAdapter? = null
    private var mViewPager: ViewPager? = null
    private var previousPage: Int? = null
    private var previousState: Int? = null
    private var dotsAdapter: DotsAdapter? = null
    private var mCustomPagerAdapter: CustomPagerAdapter? = null
    private var geasturesEnable: Boolean? = null
    private var dots: RecyclerView? = null
    private var drawerLayout: RelativeLayout? = null
    private var drawer:DrawerLayout?=null
    private var dialog: ProgressDialog? = null
    private var mSwipeRefreshLayout:SwipeRefreshLayout?=null

    private var TAG = "MainActivity.class : "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_main)


        domapping()

    }

    private fun navigationmapping() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = ""
        drawerLayout = findViewById(R.id.drawerLayout) as RelativeLayout

        drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer!!.setDrawerListener(toggle)
        toggle.syncState()


    }

    private fun domapping() {
        cartcount = findViewById(R.id.cartcount) as TextView
        recycler = findViewById(R.id.recycler) as RecyclerView
        cartlayout = findViewById(R.id.cartlayout) as LinearLayout?
        mViewPager = findViewById(R.id.pager_zoom) as ViewPager
        dots = findViewById(R.id.dots) as RecyclerView
        drawerLayout = findViewById(R.id.drawerLayout) as RelativeLayout
        mSwipeRefreshLayout = findViewById(R.id.swipetorefresh) as SwipeRefreshLayout
        mSwipeRefreshLayout!!.isRefreshing = false


        val myaddress = findViewById(R.id.myaddress) as TextView
        val myorder = findViewById(R.id.myorder) as TextView
        val mycart = findViewById(R.id.mycart) as TextView
        val contactus = findViewById(R.id.contactus) as TextView
        val aboutus = findViewById(R.id.aboutus) as TextView
        val rateus = findViewById(R.id.rateus) as TextView
        val logout = findViewById(R.id.logout) as TextView
        val others = findViewById(R.id.others) as TextView
        val info = findViewById(R.id.info) as TextView

        coordinatorLayout = findViewById(R.id.coordinatorLayout) as CoordinatorLayout
        recycler!!.setHasFixedSize(true)
        recycler!!.isNestedScrollingEnabled = false
        val mLayoutManager = GridLayoutManager(this@MainActivity, 1)
        recycler!!.layoutManager = mLayoutManager
        recycler!!.itemAnimator = DefaultItemAnimator()
        recycler!!.addOnItemTouchListener(RecyclerTouchListener(applicationContext, recycler, object :
                RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {


            }

            override fun onLongClick(view: View, position: Int) {

            }
        }))


        cartlayout!!.setOnClickListener {
            startActivity(Intent(this@MainActivity, MyCart::class.java))
        }

        mViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //Log.e("onPageScrool","position "+position+" , positionoffset "+positionOffset+" , positionoffsetPixels"+positionOffsetPixels);
            }

            override fun onPageSelected(position: Int) {
                Log.e("onPageSelected", "position " + position)
                if (previousPage != position) {
                    //changed

                    dotsAdapter!!.setSelected(position)
                    dotsAdapter!!.notifyDataSetChanged()
                }
                previousPage = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.e("onScrollStateChanged", "state " + state)

                previousState = state

            }
        })


        cartcount!!.typeface = Utility.font(this@MainActivity, "medium")
        rateus!!.typeface = Utility.font(this@MainActivity, "medium")
        logout!!.typeface = Utility.font(this@MainActivity, "medium")
        others!!.typeface = Utility.font(this@MainActivity, "medium")
        info!!.typeface = Utility.font(this@MainActivity, "medium")
        myaddress!!.typeface = Utility.font(this@MainActivity, "regular")
        myorder!!.typeface = Utility.font(this@MainActivity, "regular")
        mycart!!.typeface = Utility.font(this@MainActivity, "regular")
        contactus!!.typeface = Utility.font(this@MainActivity, "regular")
        aboutus!!.typeface = Utility.font(this@MainActivity, "regular")

        myaddress.setOnClickListener(this)
        myorder.setOnClickListener(this)
        mycart.setOnClickListener(this)
        contactus.setOnClickListener(this)
        aboutus.setOnClickListener(this)



        navigationmapping()
        if (Utility.isNetworkAvailable(this@MainActivity)) {
            getSlidder()
        }
        if (cml.size == 0) {
            try {
                if (Utility.isNetworkAvailable(this@MainActivity)) {
                    mSwipeRefreshLayout!!.isRefreshing = true
                    getHomeProduct()
                    ClsGeneral.setPreferences(this@MainActivity, ClsGeneral.ISLOADPRODUCT,"false")
                }
                else {
                    showsnakebar()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        mSwipeRefreshLayout!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            try {
                if (Utility.isNetworkAvailable(this@MainActivity)) {
                    mSwipeRefreshLayout!!.isRefreshing = true
                    getSlidder()
                    getHomeProduct()
                    ClsGeneral.setPreferences(this@MainActivity, ClsGeneral.ISLOADPRODUCT,"false")
                }
                else {
                    showsnakebar()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

    }


    override fun onClick(v: View) {

        when (v.id) {
            R.id.myaddress -> {
                ClsGeneral.setPreferences(this@MainActivity, "from", "main")
                startActivity(Intent(this@MainActivity, MyAddress::class.java))
            }
            R.id.myorder ->
                startActivity(Intent(this@MainActivity, MyOrder::class.java))
            R.id.mycart ->
                startActivity(Intent(this@MainActivity, MyCart::class.java))
            R.id.contactus ->
                    startActivity(Intent(this@MainActivity,AboutContact::class.java).putExtra("abtcontact","contact"))
            R.id.aboutus->

                startActivity(Intent(this@MainActivity,AboutContact::class.java).putExtra("abtcontact","about"))


        }
        drawer!!.closeDrawer(drawerLayout)

    }


    private fun getSlidder() {
        val models = ArrayList<PhotoModel>()
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
                ClsGeneral.setPreferences(this@MainActivity, ClsGeneral.CARTCOUNT, jsonObject
                        .optString("cart_items_count"))


                if (status.equals("1", ignoreCase = true)) {
                    cml.clear()

                    val jsonArray = jsonObject.getJSONArray("data")
                    for (i in 0..jsonArray.length() - 1) {
                        models.add(PhotoModel(jsonArray.getJSONObject(i)))
                    }
                    mCustomPagerAdapter = CustomPagerAdapter()
                    mCustomPagerAdapter!!.setData(models, applicationContext, this@MainActivity)
                    mCustomPagerAdapter!!.isDynamic(true)
                    mCustomPagerAdapter!!.setVP(mViewPager)
                    mViewPager!!.adapter = mCustomPagerAdapter
                    geasturesEnable = true
                    if (models.size > 1) {
                        //Dots

                        with(dots!!, {
                            setHasFixedSize(true)
                            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL,
                                    false)
                        })
                        dotsAdapter = DotsAdapter(models.size)
                        dots!!.setAdapter(dotsAdapter)
                    }

                } else if (jsonObject.getString("status").equals("2", ignoreCase = true)) {

                    startActivity(Intent(this@MainActivity, SendOtp::class.java))
                    finish()
                } else {
                    Toast.makeText(this@MainActivity, "" + msg, Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            Log.e("Response : ", response)

        })
        web.setReqType("get")
        web.execute(WebServices.HOME_SLIDER)
    }


    override fun onResume() {
        super.onResume()
        val a = ClsGeneral.getPreferences(this@MainActivity, ClsGeneral.ISLOADPRODUCT)
        if (a.equals("true", ignoreCase = true)) {
            if (Utility.isNetworkAvailable(this@MainActivity)) {
                mSwipeRefreshLayout!!.isRefreshing = true
                getHomeProduct()
            }
        }
        try {
            if (Integer.parseInt(ClsGeneral.getPreferences(this@MainActivity, ClsGeneral.CARTCOUNT)) >= 0) {
                cartcount!!.text = ClsGeneral.getPreferences(this@MainActivity, ClsGeneral.CARTCOUNT)
            } else {
                cartcount!!.text = "0"
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            cartcount!!.text = "0"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getHomeProduct() {

       //  showdialog(resources.getString(R.string.pleasewait))

        val web = Download_web(this, OnTaskCompleted { response ->
            try {
                mSwipeRefreshLayout!!.isRefreshing = false
               // if (dialog != null)dialog!!.cancel()

                if (snackbar != null && snackbar!!.isShown) {
                    snackbar!!.dismiss()
                }
                Log.e("Home Category : ", response)
                val jsonObject = JSONObject(response)
                val msg = jsonObject.getString("msg")
                val status = jsonObject.getString("status")
                cartcount!!.text = jsonObject.optString("cart_items_count")
                ClsGeneral.setPreferences(this@MainActivity, ClsGeneral.CARTCOUNT, jsonObject
                        .optString("cart_items_count"))


                if (status.equals("1", ignoreCase = true)) {
                    ClsGeneral.setPreferences(this@MainActivity, ClsGeneral.ISLOADPRODUCT, "false")
                    cml.clear()

                    val jsonObject1 = jsonObject.getJSONObject("data")
                    val jsonArray = jsonObject1.getJSONArray("products")
                    for (i in 0..jsonArray.length() - 1) {
                        val jo = jsonArray.getJSONObject(i)
                        val productListModel = ProductListModel(jo.getString("id_products"),
                                jo.getString("product_name"), jo.getString("id_category"),
                                jo.getString("name_category"), jo.getString("price_with_tax"),
                                jo.getString("products_image"), jo.getString("prod_short_desc"),
                                jo.getString("prod_desc"), jo.getString("user_selected_qty"), "0")

                        cml.add(productListModel)
                    }

                    myAdapter = HomeCategoryAdapter(this@MainActivity, cml,
                            this@MainActivity)
                    recycler!!.adapter = myAdapter

                } else if (jsonObject.getString("status").equals("2", ignoreCase = true)) {
                    ClsGeneral.setPreferences(this@MainActivity, ClsGeneral.USERID, "")
                    startActivity(Intent(this@MainActivity, SendOtp::class.java))
                    finish()
                } else {
                    Toast.makeText(this@MainActivity, "" + msg, Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Category Exce: ",e.printStackTrace().toString())
            }

            Log.e("Response : ", response)

        })
        web.setReqType("get")
        web.execute(WebServices.HOME_PRODUCTS + ClsGeneral.getPreferences(this@MainActivity,
                ClsGeneral.SESSIONTOKEN) + "/" + ClsGeneral.getPreferences(this@MainActivity, ClsGeneral.USERID))
    }

    private fun showdialog(string: String?) {

        dialog = ProgressDialog(this@MainActivity)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setMessage(string)
        dialog!!.show()
    }


    private fun showsnakebar() {
        snackbar = Snackbar
                .make(this.coordinatorLayout!!, "No internet connection!", Snackbar.LENGTH_LONG)
                .setAction("RETRY") {
                    if (Utility.isNetworkAvailable(this@MainActivity)!! && cml.size == 0) {
                        if (snackbar != null && snackbar!!.isShown) {
                            snackbar!!.dismiss()
                        }
                        mSwipeRefreshLayout!!.isRefreshing = true
                        getSlidder()
                        getHomeProduct()
                    } else {
                        if (cml.size == 0)
                            Handler().postDelayed({ showsnakebar() }, 2000)

                    }
                }

        // Changing message text color
        snackbar!!.setActionTextColor(Color.RED)
        // Changing action button text color
        val sbView = snackbar!!.view
        val textView = sbView.findViewById(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.YELLOW)
        snackbar!!.duration = Snackbar.LENGTH_INDEFINITE
        snackbar!!.show()
    }

    fun pos(position: Int, count: String) {
        // startActivity(Intent(this@MainActivity, MyAddress::class.java))
        startActivity(Intent(this@MainActivity, ProductDescription::class.java).putExtra("cat_id", cml[position]
                .id_category).putExtra("prod_id", cml[position].id_products).putExtra("prod_name", cml[position]
                .product_name).putExtra("itemcount", count))

    }

    fun updateplusminus(position: Int, qty: String, addplusminus: String) {

        val nameValuePairs = ArrayList<NameValuePair>()
        val web = Download_web(this, OnTaskCompleted { response ->
            try {
                Utility.closeDialog()
                if (snackbar != null && snackbar!!.isShown) {
                    snackbar!!.dismiss()
                }

                val jsonObject = JSONObject(response)
                cartcount!!.text = jsonObject.optString("cart_items_count")
                ClsGeneral.setPreferences(this@MainActivity, ClsGeneral.CARTCOUNT, jsonObject
                        .optString("cart_items_count"))
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equals("1", ignoreCase = true)) {
                        myAdapter!!.notifyDataSetChanged()

                    } else if (jsonObject.getString("status").equals("2", ignoreCase = true)) {
                        ClsGeneral.setPreferences(this@MainActivity, ClsGeneral.USERID, "")
                        startActivity(Intent(this@MainActivity, SendOtp::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@MainActivity, "" + jsonObject.getString("msg"), Toast
                                .LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }


        })

        nameValuePairs.add(BasicNameValuePair("session_token", ClsGeneral.getPreferences(this@MainActivity, ClsGeneral.SESSIONTOKEN)))
        nameValuePairs.add(BasicNameValuePair("user_id", ClsGeneral.getPreferences(this@MainActivity, ClsGeneral.USERID)))
        nameValuePairs.add(BasicNameValuePair("id_product", cml.get(position).id_products))
        nameValuePairs.add(BasicNameValuePair("qty", qty))


        web.setReqType("post")
        web.setData(nameValuePairs)
        web.execute(WebServices.ADDTOCART)

    }
}
