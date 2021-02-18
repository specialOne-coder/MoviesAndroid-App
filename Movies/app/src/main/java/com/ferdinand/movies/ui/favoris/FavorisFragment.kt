package com.ferdinand.movies.ui.favoris

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ferdinand.movies.Database.DatabaseHelper
import com.ferdinand.movies.Films.FilmAdapter
import com.ferdinand.movies.Films.FilmModel
import com.ferdinand.movies.R
import kotlinx.android.synthetic.main.fragment_recent.*

class FavorisFragment : Fragment() {

    private lateinit var favorisViewModel: FavorisViewModel
    lateinit var listfavoris:ListView
    lateinit var help:DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favorisViewModel =
            ViewModelProviders.of(this).get(FavorisViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_favoris, container, false)

        /***
         * Récuperation et initialisation
         */

        listfavoris = root.findViewById(R.id.listviewfavoris)
        help = DatabaseHelper(requireContext())


        /***
         * Selection des films de la base de donnée SQLite
         */

        var db = help.readableDatabase
        val cursor = db.rawQuery("SELECT DISTINCT * FROM filmsfavoris ORDER BY id DESC",null)
        if(cursor.count == 0){
            Toast.makeText(requireContext(),"Aucun film dans vos favoris",
                Toast.LENGTH_LONG).show()
        }

        val lesfilmsfavoris = ArrayList<FilmModel>()
        while(cursor.moveToNext()) {
            var image = cursor.getString(1)
            var titre =  cursor.getString(2)
            var vote =   cursor.getString(3)
            var description = cursor.getString(4)
            var sortie = cursor.getString(5)

            lesfilmsfavoris.add(
                FilmModel(
                    1,image,titre,vote,description,sortie
                )
            )
        }
        /***
         * Assignation au filmAdapter
         */
        val adapter = FilmAdapter(requireContext(),lesfilmsfavoris)
        listfavoris.adapter = adapter

        return root
    }
}