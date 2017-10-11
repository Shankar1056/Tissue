package bigappcompany.com.tissu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView

import bigappcompany.com.tissu.R
import bigappcompany.com.tissu.Utilz.Utility
import bigappcompany.com.tissu.activity.PaymentScreen
import bigappcompany.com.tissu.model.PaymentMethodModel


/**
 * @author Samuel Robert <sam></sam>@spotsoon.com>
 * @created on 01 Jul 2017 at 12:07 PM
 */

class PaymentOptionAdapter(private val context: Context, private val list: List<PaymentMethodModel>, private val paymentScreen: PaymentScreen) : RecyclerView.Adapter<PaymentOptionAdapter.MyViewHolder>() {
    var mSelectedItem = -1

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var debitcredit: TextView
        var dcradio: RadioButton

        init {
            debitcredit = view.findViewById(R.id.debitcredit) as TextView
            dcradio = view.findViewById(R.id.dcradio) as RadioButton

            val clickListener = View.OnClickListener {
                mSelectedItem = adapterPosition
                notifyItemRangeChanged(0, list.size)
                paymentScreen.sendpos(mSelectedItem)
            }
            itemView.setOnClickListener(clickListener)
            dcradio.setOnClickListener(clickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.payment_screen_row, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.debitcredit.typeface = Utility.font(context, "medium")
        holder.dcradio.isChecked = position == mSelectedItem
        holder.debitcredit.text = list[position].paymentmethods_name

    }

    override fun getItemCount(): Int {
        return list.size
    }
}
