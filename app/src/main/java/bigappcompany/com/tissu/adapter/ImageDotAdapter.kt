package bigappcompany.com.tissu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import bigappcompany.com.tissu.R
import bigappcompany.com.tissu.activity.ProductDescription
import bigappcompany.com.tissu.model.DescriptinSliderModel
import com.squareup.picasso.Picasso
import java.util.*

/**
 * @author Shankar
 * @created on 20 Sep 2017 at 3:25 PM
 */
class ImageDotAdapter(internal var context  : Context,internal var res :ArrayList<DescriptinSliderModel>  ,internal var
count:Int, internal var productDescription: ProductDescription) : RecyclerView.Adapter<ImageDotAdapter.ViewHolder>() {

    internal var selected = 0
    internal var previous = 0

    fun setSelected(selected: Int) {
        this.previous = this.selected
        this.selected = selected
        notifyItemChanged(previous)
        notifyItemChanged(selected)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.imagedesc_pager_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("inside", "change")

        Picasso.with(context).load(res[position].getProduct_image()).into(holder.img_dot)

        if (selected == position)
            holder.img_dot_back!!.visibility = View.VISIBLE
        else
            holder.img_dot_back!!.visibility = View.GONE
        holder.itemView!!.setOnClickListener(View.OnClickListener {
            productDescription.pos(position)
        })
    }


    override fun getItemCount(): Int {
        return count
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var img_dot: ImageView? = null
        var img_dot_back: ImageView? = null

        init {

            img_dot = itemView.findViewById(R.id.img_dot) as ImageView
            img_dot_back = itemView.findViewById(R.id.img_dot_back) as ImageView


        }


    }

}

