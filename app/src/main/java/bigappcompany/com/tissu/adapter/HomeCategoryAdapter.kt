package bigappcompany.com.tissu.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import bigappcompany.com.tissu.R
import bigappcompany.com.tissu.Utilz.Utility
import bigappcompany.com.tissu.activity.MainActivity
import bigappcompany.com.vegan.model.ProductListModel
import com.squareup.picasso.Picasso

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 15 Sep 2017 at 3:17 PM
 */
 class HomeCategoryAdapter() : RecyclerView.Adapter<HomeCategoryAdapter.MyViewHolder>(), Parcelable {
    private var myprodModels: List<ProductListModel>? = null
    private var context : Context? = null
    private var main: MainActivity? = null

    constructor(parcel: Parcel) : this()

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var productname: TextView
        var cat_desc: TextView
        var amount: TextView
        var addcarttext: TextView
        var count: TextView
        var addingtext: TextView
        var productimage: ImageView
        var plusbutton: ImageView
        var minusbutton: ImageView
        var plusminuslayout: LinearLayout
        var addcartlayout: LinearLayout
        var namedesclayout: LinearLayout

        init {
            productname = view?.findViewById(R.id.productname) as TextView
            cat_desc = view?.findViewById(R.id.cat_desc)as TextView
            amount = view?.findViewById(R.id.amount) as TextView
            addcarttext = view?.findViewById(R.id.addcarttext) as TextView
            count = view?.findViewById(R.id.count) as TextView
            addingtext = view?.findViewById(R.id.addingtext)as TextView
            productimage = view?.findViewById(R.id.productimage)as ImageView
            plusbutton = view?.findViewById(R.id.plusbutton)as ImageView
            minusbutton = view?.findViewById(R.id.minusbutton)as ImageView
            plusminuslayout = view?.findViewById(R.id.plusminuslayout) as LinearLayout
            addcartlayout = view?.findViewById(R.id.addcartlayout) as LinearLayout
            namedesclayout = view?.findViewById(R.id.namedesclayout) as LinearLayout
        }
    }



    constructor(context: Context, myprodModels: List<ProductListModel>, mainAct: MainActivity) : this() {
        this.context=context
        this.myprodModels=myprodModels
        this.main=mainAct
    }


    override fun getItemCount(): Int {
        return myprodModels!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sa = myprodModels!!.get(position)
        holder.productname.setTypeface(Utility.font(context, "medium"))
        holder.cat_desc.setTypeface(Utility.font(context, "medium"))
        holder.count.setTypeface(Utility.font(context, "medium"))
        holder.addcarttext.setTypeface(Utility.font(context, "medium"))
        holder.addingtext.setTypeface(Utility.font(context, "medium"))
        holder.amount.setTypeface(Utility.font(context, "bold"))

        Picasso.with(context).load(sa.products_image).into(holder.productimage)
        holder.productname.setText(sa.product_name)
        holder.cat_desc.setText(Html.fromHtml(sa.prod_short_desc))
        holder.amount.setText(sa.price_with_tax)
        holder.count.setText(sa.user_selected_qty)

        if (Integer.parseInt(sa.user_selected_qty) >= 1) {
            holder.addcartlayout.setVisibility(View.GONE)
            holder.plusminuslayout.visibility = View.VISIBLE
        } else if (Integer.parseInt(sa.user_selected_qty) == 0) {
            holder.addcartlayout.setVisibility(View.VISIBLE)
            holder.plusminuslayout.visibility = View.GONE
        }

        if (sa.addingtext.equals("0"))
        {
            holder.addingtext.visibility = View.INVISIBLE
        }


        holder.addcartlayout.setOnClickListener { v ->
            val clickedPos = (v.tag as Int).toInt()
            holder.addingtext.visibility=View.VISIBLE
            holder.addingtext.text = "Adding"
            addcartmethod(clickedPos, holder.count.text.toString(), position, "add")
        }
        holder.plusbutton.setOnClickListener { v ->
            val clickedPos = (v.tag as Int).toInt()
            holder.addingtext.visibility=View.VISIBLE
            holder.addingtext.text = "Adding"
            addcartmethod(clickedPos, holder.count.text.toString(), position, "plus")
        }

        holder.minusbutton.setOnClickListener { v ->
            val clickedPos = (v.tag as Int).toInt()
            holder.addingtext.visibility=View.VISIBLE
            holder.addingtext.text = "Deleting"
            deletecartmethod(clickedPos, holder.count.text.toString(), position)
        }

        holder.productimage.setOnClickListener { main!!.pos(position,holder.count.text.toString()) }
        holder.namedesclayout.setOnClickListener { main!!.pos(position,holder.count.text.toString()) }


        holder.productname.setTag(position)
        holder.productimage.setTag(position)
        holder.amount.setTag(position)
        holder.cat_desc.setTag(position)
        holder.addcartlayout.setTag(position)
        holder.plusbutton.setTag(position)
        holder.minusbutton.setTag(position)



    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent!!.getContext())
                .inflate(R.layout.category_row, parent, false)

        return MyViewHolder(itemView)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeCategoryAdapter> {
        override fun createFromParcel(parcel: Parcel): HomeCategoryAdapter {
            return HomeCategoryAdapter(parcel)
        }

        override fun newArray(size: Int): Array<HomeCategoryAdapter?> {
            return arrayOfNulls(size)
        }
    }

    private fun addcartmethod(clickedPos: Int, s: String, position: Int, whichtoperform: String) {
        var cnt = Integer.parseInt(s)
        if (cnt == 20) {
            Toast.makeText(context, "max 20 items can be added ", Toast.LENGTH_SHORT).show()
        } else {
            cnt = cnt + 1
        }
        myprodModels!![clickedPos].user_selected_qty = "" + cnt
        main!!.updateplusminus(position, "" + cnt, whichtoperform)
       // notifyDataSetChanged()
    }

    private fun deletecartmethod(clickedPos: Int, s: String, position: Int) {
        var cnt = Integer.parseInt(s)
        cnt = cnt - 1

        myprodModels!![clickedPos].user_selected_qty = "" + cnt
        main!!.updateplusminus(position, "" + cnt, "minus")
       // notifyDataSetChanged()
    }

}