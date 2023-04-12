package com.example.shift.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shift.R
import com.example.shift.databinding.BinItemBinding
import com.example.shift.db.BinItem
import kotlin.collections.ArrayList

class BinAdapter : RecyclerView.Adapter<BinAdapter.BinHolder>() {


    private val bins = ArrayList<BinItem>()
    private lateinit var onItemClickListener: OnItemClickListener
    fun interface OnItemClickListener{
        fun onItemClick(binNum : Long)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    class BinHolder(view: View) : RecyclerView.ViewHolder(view){

        private val itemBinding = BinItemBinding.bind(view)

        fun bind(binItem: BinItem, onItemClickListener: OnItemClickListener){
            itemBinding.apply {
                tvBinNum.text = binItem.binNum.toString()
                tvTime.text = binItem.searchDate
            }
            itemView.setOnClickListener { onItemClickListener.onItemClick(binNum = binItem.binNum) }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bin_item, parent, false)
        return BinHolder(view)
    }

    override fun getItemCount(): Int = bins.size


    override fun onBindViewHolder(holder: BinHolder, position: Int) {
        holder.bind(bins[position], onItemClickListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(binItem: BinItem){
        bins.add(binItem)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItemsList(bins : ArrayList<BinItem>){
        this.bins.addAll(bins)
        notifyDataSetChanged()
    }
}