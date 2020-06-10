package com.mbvp.lesswaste

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.userP_rv_grid
import kotlinx.android.synthetic.main.home_post.view.*

class ProfileFrag : Fragment() {
    var adaptadorProfile: AdaptadorProfile? = null
    private lateinit var database: FirebaseDatabase
    private lateinit var referencia: DatabaseReference
    lateinit var auth : FirebaseAuth;
    lateinit var arrPostModel: MutableList<PostModel>
    private lateinit var arrFeedIds: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_profile,container,false)
        // Inflate the layout for this fragment
        val layout = LinearLayoutManager(context)
        layout.orientation = LinearLayoutManager.VERTICAL
        view.userP_rv_grid.layoutManager = layout
        adaptadorProfile = context?.let { AdaptadorProfile(it, PostModel.arrPostModel) }
        view.userP_rv_grid.adapter = adaptadorProfile
        arrFeedIds = mutableListOf()
        arrPostModel = mutableListOf()
        val divisor = DividerItemDecoration(context, layout.orientation)
        view?.userP_rv_grid?.addItemDecoration(divisor)
        return view
    }

    override fun onStart(){
        super.onStart()
        //leerHeader()
        leerDatos()
    }

    private fun leerHeader() {
        //seguidores, siguiendo, nombre, foto
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        referencia = auth?.currentUser?.uid?.let { database.getReference("/users/$it") }!!
        referencia.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(registro in snapshot.children){
                    Log.i("1_1_1_1_1_1_1_1_1_1_1","X_X_X_X_X_X_X_X_X_X_X")
                    Log.i("PROFILE-ch",registro.child("profileImageUrl").toString())
                    Log.i("2_2_2_2_2_2_2_2_2_2_2","X_X_X_X_X_X_X_X_X_X_X")
                    Log.i("PROFILE",registro.toString())
                    Log.i("3_3_3_3_3_3_3_3_3_3_3","X_X_X_X_X_X_X_X_X_X_X")
                    val userProf = registro.child("profileImageUrl")
                    (context)?.let { view?.userP_img_profilePhoto?.let { it1 ->
                        Glide.with(it).load(userProf).into(
                            it1
                        )
                    } }
                }
            }

        })
    }

    private fun leerDatos() {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        referencia = auth?.currentUser?.uid?.let { database.getReference("/user-posts/$it") }!!
        referencia.addValueEventListener(object: ValueEventListener {
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
                    postId.addValueEventListener(object: ValueEventListener {
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
                            view?.userP_txt_posts?.text   = arrPostModel.size.toString()
                            adaptadorProfile?.arrPosts = arrPostModel.toTypedArray()
                            adaptadorProfile?.notifyDataSetChanged()
                        }

                    })
                }
                //MMMMM
                //view?.userP_txt_posts?.text   = arrPostModel.size.toString()
            }
        })
    }

}