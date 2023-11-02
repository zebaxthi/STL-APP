package com.uco.stlapp.views.fragments

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uco.stlapp.R
import com.uco.stlapp.databinding.FragmentLoanBinding
import com.uco.stlapp.models.Article
import com.uco.stlapp.models.ArticleResponse
import com.uco.stlapp.models.Loan
import com.uco.stlapp.models.PatchArticleQuantity
import com.uco.stlapp.services.ArticleService
import com.uco.stlapp.viewModels.ArticleListViewModel
import com.uco.stlapp.viewModels.LoanListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class LoanFragment : Fragment() {


    private var nameIdArticle: Int? = null
    private var nameArticle: String? = null
    private var nameQuantity: Int? = null
    private var nameStatus: String? = null
    private var nameStartDate: String? = null
    private var nameEndDate: String? = null
    private var nameIsReturned: Boolean? = null
    private var nameMonitor: String? = null
    private lateinit var binding: FragmentLoanBinding
    private var articleService = ArticleService()
    private var nameIdLoan: String?=null
    private lateinit var viewModelLoan: LoanListViewModel
    private lateinit var viewModelArticle: ArticleListViewModel

    val db = Firebase.firestore

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoanBinding.inflate(layoutInflater)
        arguments?.let {
            nameArticle = it.getString(NAMEARTICLE_BUNDLE)
            nameQuantity = it.getInt(NAMEQUANTITY_BUNDLE)
            nameStatus = it.getString(NAMESTATUS_BUNDLE)
            nameStartDate = it.getString(NAMESTARTDATE_BUNDLE)
            nameEndDate = it.getString(NAMEENDDATE_BUNDLE)
            nameIsReturned = it.getBoolean(NAMEISRETURNED_BUNDLE)
            nameMonitor = it.getString(NAMEMONITOR_BUNDLE)
            nameIdArticle= it.getInt(NAMEIDARTICLE_BUNDLE)
            nameIdLoan=it.getString(NAMEIDLOAN_BUNDLE)
        }

        binding.btReturnItem.setOnClickListener{
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }

        viewModelLoan = LoanListViewModel(requireContext())
        viewModelArticle = ArticleListViewModel(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoanBinding.inflate(inflater, container, false)
        binding.tvNameArticle.text = nameArticle
        binding.tvNameQuantity.text = "Quantity: " + nameQuantity.toString()
        binding.tvNameStatus.text = "Status: " + nameStatus
        binding.tvNameStartDate.text = "Start: " + nameStartDate
        binding.tvNameEndDate.text = "End: " + nameEndDate
        binding.tvNameIsReturned.text = "Is Returned: " + nameIsReturned.toString()
        binding.tvNameMonitor.text = "Monitor: " + nameMonitor
        binding.btReturnItem.setOnClickListener{
            findNavController().navigate(R.id.action_loanFragment_to_nav_loanList)

        }
        binding.btReturnLoanItem.setOnClickListener{
            returnLoan()
        }

        return binding.root
    }

    companion object {

        const val NAMEARTICLE_BUNDLE = "nameArticle_bundle"
        const val NAMEQUANTITY_BUNDLE = "nameQuantity_bundle"
        const val NAMESTATUS_BUNDLE = "nameStatus_bundle"
        const val NAMESTARTDATE_BUNDLE = "nameStartDate_bundle"
        const val NAMEENDDATE_BUNDLE = "nameEndDate_bundle"
        const val NAMEISRETURNED_BUNDLE = "nameIsReturned_bundle"
        const val NAMEMONITOR_BUNDLE = "nameMonitor_bundle"
        const val NAMEIDARTICLE_BUNDLE= "nameIdArticle_bundle"
        const val NAMEIDLOAN_BUNDLE="nameIdLoan_bundle"




        @JvmStatic
        fun newInstance(nameArticle: String, nameRef: String,
            nameQuantity: Int, nameStatus: String,
            nameStartDate: String, nameEndDate: String,
            nameIsReturned: Boolean, nameMonitor: String) =
            LoanFragment().apply {
                arguments = Bundle().apply {
                    putString(NAMEARTICLE_BUNDLE, nameArticle)
                    putInt(NAMEQUANTITY_BUNDLE, nameQuantity)
                    putString(NAMESTATUS_BUNDLE, nameStatus)
                    putString(NAMESTARTDATE_BUNDLE, nameStartDate)
                    putString(NAMEENDDATE_BUNDLE, nameEndDate)
                    putBoolean(NAMEISRETURNED_BUNDLE, nameIsReturned)
                    putString(NAMEMONITOR_BUNDLE, nameMonitor)
                    putInt(NAMEIDARTICLE_BUNDLE,nameIdArticle!!)
                    putString(NAMEIDLOAN_BUNDLE,nameIdLoan)

                }
            }
    }
    private fun returnLoan()
    {


        CoroutineScope(Dispatchers.IO).launch {


           val returnObjetArticle = articleService.getArticleById(nameIdArticle!!)?.body()
            val request : PatchArticleQuantity? =
                returnObjetArticle?.quantity?.plus(nameQuantity!!)?.let { PatchArticleQuantity(it) }

            returnObjetArticle?.id?.let { articleService.patchArticle(it,request!! ) }


        }
        val docRef = db.collection("loans").document(nameIdLoan!!)

        val newData = mapOf(
            "returned" to true


        )

             docRef.update(newData)
            .addOnSuccessListener {
                Log.d(TAG, "Documento actualizado exitosamente")

                viewModelLoan.fetchLoansData()
                viewModelArticle.fetchArticlesData()
                findNavController().navigate(R.id.action_loanFragment_to_nav_loanList)


            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error al actualizar el documento", e)
                Toast.makeText(this.context, "Hubo un error en la solicitud de retorno del articulo", Toast.LENGTH_SHORT).show()

            }


    }
}