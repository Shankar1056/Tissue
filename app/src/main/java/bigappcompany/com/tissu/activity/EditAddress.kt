package bigappcompany.com.tissu.activity

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.*
import bigappcompany.com.tissu.R
import bigappcompany.com.tissu.R.drawable.circular_button_full
import bigappcompany.com.tissu.Utilz.Download_web
import bigappcompany.com.tissu.Utilz.OnTaskCompleted
import bigappcompany.com.tissu.Utilz.Utility
import bigappcompany.com.tissu.Utilz.WebServices
import bigappcompany.com.tissu.common.ClsGeneral
import bigappcompany.com.tissu.login.SendOtp
import bigappcompany.com.tissu.model.AddressTypeModel
import bigappcompany.com.tissu.model.StateModel
import bigappcompany.com.tissu.network.NetworkReceiver
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import org.json.JSONObject
import java.util.*


/**
 * @author Samuel Robert <sam></sam>@spotsoon.com>
 * @created on 16 Jun 2017 at 6:59 PM
 */

class EditAddress : AppCompatActivity(), AdapterView.OnItemClickListener, View.OnClickListener {
    private var homeimage: ImageView? = null
    private var workimage: ImageView? = null
    private var otherimage: ImageView? = null
    private var input_address: EditText? = null
    private var input_locality: EditText? = null
    private var input_city: EditText? = null
    private var input_zipcode: EditText? = null
    private var autoTextView: AutoCompleteTextView? = null
    private val addressTypeModels = ArrayList<AddressTypeModel>()
    private val stateModelArrayList = ArrayList<StateModel>()
    private val stringlist = ArrayList<String>()
    private var address_type_id: String? = null
    private var id_state: String? = null
    private var hometext: TextView? = null
    private var worktext: TextView? = null
    private var othertext: TextView? = null
    private var updatetext: TextView? = null
    private var type: String? = null
    private var homelayout: LinearLayout? = null
    private var worklayout: LinearLayout? = null
    private var otherlayout: LinearLayout? = null
    private var networkReceiver: NetworkReceiver? = null
    private var intentFilter: IntentFilter? = null
    private var isInternetconnected = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editaddress)
        type = intent.getStringExtra("type")
        address_type_id = intent.getStringExtra("address_id")

        networkReceiver = object : NetworkReceiver() {
            override fun onSmsReceived(s: Boolean) {
                isInternetconnected = s
            }
        }

        intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        intentFilter!!.priority = 1000

        domapping()

    }

    private fun domapping() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.title = ""
        val toolbartext = findViewById(R.id.toolbartext) as TextView
        toolbartext.text = "Edit Address"
        toolbartext.typeface = Utility.font(this@EditAddress, "medium")
        val cartlayout = findViewById(R.id.cartlayout) as LinearLayout
        cartlayout.visibility = View.INVISIBLE


        homelayout = findViewById(R.id.homelayout) as LinearLayout
        worklayout = findViewById(R.id.worklayout) as LinearLayout
        otherlayout = findViewById(R.id.otherlayout) as LinearLayout

        homeimage = findViewById(R.id.homeimage) as ImageView
        workimage = findViewById(R.id.workimage) as ImageView
        otherimage = findViewById(R.id.otherimage) as ImageView

        input_address = findViewById(R.id.input_address) as EditText
        input_locality = findViewById(R.id.input_locality) as EditText
        input_city = findViewById(R.id.input_city) as EditText
        input_zipcode = findViewById(R.id.input_zipcode) as EditText
        hometext = findViewById(R.id.hometext) as TextView
        worktext = findViewById(R.id.worktext) as TextView
        othertext = findViewById(R.id.othertext) as TextView
        autoTextView = findViewById(R.id.autocompleteEditTextView) as AutoCompleteTextView
        updatetext = findViewById(R.id.updatetext) as TextView

        autoTextView!!.threshold = 0

        input_address!!.typeface = Utility.font(this@EditAddress, "bold")
        input_locality!!.typeface = Utility.font(this@EditAddress, "bold")
        input_city!!.typeface = Utility.font(this@EditAddress, "bold")
        input_zipcode!!.typeface = Utility.font(this@EditAddress, "bold")
        worktext!!.typeface = Utility.font(this@EditAddress, "bold")
        hometext!!.typeface = Utility.font(this@EditAddress, "bold")
        othertext!!.typeface = Utility.font(this@EditAddress, "bold")
        autoTextView!!.typeface = Utility.font(this@EditAddress, "bold")
        updatetext!!.typeface = Utility.font(this@EditAddress, "regular")

        updatetext!!.setOnClickListener(this)
        homelayout!!.setOnClickListener(this)
        worklayout!!.setOnClickListener(this)
        otherlayout!!.setOnClickListener(this)
        if (type!!.equals("addnew", ignoreCase = true)) {
            updatetext!!.text = "Add Address"
            toolbartext.text = "Add Address"
        } else if (type!!.equals("edit", ignoreCase = true)) {
            updatetext!!.text = "Update Address"
            toolbartext.text = "Edit Address"
            input_address!!.setText(intent.getStringExtra("address1"))
            input_locality!!.setText(intent.getStringExtra("address2"))
            input_city!!.setText(intent.getStringExtra("city"))
            input_zipcode!!.setText(intent.getStringExtra("zipcode"))
            id_state = intent.getStringExtra("state_id")
            homelayout!!.visibility = View.GONE
            worklayout!!.visibility = View.GONE
            otherlayout!!.visibility = View.GONE

        }

        toolbar.setNavigationOnClickListener { finish() }

        autoTextView!!.onItemClickListener = this
       // autoTextView!!.setOnClickListener { autoTextView!!.showDropDown() }
        if (Utility.isNetworkAvailable(this@EditAddress) || isInternetconnected) {
            getAddress()
        }
        autoTextView!!.setOnTouchListener(View.OnTouchListener { v, event ->
            autoTextView!!.showDropDown()
            false
        })

    }

    private fun getAddress() {
        Utility.showDailog(this@EditAddress, resources.getString(R.string.pleasewait))
        val web = Download_web(this, OnTaskCompleted { response ->
            try {
                Utility.closeDialog()
                val jsonObject = JSONObject(response)
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equals("1", ignoreCase = true)) {
                        val jsonObject1 = jsonObject.getJSONObject("data")
                        val jsonArray = jsonObject1.getJSONArray("address_types")
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject2 = jsonArray.getJSONObject(i)
                            val id_address_type = jsonObject2.getString("id_address_type")
                            val name = jsonObject2.getString("name")
                            val addressTypeModel = AddressTypeModel(id_address_type, name)
                            addressTypeModels.add(addressTypeModel)
                        }

                        hometext!!.text = addressTypeModels[0].name
                        worktext!!.text = addressTypeModels[1].name
                        othertext!!.text = addressTypeModels[2].name
                        address_type_id = addressTypeModels[0].id_address_type

                        if (intent.getStringExtra("type").equals("edit", ignoreCase = true)) {
                            if (intent.getStringExtra("addresstype_id").equals(addressTypeModels[0].id_address_type, ignoreCase = true)) {
                                //homeimage.setBackgroundResource(R.drawable.circular_button_full);
                                changecolor(homeimage, intent.getStringExtra("addresstype_id"))
                            } else if (intent.getStringExtra("addresstype_id")
                                    .equals(addressTypeModels[1].id_address_type, ignoreCase = true)) {
                                //workimage.setBackgroundResource(circular_button_full);
                                changecolor(workimage, intent.getStringExtra("addresstype_id"))
                            } else if (intent.getStringExtra("addresstype_id")
                                    .equals(addressTypeModels[2].id_address_type, ignoreCase = true)) {
                                //otherimage.setBackgroundResource(circular_button_full);
                                changecolor(otherimage, intent.getStringExtra("addresstype_id"))
                            }
                            address_type_id = intent.getStringExtra("addresstype_id")

                            if (addressTypeModels[0].id_address_type.equals(address_type_id!!, ignoreCase = true)) {
                                homelayout!!.visibility = View.VISIBLE
                            } else if (addressTypeModels[1].id_address_type.equals(address_type_id!!, ignoreCase = true)) {
                                worklayout!!.visibility = View.VISIBLE
                            } else
                                otherlayout!!.visibility = View.VISIBLE
                        }

                    } else if (jsonObject.getString("status").equals("2", ignoreCase = true)) {
                        ClsGeneral.setPreferences(this@EditAddress, ClsGeneral.USERID, "")
                        startActivity(Intent(this@EditAddress, SendOtp::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@EditAddress, "" + jsonObject.getString("msg"), Toast
                                .LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            getstate()
            Log.e("Response : ", response)
        })
        web.setReqType("get")
        web.execute(WebServices.GETADDRESS)

    }

    private fun getstate() {

        val web = Download_web(this, OnTaskCompleted { response ->
            try {
                Utility.closeDialog()
                val jsonObject = JSONObject(response)
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equals("1", ignoreCase = true)) {
                        val jsonObject1 = jsonObject.getJSONObject("data")
                        val jsonArray = jsonObject1.getJSONArray("states")
                        for (i in 0..jsonArray.length() - 1) {
                            val jsonObject2 = jsonArray.getJSONObject(i)
                            val id_state = jsonObject2.getString("id_state")
                            val name = jsonObject2.getString("name")
                            val state_code = jsonObject2.getString("state_code")
                            val stateModel = StateModel(id_state, name, state_code)
                            stateModelArrayList.add(stateModel)
                            stringlist.add(name)
                        }

                        val arrayAdapter = ArrayAdapter(this@EditAddress,
                                android.R.layout.select_dialog_item, stringlist)


                        autoTextView!!.threshold = 0
                        autoTextView!!.setAdapter(arrayAdapter)
                        if (intent.getStringExtra("type").equals("edit", ignoreCase = true)) {
                            autoTextView!!.setText(intent.getStringExtra("state"))
                        }

                    } else if (jsonObject.getString("status").equals("2", ignoreCase = true)) {
                        ClsGeneral.setPreferences(this@EditAddress, ClsGeneral.USERID, "")
                        startActivity(Intent(this@EditAddress, SendOtp::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@EditAddress, "" + jsonObject.getString("msg"), Toast
                                .LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            Log.e("Response : ", response)
        })
        web.setReqType("get")
        web.execute(WebServices.GETSTATES)


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.homelayout -> changecolor(homeimage, addressTypeModels[0].id_address_type)
            R.id.worklayout -> changecolor(workimage, addressTypeModels[1].id_address_type)
            R.id.otherlayout -> changecolor(otherimage, addressTypeModels[2].id_address_type)
            R.id.updatetext -> {
                if (address_type_id!!.equals("", ignoreCase = true)) {
                    Toast.makeText(this, "Select address type", Toast.LENGTH_SHORT).show()
                    return
                }
                if (input_address!!.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                    Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show()
                    input_address!!.setText("")
                    return
                }
                if (input_locality!!.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                    Toast.makeText(this, "Enter Localiti", Toast.LENGTH_SHORT).show()
                    input_locality!!.setText("")
                    return
                }
                if (input_city!!.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                    Toast.makeText(this, "Enter City", Toast.LENGTH_SHORT).show()
                    input_city!!.setText("")
                    return
                }
                if (autoTextView!!.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                    Toast.makeText(this, "Enter State", Toast.LENGTH_SHORT).show()
                    autoTextView!!.setText("")
                    return
                }
                if (input_zipcode!!.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                    Toast.makeText(this, "Enter ZipCode", Toast.LENGTH_SHORT).show()
                    input_zipcode!!.setText("")
                    return
                } else {
                    if (updatetext!!.text.toString().equals("Add Address", ignoreCase = true)) {

                        saveAddress("add", "")

                        /*new SaveAddress().execute(WebServices.ADDADDRESS, input_address.getText().toString
							(), input_locality.getText().toString(), input_city.getText().toString(),
						    input_zipcode.getText().toString(), "add", "");*/
                    } else if (updatetext!!.text.toString().equals("Update Address", ignoreCase = true)) {

                        saveAddress("update", intent.getStringExtra("address_id"))

                        /*new SaveAddress().execute(WebServices.UPDATEDADDRESS, input_address.getText().toString
							(), input_locality.getText().toString(), input_city.getText().toString(),
						    input_zipcode.getText().toString(), "update", getIntent().getStringExtra("address_id"));*/
                    }

                }
            }
        }
    }

    private fun saveAddress(addupdate: String, addressId: String) {
        Utility.showDailog(this@EditAddress, resources.getString(R.string.pleasewait))
        val nameValuePairs = ArrayList<NameValuePair>()
        val web = Download_web(this, OnTaskCompleted { response ->
            try {
                Utility.closeDialog()
                val jsonObject = JSONObject(response)
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equals("1", ignoreCase = true)) {
                        Toast.makeText(this@EditAddress, "" + jsonObject.getString("msg"), Toast
                                .LENGTH_SHORT).show()
                        val address = input_address!!.text.toString() + ", " + input_locality!!.text.toString() +
                                ", " + input_city!!.text.toString() + ", " + input_zipcode!!.text.toString() + ", " + autoTextView!!.text.toString()

                        if (ClsGeneral.getPreferences(this@EditAddress, "from").equals
                        ("cart", ignoreCase = true)) {
                            startActivity(Intent(this@EditAddress, PaymentScreen::class.java).putExtra
                            ("address", address).putExtra("address_id", jsonObject.optString("data0")))
                            finish()
                        } else {
                            finish()
                        }

                    } else if (jsonObject.getString("status").equals("2", ignoreCase = true)) {
                        ClsGeneral.setPreferences(this@EditAddress, ClsGeneral.USERID, "")
                        startActivity(Intent(this@EditAddress, SendOtp::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@EditAddress, "" + jsonObject.getString("msg"), Toast
                                .LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            Log.e("Response : ", response)
        })

        nameValuePairs.add(BasicNameValuePair("session_token", ClsGeneral.getPreferences(this@EditAddress, ClsGeneral.SESSIONTOKEN)))
        nameValuePairs.add(BasicNameValuePair("user_id", ClsGeneral.getPreferences(this@EditAddress, ClsGeneral.USERID)))
        nameValuePairs.add(BasicNameValuePair("address1", input_address!!.text.toString()))
        nameValuePairs.add(BasicNameValuePair("address2", input_locality!!.text.toString()))
        nameValuePairs.add(BasicNameValuePair("city", input_city!!.text.toString()))
        nameValuePairs.add(BasicNameValuePair("pincode", input_zipcode!!.text.toString()))
        nameValuePairs.add(BasicNameValuePair("id_state", id_state))
        nameValuePairs.add(BasicNameValuePair("address_type", address_type_id))
        nameValuePairs.add(BasicNameValuePair("customer_address_id", addressId))

        if (addupdate.equals("add", ignoreCase = true)) {
            web.setReqType("post")
        } else if (addupdate.equals("update", ignoreCase = true)) {
            web.setReqType("put")
        }
        web.setData(nameValuePairs)
        web.execute(WebServices.ADDADDRESS)


    }


    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        try {
            val tempname = "" + parent.getItemAtPosition(position)
            for (i in stateModelArrayList.indices) {
                if (tempname.equals(stateModelArrayList[i].name, ignoreCase = true)) {
                    id_state = stateModelArrayList[i].id_state
                }
            }

        } catch (e: Exception) {
            Log.e("Location Toast", e.message)
        }

    }


    private fun changecolor(hmeimage: ImageView?, id_address_type: String) {
        //if (addressTypeModels.size() > 0) {
        homeimage!!.setBackgroundResource(R.drawable.circular_button_half)
        workimage!!.setBackgroundResource(R.drawable.circular_button_half)
        otherimage!!.setBackgroundResource(R.drawable.circular_button_half)

        hmeimage!!.setBackgroundResource(circular_button_full)
        address_type_id = id_address_type
        //}
    }
}
