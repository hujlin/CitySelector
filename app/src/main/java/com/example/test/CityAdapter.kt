package com.example.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.promeg.pinyinhelper.Pinyin
import kotlinx.android.synthetic.main.adapter_item_layout.view.*

class CityAdapter : RecyclerView.Adapter<CityViewHolder>() {

    val data = mutableListOf<City>()

    var onItemClickListener: ((Int, City) -> Unit)? = null

    fun setData(data: List<City>) {
        this.data.clear()
        data.forEach {
            it.character = Pinyin.toPinyin(it.cityName[0])[0].toString()
        }
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_layout, parent, false)
        return CityViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.tvCityName.text = data[position].cityName
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(position, data[position])
        }
    }

}

class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvCityName: TextView = itemView.tvCityName
}