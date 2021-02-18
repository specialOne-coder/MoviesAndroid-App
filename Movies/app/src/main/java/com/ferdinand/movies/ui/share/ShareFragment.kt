package com.ferdinand.movies.ui.share

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ferdinand.movies.R

class ShareFragment : Fragment() {

    private lateinit var shareViewModel: ShareViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shareViewModel =
            ViewModelProviders.of(this).get(ShareViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_share, container, false)
        val btnsearch = root.findViewById<Button>(R.id.btnshare)

        btnsearch.setOnClickListener(View.OnClickListener {
            val share = Intent(Intent.ACTION_SEND)
            share.type = "text/plain"
            share.putExtra(
                Intent.EXTRA_TEXT,
                "Pour l'instant l'appli n'est pas disponible sur PlayStore sinon vous aurez le lien a envoyer !"
            )
            startActivity(Intent.createChooser(share, "Partager"))
        })

        return root
    }
}