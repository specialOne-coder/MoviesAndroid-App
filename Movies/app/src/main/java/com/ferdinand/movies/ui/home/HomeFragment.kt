package com.ferdinand.movies.ui.home

import android.app.ProgressDialog
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.IntegerRes
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.ferdinand.movies.Films.DetailFilms
import com.ferdinand.movies.Films.FilmAdapter
import com.ferdinand.movies.Films.FilmModel
import com.ferdinand.movies.R
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import kotlin.jvm.java as java

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var listview :ListView
    lateinit var filmrecherche:EditText
    lateinit var searchbtn:Button
    lateinit var linearsearch:LinearLayout
    lateinit var entrée:String
    lateinit var progressDialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        //Recuperation des champs du fichier xml
        listview = root.findViewById(R.id.listview)
        filmrecherche = root.findViewById(R.id.rechercheenter)
        searchbtn = root.findViewById(R.id.recherchebtn)
        linearsearch = root.findViewById(R.id.searchgood)
        progressDialog = ProgressDialog(requireContext())

//        images = root.findViewById(R.id.filmimage)
//        titres = root.findViewById(R.id.filmtitre)
//        descriptions = root.findViewById(R.id.filmdescription)
//        votes = root.findViewById(R.id.filmvote)
//        sorties = root.findViewById(R.id.filmsortie)

        searchbtn.setOnClickListener(View.OnClickListener {
            progressDialog.setTitle("Recherche en cours")
            progressDialog.setMessage("patientez quelques secondes ...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            load_film()

        })


        return root
    }

    private fun load_film() {

        entrée = filmrecherche.text.toString()
        val url = "https://api.themoviedb.org/3/search/movie?api_key=6dbc6176862220c2bb03b35d3c483dbb&language=fr-FR&query="+entrée+"&page=1&include_adult=false"

        var stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> {

                var jsonObjects = JSONObject(it.toString())
                var jsonArray = jsonObjects.getJSONArray("results")
                val lesfilms = ArrayList<FilmModel>()

                for (i in 0..(jsonArray.length() - 1)) {
                    val item = jsonArray.getJSONObject(i)
                    try {
                        var imageapi = item.getString("poster_path")
                        var titreapi = item.getString("title")
                        var voteapi = item.getString("vote_average")
                        var descriptionapi = item.getString("overview")
                        var sortieapi = item.getString("release_date")

                        lesfilms.add(
                            FilmModel(
                                1,imageapi,titreapi,voteapi,descriptionapi,sortieapi
                            )
                        )
                    }catch (e:Exception){
                        Toast.makeText(requireContext(),"Ce film n'existe pas, mais il existe bien d'autres!!!",Toast.LENGTH_LONG).show()
                    }
                }

               linearsearch.isInvisible = true
               progressDialog.dismiss()

                val adapter = FilmAdapter(requireContext(),lesfilms)
                listview.adapter = adapter


            },
            Response.ErrorListener {
                Toast.makeText(requireContext(),"Pas de connexion internet,Connectez-vous pour chercher vos films",Toast.LENGTH_LONG).show()
            })

        var requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)
    }


}