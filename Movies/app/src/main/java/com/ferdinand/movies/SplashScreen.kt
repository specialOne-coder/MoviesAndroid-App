package com.ferdinand.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout

class SplashScreen : AppCompatActivity() {

    /***
     *  Ecran de chargement de l'application (Première page)
     */
    lateinit var lay:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lay = findViewById(R.id.splash)
         val hand = Handler()
        hand.postDelayed(Runnable {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },2000)
    }
}
