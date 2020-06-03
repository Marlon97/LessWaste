package com.mbvp.lesswaste

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    lateinit var auth : FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvRegistrate.setOnClickListener { view ->
            val intRegistrarse = Intent(this, Registrarse::class.java)
            startActivity(intRegistrarse)
            finish()
        }

        btnLogin.setOnClickListener { view ->
            val email = etEmail.text.toString()
            val pass = etPass.text.toString()
            if(validateCombo(email,pass)){
                auth = FirebaseAuth.getInstance()
                auth?.signInWithEmailAndPassword(email,pass)
                    ?.addOnCompleteListener {
                            task ->
                        if(task.isSuccessful){
                            if(task.result?.user!=null){
                                val intLess = Intent(this, Less::class.java)
                                startActivity(intLess)
                                finish()
                            }
                        }else{
                            task.exception?.message?.let { mostrarMensajeToast(it) }
                        }
                    }
            }else{
                mostrarMensajeToast("Favor de introducir correo y contraseña")
            }
        }

    }

    private fun mostrarMensajeToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    fun validateCombo(email: String, pass: String): Boolean {
        return email.isNotEmpty() && pass.isNotEmpty()
    }

}
