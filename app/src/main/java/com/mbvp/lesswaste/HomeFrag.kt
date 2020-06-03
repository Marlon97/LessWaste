package com.mbvp.lesswaste

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFrag : Fragment() {
    var adaptadorPost: AdaptadorPost? = null
    private lateinit var database: FirebaseDatabase
    private lateinit var referencia: DatabaseReference
    lateinit var auth : FirebaseAuth;
    lateinit var arrPostModel: MutableList<PostModel>
    private lateinit var arrFeedIds: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_home,container,false)
        // Inflate the layout for this fragment
        val layout = LinearLayoutManager(context)
        layout.orientation = LinearLayoutManager.VERTICAL
        view.homePostRec.layoutManager = layout
        adaptadorPost = context?.let { AdaptadorPost(it, PostModel.arrPostModel) }
        view.homePostRec.adapter = adaptadorPost
        //configurarRecycler()
        arrFeedIds = mutableListOf()
        arrPostModel = mutableListOf()
        return view
    }

    private fun configurarRecycler() {
        //val layout = LinearLayoutManager(context)
        //layout.orientation = LinearLayoutManager.VERTICAL
        //view.homePostRec.layoutManager = layout
        adaptadorPost = context?.let { AdaptadorPost(it, PostModel.arrPostModel) }
        view?.homePostRec?.adapter = adaptadorPost
        //val divisor = DividerItemDecoration(context, layout.orientation)
        //homePostRec.addItemDecoration(divisor)
    }

    override fun onStart(){
        super.onStart()
        leerDatos()
    }

    private fun leerDatos() {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        referencia = auth?.currentUser?.uid?.let { database.getReference("/user-feed/$it") }!!
        referencia.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                arrFeedIds.clear()
                arrPostModel.clear()
                for(registro in snapshot.children){
                    val userFeed = registro.key
                    arrFeedIds.add("${userFeed.toString()}")
                    //Log.i("FEEDID",arrFeedIds.toString())
                }
                for(feedId in arrFeedIds){
                    val postId = database.getReference("/posts").orderByKey().equalTo(feedId)
                    postId.addValueEventListener(object: ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {

                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            //arrPostModel.clear()
                            for(registro in p0.children){
                                val pm = registro.getValue(PostModel::class.java)
                                if (pm != null) {
                                    arrPostModel.add(pm)
                                    //Log.i("For2ARR",arrPostModel.toString())
                                }
                            }
                            adaptadorPost?.arrPosts = arrPostModel.toTypedArray()
                            adaptadorPost?.notifyDataSetChanged()
                        }

                    })
                }
            }
        })
    }

}