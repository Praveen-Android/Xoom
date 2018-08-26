package com.praveen.xoom.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.praveen.xoom.R
import com.praveen.xoom.XoomApplication
import com.praveen.xoom.database.CountryDetails
import javax.inject.Inject

class XoomAdapter(private val listener: OnFavoriteSelected, private val list: List<CountryDetails>): RecyclerView.Adapter<XoomAdapter.XoomViewHolder>() {

    @Inject
    lateinit var mContext: Context

    init {
        XoomApplication.appComponent.inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XoomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_country_item, parent, false)
        return XoomViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: XoomViewHolder, position: Int) {
        val item = list[position]

        item.countryCode?:return

        val urlString = "https://www.countryflags.io/${item.countryCode}/flat/$IMAGE_SIZE.png"

        Glide.with(mContext)
                .load(urlString)
                .into(holder.countryImage)

        holder.countryName.text = item.countryName

        when(item.isFavorite) {
            true -> holder.favButton.setBackgroundResource(R.drawable.ic_baseline_star_24px)
            false -> holder.favButton.setBackgroundResource(R.drawable.ic_baseline_star_border_24px)
        }

        holder.favButton.setOnClickListener {

            when(item.isFavorite) {
                true -> {
                    item.isFavorite = false
                    holder.favButton.setBackgroundResource(R.drawable.ic_baseline_star_border_24px)
                    listener.removeFavorite(item)
                }
                false -> {
                    item.isFavorite = true
                    holder.favButton.setBackgroundResource(R.drawable.ic_baseline_star_24px)
                    listener.saveFavorite(item)
                }
            }
        }
    }

    class XoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var countryImage: ImageView = view.findViewById(R.id.country_flag_view)
        var countryName: TextView = view.findViewById(R.id.country_name_view)
        var favButton: ImageButton = view.findViewById(R.id.fav_button_view)
    }

    companion object {
        const val IMAGE_SIZE = 64
    }
}