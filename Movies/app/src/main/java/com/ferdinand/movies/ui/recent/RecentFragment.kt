package com.ferdinand.movies.ui.recent

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ferdinand.movies.Films.FilmAdapter
import com.ferdinand.movies.Films.FilmModel
import com.ferdinand.movies.R
import org.json.JSONObject
import java.lang.Exception

class RecentFragment : Fragment() {

    private lateinit var recentViewModel: RecentViewModel
    lateinit var listViewrecent: ListView
    lateinit var progressDialogr: ProgressDialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recentViewModel =
            ViewModelProviders.of(this).get(RecentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_recent, container, false)
        listViewrecent = root.findViewById(R.id.listviewrecent)
        progressDialogr = ProgressDialog(requireContext())

        progressDialogr.setTitle("Recherche en cours")
        progressDialogr.setMessage("patientez quelques secondes ...")
        progressDialogr.setCanceledOnTouchOutside(false)
        progressDialogr.show()

        load_lastfilm()



        return root
    }

    private fun load_lastfilm() {
        val url = "https://api.themoviedb.org/3/search/movie?api_key=6dbc6176862220c2bb03b35d3c483dbb&language=fr-FR&query=a&page=1&include_adult=false&year=2019"

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
                    }catch (e: Exception){
                        Toast.makeText(requireContext(),"Ce film n'existe pas, mais il existe bien d'autres!!!",
                            Toast.LENGTH_LONG).show()
                    }
                }

                progressDialogr.dismiss()
                val adapter = FilmAdapter(requireContext(),lesfilms)
                listViewrecent.adapter = adapter

            },
            Response.ErrorListener {
                Toast.makeText(requireContext(),"Pas de connexion internet,Connectez-vous pour chercher vos films",
                    Toast.LENGTH_LONG).show()
            })

        var requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)
    }

}
