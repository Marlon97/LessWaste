package com.mbvp.lesswaste

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.home_post.view.*

class AdaptadorPost(private val contexto: Context, var arrPosts: Array<PostModel>): RecyclerView.Adapter<AdaptadorPost.HomePost>() {


    inner class HomePost(var vistaPost: View):RecyclerView.ViewHolder(vistaPost){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePost {
        val vista = LayoutInflater.from(contexto).inflate(R.layout.home_post, parent, false)
        return HomePost(vista)
    }

    override fun getItemCount(): Int {
        return arrPosts.size
    }

    override fun onBindViewHolder(holder: HomePost, position: Int) {
        val post = arrPosts[position] //arrPaises.get(position)
        holder.vistaPost.postD_txt_title.text = post.caption
        holder.vistaPost.postD_txt_likes.text = post.likes.toString()+" likes"
        holder.vistaPost.postD_txt_username.text = post.caption
        Glide.with(holder.itemView.context).load(post.imageUrl).into(holder.vistaPost.postD_img_photo)
        //EVENTO
        //holder.vistaRenglon.setOnClickListener {
        //    listener?.itemClicked(position)
        //}
    }
}