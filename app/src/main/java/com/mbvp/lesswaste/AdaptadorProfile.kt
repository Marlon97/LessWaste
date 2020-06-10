package com.mbvp.lesswaste

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class AdaptadorProfile(private val contexto: Context, var arrPosts: Array<PostModel>): RecyclerView.Adapter<AdaptadorProfile.ProfilePost>() {

    inner class ProfilePost(var imageview: ImageView):RecyclerView.ViewHolder(imageview){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilePost {
        //var width = contexto.resources.displayMetrics.widthPixels / 3
        var width = parent.resources.displayMetrics.widthPixels

        var imageview = ImageView(parent.context)
        imageview.layoutParams = LinearLayoutCompat.LayoutParams(width,width)
        return ProfilePost(imageview)
    }

    override fun getItemCount(): Int {
        return arrPosts.size
    }

    override fun onBindViewHolder(holder: ProfilePost, position: Int) {
        var imageview = holder.imageview
        Glide.with(holder.itemView.context).load(arrPosts[position].imageUrl).apply(RequestOptions().centerCrop()).into(imageview)
    }


}