package bigappcompany.com.tissu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView

import bigappcompany.com.tissu.R
import bigappcompany.com.tissu.Utilz.Utility
import bigappcompany.com.tissu.activity.MyAddress
import bigappcompany.com.tissu.model.SavedAddressModel

/**
 * @author Samuel Robert <sam></sam>@spotsoon.com>
 * @created on 29 Jun 2017 at 11:45 AM
 */

class SavedAddressAdapter(private val context: Context, private val saveaddress: List<SavedAddressModel>, private val from: String, private val myAddress: MyAddress) : RecyclerView.Adapter<SavedAddressAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var address: TextView
        var addresstype: TextView
        var addressdesc: TextView
        var edit: TextView
        var delete: TextView
        var checkbox: CheckBox

        init {
            address = view.findViewById(R.id.address) as TextView
            addresstype = view.findViewById(R.id.addresstype) as TextView
            addressdesc = view.findViewById(R.id.addressdesc) as TextView
            edit = view.findViewById(R.id.edit) as TextView
            delete = view.findViewById(R.id.delete) as TextView
            checkbox = view.findViewById(R.id.checkbox) as CheckBox
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.myaddress_row, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sa = saveaddress[position]
        holder.address.typeface = Utility.font(context, "medium")
        holder.addresstype.typeface = Utility.font(context, "bold")
        holder.addressdesc.typeface = Utility.font(context, "medium")
        holder.edit.typeface = Utility.font(context, "bold")
        holder.delete.typeface = Utility.font(context, "bold")
        holder.addresstype.text = sa.address_type_name
        holder.addressdesc.text = sa.address1 + ", " + sa.address2 + ", " + sa.city + ", " + sa
                .pincode + ", " + sa.state_name

        holder.checkbox.isChecked = sa.isSelected
        holder.checkbox.tag = position
        if (position == 0 && saveaddress[0].isSelected && holder.checkbox.isChecked) {
            lastChecked = holder.checkbox
            lastCheckedPos = 0
        }

        if (from.equals("main", ignoreCase = true)) {
            holder.checkbox.visibility = View.GONE
        }
        holder.edit.setOnClickListener { myAddress.edit(position) }
        holder.delete.setOnClickListener { myAddress.delete(position) }
        holder.checkbox.setOnClickListener { v ->
            val cb = v as CheckBox
            val clickedPos = (cb.tag as Int).toInt()

            if (cb.isChecked) {
                if (lastChecked != null) {
                    lastChecked!!.isChecked = false
                    saveaddress[lastCheckedPos].isSelected = false
                }

                lastChecked = cb
                lastCheckedPos = clickedPos
                myAddress.sendpos("true", holder.addressdesc.text.toString(), saveaddress[clickedPos].address_id)
            } else {
                lastChecked = null
                myAddress.sendpos("false", "", "")
            }

            saveaddress[clickedPos].isSelected = cb.isChecked
        }
    }

    override fun getItemCount(): Int {
        return saveaddress.size
    }

    companion object {
        private var lastChecked: CheckBox? = null
        private var lastCheckedPos = 0
    }
}
