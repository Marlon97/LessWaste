package com.mbvp.lesswaste

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import java.util.jar.Manifest

class Less : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_less)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.INTERNET),1)
        val fragHome = HomeFrag()
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentFrag, fragHome)
            .commit()
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener { item -> Boolean
            when (item.itemId) {
                R.id.navigation_home -> {
                    Log.i("NAVIGATION", "Usuario accede a 'Home'")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.contentFrag, fragHome)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.navigation_explore ->{
                    Log.i("NAVIGATION", "Usuario accede a 'Explore'")
                    val fragExplore = ExploreFrag()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.contentFrag, fragExplore)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .addToBackStack(null)
                        .commit()
                }
                R.id.navigation_add ->{
                    Log.i("NAVIGATION", "Usuario accede a 'Add'")
                    if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.INTERNET)==PackageManager.PERMISSION_GRANTED){
                            val intAdd = Intent(this, Add::class.java)
                            startActivity(intAdd)
                            finish()
                        }
                    }
                }
                R.id.navigation_notifications ->{
                    Log.i("NAVIGATION","Usuario accede a 'Notifications'")
                    val fragNotifications = NotificationsFrag()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.contentFrag, fragNotifications)
                        .addToBackStack(null)
                        .commit()
                }
                R.id.navigation_profile ->{
                    Log.i("NAVIGATION", "Usuario accede a 'Profile'")
                    val fragProfile = ProfileFrag()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.contentFrag, fragProfile)
                        .addToBackStack(null)
                        .commit()
                }
                else ->{
                    Log.i("UNSUPPORTED", "Opción no soportada")
                }

            }
            true
        }
    }
}