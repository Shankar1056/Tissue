package bigappcompany.com.tissu.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
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
import bigappcompany.com.tissu.adapter.SavedAddressAdapter
import bigappcompany.com.tissu.common.ClsGeneral
import bigappcompany.com.tissu.login.SendOtp
import bigappcompany.com.tissu.model.SavedAddressModel
import org.json.JSONObject
import java.util.*

/**
 * @author Samuel Robert <sam></sam>@spotsoon.com>
 * @created on 18 Sep 2017 at 3:56 PM
 */

class MyAddress : AppCompatActivity(), View.OnClickListener {

    private var savedaddress_rec: RecyclerView? = null
    private var savedaddresslist = ArrayList<SavedAddressModel>()
    private var savedAddressAdapter: SavedAddressAdapter? = null
    private var addaddresslayout: LinearLayout? = null
    private var addnewaddres: TextView? = null
    private var addresslistsize: Int = 0
    private var address: String? = null
    private var address_id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myaddress)

        domapping()

    }

    private fun domapping() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.title = ""
        val toolbartext = findViewById(R.id.toolbartext) as TextView
        toolbartext.text = "My Addresses"
        addnewaddres = findViewById(R.id.addnewaddres) as TextView
        val cartlayout = findViewById(R.id.cartlayout) as LinearLayout
        cartlayout.visibility = View.INVISIBLE

        addnewaddres!!.setOnClickListener(this)
        toolbartext.typeface = Utility.font(this@MyAddress, "medium")
        addnewaddres!!.typeface = Utility.font(this@MyAddress, "medium")

        toolbar.setNavigationOnClickListener { finish() }

        recycleviewmapping()

    }

    private fun recycleviewmapping() {
        savedaddress_rec = findViewById(R.id.savedaddress_rec) as RecyclerView
        addaddresslayout = findViewById(R.id.addaddresslayout) as LinearLayout

        savedaddress_rec!!.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        savedaddress_rec!!.layoutManager = mLayoutManager
        //savedaddress_rec.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        savedaddress_rec!!.itemAnimator = DefaultItemAnimator()


        savedaddress_rec!!.addOnItemTouchListener(RecyclerTouchListener(applicationContext, savedaddress_rec, object : RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {

            }

            override fun onLongClick(view: View, position: Int) {

            }
        }))


    }

    private fun getsavedlocation() {


        getSavedAddress()

    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.addnewaddres -> if (addnewaddres!!.text.toString() == "Checkout") {
                startActivity(Intent(this@MyAddress, PaymentScreen::class.java).putExtra("address",
                        address).putExtra("address_id", address_id))
            } else if (addnewaddres!!.text.toString().equals(resources.getString(R
                    .string.addnewaddress), ignoreCase = true)) {
                startActivity(Intent(this@MyAddress, EditAddress::class.java).putExtra("type", "addnew"))
            }
        }

    }

    fun edit(position: Int) {
        val intent = Intent(this@MyAddress, EditAddress::class.java)
        intent.putExtra("type", "edit")
        intent.putExtra("address1", savedaddresslist[position].address1)
        intent.putExtra("address2", savedaddresslist[position].address2)
        intent.putExtra("city", savedaddresslist[position].city)
        intent.putExtra("zipcode", savedaddresslist[position].pincode)
        intent.putExtra("state", savedaddresslist[position].state_name)
        intent.putExtra("addresstype", savedaddresslist[position].address_type_name)
        intent.putExtra("addresstype_id", savedaddresslist[position].address_type_id)
        intent.putExtra("state_id", savedaddresslist[position].state_id)
        intent.putExtra("address_id", savedaddresslist[position].address_id)
        startActivity(intent)
    }

    fun delete(position: Int) {
        val url = "session_token=" + ClsGeneral.getPreferences(this@MyAddress, ClsGeneral.SESSIONTOKEN) + "&user_id=" + ClsGeneral
                .getPreferences(this@MyAddress, ClsGeneral.USERID) + "&address_id=" + savedaddresslist[position]
                .address_id
        //new DeleteAddress().execute(WebServices.DELETEADDRESS,savedaddresslist.get(position).getAddress_id());

        deleteAddress(url, position)
    }

    private fun getSavedAddress() {
        Utility.showDailog(this@MyAddress, resources.getString(R.string.pleasewait))
        val web = Download_web(this, OnTaskCompleted { response ->
            try {
                Utility.closeDialog()
                val jsonObject = JSONObject(response)
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equals("1", ignoreCase = true)) {
                        savedaddresslist.clear()
                        val jsonObject1 = jsonObject.getJSONObject("data")
                        val jsonArray = jsonObject1.getJSONArray("address")
                        addresslistsize = jsonArray.length()
                        for (i in 0..jsonArray.length() - 1) {
                            val jo = jsonArray.getJSONObject(i)
                            val savedAddress = SavedAddressModel(jo.getString("address_type_id"), jo.getString("address_type_name"), jo.getString("state_id"),
                                    jo.getString("state_name"), jo.getString("pincode"), jo.getString("address1"),
                                    jo.getString("address2"), jo.getString("city"), jo.getString("address_id"), false)
                            savedaddresslist.add(savedAddress)

                        }

                        savedAddressAdapter = SavedAddressAdapter(this@MyAddress, savedaddresslist, ClsGeneral.getPreferences(this@MyAddress, "from"), this@MyAddress)
                        savedaddress_rec!!.adapter = savedAddressAdapter

                        if (savedaddresslist.size == 3) {
                            addaddresslayout!!.visibility = View.GONE
                            addnewaddres!!.text = "Checkout"
                        }
                        if (savedaddresslist.size < 3) {
                            addaddresslayout!!.visibility = View.VISIBLE
                            addnewaddres!!.text = resources.getString(R.string.addnewaddress)
                        }
                        val myordershow = findViewById(R.id
                                .myordershow) as RelativeLayout
                        myordershow.visibility = View.GONE

                    } else if (jsonObject.getString("status").equals("0", ignoreCase = true)) {

                        val myordershow = findViewById(R.id
                                .myordershow) as RelativeLayout
                        myordershow.visibility = View.VISIBLE
                    } else if (jsonObject.getString("status").equals("2", ignoreCase = true)) {
                        ClsGeneral.setPreferences(this@MyAddress, ClsGeneral.USERID, "")
                        startActivity(Intent(this@MyAddress, SendOtp::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@MyAddress, "" + jsonObject.getString("msg"), Toast
                                .LENGTH_SHORT).show()
                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

            Log.e("Response : ", response)
        })
        web.setReqType("get")
        web.execute(WebServices.SAVEDADDRESS + ClsGeneral.getPreferences(this@MyAddress,
                ClsGeneral.SESSIONTOKEN) + "/" + ClsGeneral.getPreferences(this@MyAddress, ClsGeneral.USERID))
    }

    private fun deleteAddress(url: String, pos: Int) {
        val web = Download_web(this, OnTaskCompleted { response ->
            try {
                val jsonObject = JSONObject(response)
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equals("1", ignoreCase = true)) {
                        Toast.makeText(this@MyAddress, "" + jsonObject.getString("msg"), Toast
                                .LENGTH_SHORT).show()
                        savedaddresslist.remove(savedaddresslist[pos])
                        savedAddressAdapter!!.notifyDataSetChanged()
                        addresslistsize = savedaddresslist.size
                        if (addresslistsize == 3) {
                            addaddresslayout!!.visibility = View.GONE
                            addnewaddres!!.text = "Checkout"
                        }
                        if (addresslistsize < 3) {
                            addaddresslayout!!.visibility = View.VISIBLE
                            addnewaddres!!.text = resources.getString(R.string.addnewaddress)
                        }
                        addresslistsize = savedaddresslist.size
                        if (addresslistsize == 0) {

                            val myordershow = findViewById(R.id
                                    .myordershow) as RelativeLayout
                            myordershow.visibility = View.VISIBLE
                        }

                    } else if (jsonObject.getString("status").equals("2", ignoreCase = true)) {
                        ClsGeneral.setPreferences(this@MyAddress, ClsGeneral.USERID, "")
                        startActivity(Intent(this@MyAddress, SendOtp::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@MyAddress, "" + jsonObject.getString("msg"), Toast
                                .LENGTH_SHORT).show()
                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

            Log.e("Response : ", response)
        })
        web.setReqType("delete")
        web.setPutdata(url)
        web.execute(WebServices.DELETEADDRESS)
    }

    fun sendpos(clickedPos: String, adress: String, address_id: String) {
        address = adress
        this.address_id = address_id

        if (clickedPos.equals("true", ignoreCase = true)) {
            //if (ClsGeneral.getPreferences(MyAddress.this,"from").equalsIgnoreCase("cart")) {
            addaddresslayout!!.visibility = View.VISIBLE
            addnewaddres!!.text = "Checkout"
            //}
        } else if (clickedPos.equals("false", ignoreCase = true)) {
            if (addresslistsize < 3) {
                if (ClsGeneral.getPreferences(this@MyAddress, "from").equals("cart", ignoreCase = true)) {
                    addaddresslayout!!.visibility = View.VISIBLE
                    addnewaddres!!.text = resources.getString(R.string.addnewaddress)
                }
            } else if (addresslistsize == 3) {
                addaddresslayout!!.visibility = View.GONE
            }
        }


    }

    override fun onResume() {
        super.onResume()
        getsavedlocation()
        val cartcount = findViewById(R.id.cartcount) as TextView
        cartcount.text = ClsGeneral.getPreferences(this@MyAddress, ClsGeneral.CARTCOUNT)
    }


}
