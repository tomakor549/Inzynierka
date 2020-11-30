package com.example.inzynierka.ui.article

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka.R
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_article.view.*

class ArticleFragment : Fragment() {

    private lateinit var articleViewModel: ArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        articleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_article, container, false)


        root.article_city.setOnClickListener{
            val intent = Intent(activity, ArticleActivity::class.java)
            startActivity(intent)
        }
        //val textView: TextView = root.findViewById(R.id.txt_article)
        //articleViewModel.text.observe(viewLifecycleOwner, Observer { textView.text = it })
        return root
    }

}
