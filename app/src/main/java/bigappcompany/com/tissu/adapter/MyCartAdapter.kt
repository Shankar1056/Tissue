package bigappcompany.com.tissu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import bigappcompany.com.tissu.R
import bigappcompany.com.tissu.Utilz.Utility
import bigappcompany.com.tissu.activity.MyCart
import bigappcompany.com.tissu.model.MyCartModel
import com.squareup.picasso.Picasso

/**
 * @author Samuel Robert <sam></sam>@spotsoon.com>
 * @created on 30 Jun 2017 at 4:13 PM
 */

class MyCartAdapter(private val context: Context, private val myCartModels: List<MyCartModel>, private val cart: MyCart) : RecyclerView.Adapter<MyCartAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cartprod_name: TextView
        var cartamount: TextView
        var counttext: TextView
        var cartimage: ImageView
        var delete: ImageView
        var plus: ImageView
        var minus: ImageView
        var cartprod_desc:TextView

        init {
            cartprod_name = view.findViewById(R.id.cartprod_name) as TextView
            cartamount = view.findViewById(R.id.cartamount) as TextView
            counttext = view.findViewById(R.id.counttext) as TextView
            cartimage = view.findViewById(R.id.cartimage) as ImageView
            delete = view.findViewById(R.id.delete) as ImageView
            plus = view.findViewById(R.id.plus) as ImageView
            minus = view.findViewById(R.id.minus) as ImageView
            cartprod_desc = view.findViewById(R.id.cartprod_desc) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.mycart_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sa = myCartModels[position]
        holder.cartprod_name.typeface = Utility.font(context, "medium")
        holder.counttext.typeface = Utility.font(context, "medium")
        holder.cartprod_desc.typeface = Utility.font(context, "medium")
        holder.cartamount.typeface = Utility.font(context, "bold")

        Picasso.with(context).load(sa.cart_product_image).into(holder.cartimage)
        holder.cartprod_name.text = sa.cart_product_name
        try {
           // holder.cartamount.text = "" + gettotalprice(sa.cart_products_qty, sa.cart_product_price)
            holder.cartamount.text = (sa.cart_product_price)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        holder.counttext.text = sa.cart_products_qty

        holder.plus.tag = position
        holder.minus.tag = position

        holder.plus.setOnClickListener { v ->
            val clickedPos = (v.tag as Int).toInt()
            val cntt = sa.cart_products_qty
            var cnt = Integer.parseInt(cntt)
            if (cnt == 20) {
                Toast.makeText(context, "max 20 items can be added ", Toast.LENGTH_SHORT).show()
            } else {
                cnt = cnt + 1
            }
            myCartModels[clickedPos].cart_products_qty = "" + cnt
            cart.updateplusminus(position, "" + cnt)
        }
        holder.minus.setOnClickListener { v ->
            val clickedPos = (v.tag as Int).toInt()
            val cntt = sa.cart_products_qty
            var cnt = Integer.parseInt(cntt)
            cnt = cnt - 1
            if (cnt == 0 || cnt == -1) {
                cart.detele(position)
            } else {
                myCartModels[clickedPos].cart_products_qty = "" + cnt
                cart.updateplusminus(position, "" + cnt)
            }
        }

        holder.delete.setOnClickListener { cart.detele(position) }
    }

    private fun gettotalprice(cart_products_qty: String?, cart_product_price: String): Int {

        var quantity = 0
        var price = 0
        try {
            quantity = Integer.parseInt(cart_products_qty)
            price = Integer.parseInt(cart_product_price)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return totalprice(quantity, price)
    }

    private fun totalprice(quantity: Int, price: Int): Int {
        return quantity * price
    }

    override fun getItemCount(): Int {
        return myCartModels.size
    }
}
