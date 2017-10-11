package bigappcompany.com.tissu.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import bigappcompany.com.tissu.R
import bigappcompany.com.tissu.Utilz.Utility


/**
 * @author Samuel Robert <sam></sam>@spotsoon.com>
 * @created on 03 Jul 2017 at 2:57 PM
 */

class SuccessPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.success_page)
        val order_total = intent.getStringExtra("order_total")
        val amount = findViewById(R.id.amount) as TextView
        val orderplaced = findViewById(R.id.orderplaced) as TextView
        val successfully = findViewById(R.id.successfully) as TextView
        val total = findViewById(R.id.total) as TextView
        val thankyoutext = findViewById(R.id.thankyoutext) as TextView

        amount.typeface = Utility.font(this@SuccessPage,"bold")
        orderplaced.typeface = Utility.font(this@SuccessPage,"medium")
        successfully.typeface = Utility.font(this@SuccessPage,"bold")
        total.typeface = Utility.font(this@SuccessPage,"bold")
        thankyoutext.typeface = Utility.font(this@SuccessPage,"medium")


        amount.text = ""+order_total
        findViewById(R.id.thankyou).setOnClickListener {
            startActivity(Intent(this@SuccessPage, MainActivity::class.java))
            finish()
        }


    }
}
