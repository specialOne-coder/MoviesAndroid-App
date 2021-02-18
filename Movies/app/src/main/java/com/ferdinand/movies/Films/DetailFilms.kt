package com.ferdinand.movies.Films

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.ferdinand.movies.Database.DatabaseHelper
import com.ferdinand.movies.R
import java.io.BufferedReader

class DetailFilms : AppCompatActivity() {

    lateinit var photo:ImageView
    lateinit var titre:TextView
    lateinit var vote:TextView
    lateinit var description:TextView
    lateinit var sortie:TextView
    lateinit var like:ImageButton
    lateinit var helper:DatabaseHelper
    lateinit var p:String
    lateinit var t:String
    lateinit var v:String
    lateinit var d:String
    lateinit var s:String
    public var isliked:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_films)

        /***
         *  Récuperation  et Initialisation
         */

        photo = findViewById(R.id.imagedetails)
        titre = findViewById(R.id.titredetails)
        vote = findViewById(R.id.votedetails)
        description = findViewById(R.id.descriptiondetails)
        sortie = findViewById(R.id.sortiedetails)
        like = findViewById(R.id.like)
        helper = DatabaseHelper(this)
        isliked = false


        /***
         *  Recupération des infos envoyés depuis la page d'accueil
         */

        val intentextra = intent.extras
        if(intentextra != null){
            var photoextra = intentextra.getString("imagedetails")
            var titreextra = intentextra.getString("titredetails")
            var descriptionextra = intentextra.getString("descriptiondetails")
            var voteextra = intentextra.getString("votedetails")
            var datesortieextra = intentextra.getString("sortiedetails")

            Glide.with(this).load("https://image.tmdb.org/t/p/original${photoextra}").into(photo)
            titre.setText(titreextra)
            vote.setText("Vote :"+voteextra)
            description.setText(descriptionextra)
            sortie.setText("Date de Sortie: "+datesortieextra)
            p = photoextra.toString()
            t = titreextra.toString()
            v = voteextra.toString()
            d = descriptionextra.toString()
            s = datesortieextra.toString()


            /**
             *  Verification: si le film est existe déja dans la base
             */
                var db = helper.readableDatabase
                val cursor = db.rawQuery("SELECT * FROM filmsfavoris ",null)
                if(cursor.count == 0){
                    Toast.makeText(this,"Aucun film ajouté aux favoris pour l'instant ",
                        Toast.LENGTH_LONG).show()
                }
                while(cursor.moveToNext()){
                    if(t == cursor.getString(2)){
                        like.setBackgroundResource(R.drawable.favorites)
                        isliked = true
                    }
                }

            /***
             *  Ajout des films aux favoris ou le contraire
             */

                if(isliked == false) {
                    like.setOnClickListener(View.OnClickListener {
                      like.setBackgroundResource(R.drawable.favorites)
                      val insertion = helper.insert(p, t, v, d, s)
                     if (insertion == true) {
                        Toast.makeText(
                            this, "Film ajouté aux favoris avec succès",
                            Toast.LENGTH_LONG
                        ).show()
                     } else {
                        Toast.makeText(
                            this, "Erreur",
                            Toast.LENGTH_LONG
                        ).show()
                     }
                    })
                }else if(isliked == true){
                    like.setOnClickListener(View.OnClickListener {
                      like.setBackgroundResource(R.drawable.favbtn1)
                      val suppression = helper.retirer(t)
                      if (suppression == true) {
                        Toast.makeText(
                            this, "Film retiré des favoris avec succès",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this, "Erreur",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    })
                }




        }


    }
}