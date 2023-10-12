package com.uco.stlapp.views.fragments

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uco.stlapp.R
import com.uco.stlapp.views.adapters.ArticleAdapter
import com.uco.stlapp.databinding.FragmentArticleListBinding
import com.uco.stlapp.views.fragments.ArticleFragment.Companion.NAMEARTICLE_BUNDLE
import com.uco.stlapp.views.fragments.ArticleFragment.Companion.NAMEQUANTITY_BUNDLE
import com.uco.stlapp.views.fragments.ArticleFragment.Companion.NAMESTATUS_BUNDLE
import com.uco.stlapp.views.fragments.ArticleFragment.Companion.NAMEREF_BUNDLE
import com.uco.stlapp.models.Article
import com.uco.stlapp.repository.database.AppDatabase
import com.uco.stlapp.utils.messaging.MessagingService
import com.uco.stlapp.viewModels.ArticleListViewModel


class ArticleListFragment : Fragment() {

    private lateinit var binding: FragmentArticleListBinding
    private var ArticleMutableList: MutableList<Article> = mutableListOf()
    private lateinit var adapter: ArticleAdapter
    private val manager by lazy { LinearLayoutManager(requireContext()) }
    private lateinit var db: AppDatabase
    private val channelID ="notification_channel"
    private val channelName ="com.uco.stlapp.utils.messaging"
    private val notificID = 0

    companion object {
        fun newInstance() = ArticleListFragment()
    }

    private lateinit var viewModel: ArticleListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleListBinding.inflate(inflater, container, false)
        viewModel = ArticleListViewModel(requireContext())
        db = AppDatabase.getInstance(context)
        ArticleMutableList = viewModel.getArticles().toMutableList()
        createNotificationChannel()
        return binding.root
    }

    fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelID,channelName, importance).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            val notificationManager = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etFilterArticle.addTextChangedListener { articleFilter ->
            val filtered = ArticleMutableList.filter { x ->
                x.name.lowercase().contains(articleFilter.toString().lowercase())
            }
            adapter.updateArticles(filtered)
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = ArticleAdapter(ArticleMutableList) {
            onItemSelected(it)
        }
        binding.recyclerArticle.layoutManager = manager
        binding.recyclerArticle.adapter = adapter
    }

    private fun onItemSelected(article: Article) {
        val bundle = bundleOf(
            NAMEARTICLE_BUNDLE to article.name,
            NAMEREF_BUNDLE to article.ref,
            NAMEQUANTITY_BUNDLE to article.quantity,
            NAMESTATUS_BUNDLE to article.status
        )

        val notificacion= context?.let { it ->
            NotificationCompat.Builder(it,channelID).also{
                    it.setContentTitle(article.name)
                    it.setContentText(article.ref)
                    it.setSmallIcon(R.drawable.loan_logo)
                    it.priority = NotificationCompat.PRIORITY_HIGH
            }.build()
        }
            if (notificacion != null) {
                if (this.context?.let {
                        ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.POST_NOTIFICATIONS
                        )
                    } != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                context?.let { it1 ->
                    NotificationManagerCompat.from(
                        it1
                    )
                }?.notify(notificID, notificacion)
        }

        findNavController().navigate(R.id.action_nav_articleList_to_articleFragment, bundle)
    }
}