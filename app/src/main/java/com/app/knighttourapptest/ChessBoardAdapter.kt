package com.app.knighttourapptest

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import java.util.ArrayList

class ChessBoardsAdapter(
    internal var models: ArrayList<ChessBoardModel>,
    internal var mContext: Context,
    internal var listner: OnBoardClick) : RecyclerView.Adapter<ChessBoardsAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return models.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(mContext).inflate(R.layout.recycle_item_board, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = models[position]

//        val pos = model. * 8 + model.getJ() + 1

        holder.tvPath.visibility = View.GONE


        if (model.selected) {
            holder.ivKnight.visibility = View.VISIBLE
        } else {
            holder.ivKnight.visibility = View.GONE

        }



        if (model.j - model.i == 0 || (model.j - model.i) % 2 == 0) {
            holder.relBg.setBackgroundResource(R.drawable.back_ground_white)
        } else {
            holder.relBg.setBackgroundResource(R.drawable.back_ground_black)
        }
        if (model.available) {
            holder.relBg.setBackgroundResource(R.drawable.back_ground_green)
        }
        if (model.isVisited) {
            holder.tvPath.visibility = View.VISIBLE
            holder.tvPath.text = model.count.toString()
            holder.relBg.setBackgroundResource(R.drawable.back_ground_yellow)
        }




        holder.relBg.setOnClickListener {
            if (model.selected) {
                listner.onChessBoardClick(position, model)
            } else if (model.available) {
                listner.onAvailableBoardClick(position, model)
            }
        }


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var relBg: RelativeLayout = itemView.findViewById(R.id.rel_board_bg)
        internal var ivKnight: ImageView = itemView.findViewById(R.id.iv_knight)
        internal var tvPath: TextView = itemView.findViewById(R.id.tv_path)

    }
}
