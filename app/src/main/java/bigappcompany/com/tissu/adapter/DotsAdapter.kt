package bigappcompany.com.tissu.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import bigappcompany.com.tissu.R


public class DotsAdapter(internal var count: Int) : RecyclerView.Adapter<DotsAdapter.ViewHolder>() {

    internal var selected = 0
    internal var previous = 0

    fun setSelected(selected: Int) {
        this.previous = this.selected
        this.selected = selected
        notifyItemChanged(previous)
        notifyItemChanged(selected)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_dot, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("inside", "change")
        if (selected == position)
            holder.img_dot.setImageResource(R.drawable.round_rank_highlight)
        else
            holder.img_dot.setImageResource(android.R.color.transparent)


    }


    override fun getItemCount(): Int {
        return count
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var img_dot: ImageView

        init {

            img_dot = itemView?.findViewById(R.id.img_dot) as ImageView


        }


    }

}

