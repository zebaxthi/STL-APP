package com.uco.stlapp.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.uco.stlapp.R
import com.uco.stlapp.databinding.FragmentLoanBinding

class LoanFragment : Fragment() {

    private var nameArticle: String? = null
    private var nameRef: String? = null
    private var nameQuantity: Int? = null
    private var nameStatus: String? = null
    private var nameStartDate: String? = null
    private var nameEndDate: String? = null
    private var nameIsReturned: Boolean? = null
    private var nameMonitor: String? = null
    private lateinit var binding: FragmentLoanBinding

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
            nameRef = it.getString(NAMEREF_BUNDLE)
            nameQuantity = it.getInt(NAMEQUANTITY_BUNDLE)
            nameStatus = it.getString(NAMESTATUS_BUNDLE)
            nameStartDate = it.getString(NAMESTARTDATE_BUNDLE)
            nameEndDate = it.getString(NAMEENDDATE_BUNDLE)
            nameIsReturned = it.getBoolean(NAMEISRETURNED_BUNDLE)
            nameMonitor = it.getString(NAMEMONITOR_BUNDLE)
        }

        binding.btReturnItem.setOnClickListener{
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoanBinding.inflate(inflater, container, false)
        binding.tvNameArticle.text = nameArticle
        binding.tvNameRef.text = "Ref: " + nameRef
        binding.tvNameQuantity.text = "Quantity: " + nameQuantity.toString()
        binding.tvNameStatus.text = "Status: " + nameStatus
        binding.tvNameStartDate.text = "Start: " + nameStartDate
        binding.tvNameEndDate.text = "End: " + nameEndDate
        binding.tvNameIsReturned.text = "Is Returned: " + nameIsReturned.toString()
        binding.tvNameMonitor.text = "Monitor: " + nameMonitor
        binding.btReturnItem.setOnClickListener{
            findNavController().navigate(R.id.action_loanFragment_to_nav_loanList)
        }
        return binding.root
    }

    companion object {

        const val NAMEARTICLE_BUNDLE = "nameArticle_bundle"
        const val NAMEREF_BUNDLE = "nameRef_bundle"
        const val NAMEQUANTITY_BUNDLE = "nameQuantity_bundle"
        const val NAMESTATUS_BUNDLE = "nameStatus_bundle"
        const val NAMESTARTDATE_BUNDLE = "nameStartDate_bundle"
        const val NAMEENDDATE_BUNDLE = "nameEndDate_bundle"
        const val NAMEISRETURNED_BUNDLE = "nameIsReturned_bundle"
        const val NAMEMONITOR_BUNDLE = "nameMonitor_bundle"

        @JvmStatic
        fun newInstance(nameArticle: String, nameRef: String,
            nameQuantity: Int, nameStatus: String,
            nameStartDate: String, nameEndDate: String,
            nameIsReturned: Boolean, nameMonitor: String) =
            LoanFragment().apply {
                arguments = Bundle().apply {
                    putString(NAMEARTICLE_BUNDLE, nameArticle)
                    putString(NAMEREF_BUNDLE, nameRef)
                    putInt(NAMEQUANTITY_BUNDLE, nameQuantity)
                    putString(NAMESTATUS_BUNDLE, nameStatus)
                    putString(NAMESTARTDATE_BUNDLE, nameStartDate)
                    putString(NAMEENDDATE_BUNDLE, nameEndDate)
                    putBoolean(NAMEISRETURNED_BUNDLE, nameIsReturned)
                    putString(NAMEMONITOR_BUNDLE, nameMonitor)
                }
            }
    }
}