package com.example.inzynierka.ui.articles

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.inzynierka.R
import com.example.inzynierka.ui.articles.articlesList.ArticlesActivity
import com.example.inzynierka.ui.articles.enum.ArticleNameEnum
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

        setOnClickSection(root)
        //val textView: TextView = root.findViewById(R.id.txt_article)
        //articleViewModel.text.observe(viewLifecycleOwner, Observer { textView.text = it })
        return root
    }

    private fun setOnClickSection(root:View){
        val key = "title"

        root.article_city.setOnClickListener{
            val intent = Intent(activity, ArticlesActivity::class.java)
            intent.putExtra(key, ArticleNameEnum.CITY.toString())
            startActivity(intent)
        }

        root.article_sea.setOnClickListener{
            val intent = Intent(activity, ArticlesActivity::class.java)
            intent.putExtra(key, ArticleNameEnum.SEA.toString())
            startActivity(intent)
        }


        root.article_mountain.setOnClickListener{
            val intent = Intent(activity, ArticlesActivity::class.java)
            intent.putExtra(key, ArticleNameEnum.MOUNTAIN.toString())
            startActivity(intent)
        }

        root.article_forest.setOnClickListener{
            val intent = Intent(activity, ArticlesActivity::class.java)
            intent.putExtra(key, ArticleNameEnum.FOREST.toString())
            startActivity(intent)
        }
    }

}
