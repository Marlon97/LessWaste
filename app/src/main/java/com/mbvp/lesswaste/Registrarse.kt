package com.mbvp.lesswaste

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registrarse.*

class Registrarse : AppCompatActivity() {
    lateinit var auth : FirebaseAuth;
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        tvLogin.setOnClickListener { view ->
            val intLogin = Intent(this, Login::class.java)
            startActivity(intLogin)
            finish()
        }

        btnRegistro.setOnClickListener { view ->
            val email = etEmailReg.text.toString()
            val name = etNameReg.text.toString()
            val user = etUserReg.text.toString()
            val pass = etPassReg.text.toString()
            val imageUrl = "https://firebasestorage.googleapis.com/v0/b/lesswaste-28106.appspot.com/o/post_images%2F611F5FD9-805C-46B7-A685-545B05E81B49?alt=media&token=4484f0c8-46de-4591-84cc-ff9791b01935"
            val passConf = etPassCheckReg.text.toString()
            if (validateData(email,name,user,pass,passConf)){
                if(validatePass(pass,passConf)){
                    auth = FirebaseAuth.getInstance()
                    auth?.createUserWithEmailAndPassword(email,pass)
                        ?.addOnCompleteListener {
                            task ->
                                if(task.isSuccessful){
                                    val uid = task.result?.user?.uid
                                    database = FirebaseDatabase.getInstance().reference
                                    var userModel = UserModel()
                                    userModel.name = name
                                    userModel.profileImageUrl=imageUrl
                                    userModel.username=user
                                    if (uid != null) {
                                        database?.child("users").child(uid).setValue(userModel)
                                            ?.addOnCompleteListener {
                                                task->
                                                    if(task.isSuccessful){
                                                        val intLess = Intent(this, Less::class.java)
                                                        startActivity(intLess)
                                                        finish()
                                                    }else{
                                                        mostrarMensajeToast(task.exception?.message.toString())
                                                    }
                                            }
                                    }
                                }else{
                                    mostrarMensajeToast(task.exception?.message.toString())
                                }
                        }
                }else{
                    mostrarMensajeToast("Las contraseñas deben ser iguales")
                }
            }else{
                mostrarMensajeToast("Favor de llenar todos los datos")
            }
        }

    }



    private fun mostrarMensajeToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    fun validateData(email:String, name:String, user:String, pass:String, passConf:String): Boolean {
        return email.isNotEmpty() && name.isNotEmpty() && user.isNotEmpty() && pass.isNotEmpty() && passConf.isNotEmpty()
    }

    fun validatePass(pass:String, passConf:String): Boolean{
        return pass == passConf
    }
}