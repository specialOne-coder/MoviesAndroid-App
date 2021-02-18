package com.ferdinand.movies.Films

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ferdinand.movies.R

class FilmAdapter(var context: Context,var list: ArrayList<FilmModel>) : BaseAdapter() {

    lateinit var option:RequestOptions

    /***
     *  Méthode qui renvoie la vue
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflater = LayoutInflater.from(context)
        val vue = layoutInflater.inflate(R.layout.activity_film_adapter,null)

        var imagevue = vue.findViewById<ImageView>(R.id.filmimagea)
        var titre = vue.findViewById<TextView>(R.id.filmtitre)
        var vote = vue.findViewById<TextView>(R.id.filmvote)
        var desc = vue.findViewById<TextView>(R.id.filmdescription)
        var sortie = vue.findViewById<TextView>(R.id.filmsortie)
        var contenu = vue.findViewById<LinearLayout>(R.id.container)

        option = RequestOptions().centerCrop().placeholder(R.drawable.fondgris).error(R.drawable.fondgris)
        val positionfilm = list.get(position)

        contenu.setOnClickListener(View.OnClickListener {
            var intent = Intent(context,DetailFilms::class.java)
            intent.putExtra("imagedetails",positionfilm.photof)
            intent.putExtra("titredetails",positionfilm.titre)
            intent.putExtra("votedetails",positionfilm.vote)
            intent.putExtra("descriptiondetails",positionfilm.description)
            intent.putExtra("sortiedetails",positionfilm.sortie)

            context.startActivity(intent)
        })

        Glide.with(context).load("https://image.tmdb.org/t/p/w342${positionfilm.photof}").apply(option).into(imagevue)
        titre.text = positionfilm.titre
        vote.text = positionfilm.vote
        desc.text = positionfilm.description
        sortie.text = "Sortie le "+positionfilm.sortie

        return vue;

    }

    /***
     *  Méthode de renvoie de la position d'un element dans la liste
     */
    override fun getItem(position: Int): Any {
       return list.get(position)
    }

    /***
     *  Méthode de recuperation d'identifiant d'un element de la liste
     */
    override fun getItemId(position: Int): Long {
        return list.get(position).id
    }

    /***
     *  Méthode permettant de compter le nombre d'element de la liste
     */
    override fun getCount(): Int {
        return list.size
    }


}
