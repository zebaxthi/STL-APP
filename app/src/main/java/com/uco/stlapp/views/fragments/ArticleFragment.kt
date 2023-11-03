package com.uco.stlapp.views.fragments

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uco.stlapp.R
import com.uco.stlapp.databinding.FragmentArticleBinding
import com.uco.stlapp.models.Loan
import com.uco.stlapp.models.PatchArticleQuantity
import com.uco.stlapp.services.ArticleService
import com.uco.stlapp.viewModels.ArticleListViewModel
import com.uco.stlapp.viewModels.LoanListViewModel
import com.uco.stlapp.views.adapters.ArticleAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date


class ArticleFragment : Fragment() {

    private var nameId: Int? = null
    private var nameArticle: String? = null
    private var nameRef: String? = null
    private var nameQuantity: Int? = null
    private var nameStatus: String? = null
    private var DateEndLoan: Date? = null
    private lateinit var binding: FragmentArticleBinding //ref de vista
    private var articleService = ArticleService()
    private lateinit var viewModel: ArticleListViewModel
    private lateinit var adapter: ArticleAdapter
    private lateinit var viewModelLoan: LoanListViewModel

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    private val channelID ="notification_channel"
    private val channelName ="com.uco.stlapp.utils.messaging"
    private val notificID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentArticleBinding.inflate(layoutInflater)
        arguments?.let {
            nameArticle = it.getString(NAMEARTICLE_BUNDLE)
            nameRef = it.getString(NAMEREF_BUNDLE)
            nameQuantity = it.getInt(NAMEQUANTITY_BUNDLE)
            nameStatus = it.getString(NAMESTATUS_BUNDLE)
            nameId= it.getInt(NAMEID_BUNDLE)
        }
        auth = FirebaseAuth.getInstance()
        viewModelLoan = LoanListViewModel(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        binding.tvNameArticle.text = nameArticle
        binding.tvNameRef.text = "Ref: " + nameRef
        binding.tvNameQuantity.text = "Quantity: " + nameQuantity.toString()
        binding.tvNameStatus.text = "Status: " + nameStatus
        binding.btReturnItem.setOnClickListener{

            findNavController().navigate(R.id.action_articleFragment_to_nav_articleList)
        }
        binding.btLendItem.setOnClickListener{
            generateLoan()
        }
        binding.etDate.showSoftInputOnFocus = false
        binding.etDate.setOnClickListener{

            showDatePickerDialog()
        }
        createNotificationChannel()
        return binding.root
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment{day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(requireActivity().supportFragmentManager, "datePicker")
    }

    fun onDateSelected(day: Int, month: Int, year: Int){
        var monthReal = month + 1
        binding.etDate.setText("$day/$monthReal/$year")
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        DateEndLoan = calendar.time
    }

    private fun generateLoan(){
        val id: Int = nameId ?: 0
        val request : PatchArticleQuantity = (nameQuantity ?: 0).let { PatchArticleQuantity(it - 1)
        }
        CoroutineScope(Dispatchers.IO).launch {
            articleService.patchArticle(id, request)
            viewModel = ArticleListViewModel(requireContext())
            viewModel.fetchArticlesData()
        }
        val user = auth.currentUser
        if(DateEndLoan == null){
            Toast.makeText(this.context, "Seleccione una fecha de devolucion", Toast.LENGTH_SHORT).show()
        }else{
            var loan = Loan(null,null, null, nameId!!, nameArticle.toString(), user?.uid.toString(), 1, Date(), DateEndLoan, false)
            db.collection("loans")
                .add(loan)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    notificationLoan(loan.articleName)
                    viewModelLoan.fetchLoansData()
                    findNavController().navigate(R.id.action_articleFragment_to_nav_articleList)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    Toast.makeText(this.context, "Hubo un error en la solicitud del prestamo", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelID,channelName, importance).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun notificationLoan(articleName: String){
        val notificacion= context?.let { it ->
            NotificationCompat.Builder(it,channelID).also{
                it.setContentTitle("El prestamo exitoso")
                it.setContentText("El prestamo de el articulo: ${articleName}.")
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
            ){
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
    }

    companion object {
        const val NAMEID_BUNDLE = "nameId_bundle"
        const val NAMEARTICLE_BUNDLE = "nameArticle_bundle"
        const val NAMEREF_BUNDLE = "nameRef_bundle"
        const val NAMEQUANTITY_BUNDLE = "nameQuantity_bundle"
        const val NAMESTATUS_BUNDLE = "nameStatus_bundle"
        @JvmStatic
        fun newInstance(nameArticle: String, nameRef: String,
                        nameQuantity: Int, nameId: Int, nameStatus: String) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putInt(NAMEID_BUNDLE, nameId)
                    putString(NAMEARTICLE_BUNDLE, nameArticle)
                    putString(NAMEREF_BUNDLE, nameRef)
                    putInt(NAMEQUANTITY_BUNDLE, nameQuantity)
                    putString(NAMESTATUS_BUNDLE, nameStatus)
                }
            }
    }
}