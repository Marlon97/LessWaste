package com.mbvp.lesswaste

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_add.*

class Add : AppCompatActivity() {
    var PICK_IMAGE_FROM_ALBUM = 0
    var storage : FirebaseStorage? = null
    var photoUri : Uri? = null
    var auth : FirebaseAuth? = null
    var firestore : FirebaseFirestore? = null
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance().reference

        var photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent,PICK_IMAGE_FROM_ALBUM)

        btnAddAgregar.setOnClickListener { view ->
            contentUpload()
        }

        btnAddCancelar.setOnClickListener { view ->
            val intLess = Intent(this, Less::class.java)
            startActivity(intLess)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_FROM_ALBUM){
            if(resultCode == Activity.RESULT_OK){
                photoUri = data?.data
                ivAddImagen.setImageURI(photoUri)

            }else{
                val intLess = Intent(this, Less::class.java)
                startActivity(intLess)
                finish()

            }
        }
    }

    fun contentUpload(){

        var timestamp = System.currentTimeMillis()/1000
        var imageFileName = "NDR_" + timestamp

        var storageRef = storage?.reference?.child("post_images")?.child(imageFileName)

        storageRef?.putFile(photoUri!!)?.continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
            return@continueWithTask storageRef.downloadUrl
        }?.addOnSuccessListener { uri ->
            var postModel = PostModel()
            postModel.imageUrl = uri.toString()
            postModel.ownerUid = auth?.currentUser?.uid
            postModel.caption = etAddTitulo.text.toString()
            postModel.creationDate = timestamp
            postModel.likes = 0
            postModel.ingredientes = etAddIngredientes.text.toString()
            postModel.linkYT = etAddLinkYT.text.toString()
            postModel.etiquetas = etAddEtiquetas.text.toString()
            postModel.instrucciones = etAddPasos.text.toString()

            val key = database.child("posts").push().key
            if (key != null) {
                database?.child("posts").child(key).setValue(postModel)
                auth?.currentUser?.uid?.let { database?.child("user-posts").child(it).child(key).setValue(1) }
                auth?.currentUser?.uid?.let { database?.child("user-feed").child(it).child(key).setValue(1) }

            }

            setResult(Activity.RESULT_OK)
            val intLess = Intent(this, Less::class.java)
            startActivity(intLess)
            finish()
        }

    }


}