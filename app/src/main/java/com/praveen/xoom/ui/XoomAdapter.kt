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

    /**
     * Usually this method is used when we have to do a post/delete of user's favorites and then roll back or keep the changes based on the response
     * We don't do a network call in this case so this method might not be needed (since we do a toggle and not worry about updating it on the server)
     * Added this method here for enhancement sake
     */
    override fun onBindViewHolder(holder: XoomViewHolder, position: Int, payloads: MutableList<Any>) {

        when(payloads.isNotEmpty()){
            true -> {
                if(PAYLOAD_FAVORITE in payloads){
                    holder.favButton.background = mContext.getDrawable(R.drawable.ic_star)
                }else if(PAYLOAD_NO_FAVORITE in payloads) {
                    holder.favButton.background = mContext.getDrawable(R.drawable.ic_star_border)
                }
            }
            else -> super.onBindViewHolder(holder, position, payloads)
        }
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
            true -> holder.favButton.setBackgroundResource(R.drawable.ic_star)
            false -> holder.favButton.setBackgroundResource(R.drawable.ic_star_border)
        }

        holder.favButton.setOnClickListener {

            when(item.isFavorite) {
                true -> {
                    item.isFavorite = false
                    listener.removeFavorite(item.countryCode)
                    notifyItemChanged(holder.adapterPosition, PAYLOAD_NO_FAVORITE)
                }
                false -> {
                    item.isFavorite = true
                    listener.saveFavorite(item.countryCode)
                    notifyItemChanged(holder.adapterPosition, PAYLOAD_FAVORITE)
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
        const val PAYLOAD_FAVORITE = "favorite"
        const val PAYLOAD_NO_FAVORITE = "not_favorite"
    }
}