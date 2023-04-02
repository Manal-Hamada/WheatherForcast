package com.example.wheatherforcast.alerts.view


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wheatherforcast.R
import com.example.wheatherforcast.databinding.AddAlertBinding
import com.example.wheatherforcast.databinding.FragmentAlertsScreenBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class AlertFragment : Fragment() {

    lateinit var binding: FragmentAlertsScreenBinding
    lateinit var bottomSheet:AddAlertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAlertsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomSheet()
    }


    fun addAlertBottomSheet():BottomSheetDialog {
        var dialog = BottomSheetDialog(this.requireContext(),R.style.SheetDialog)
        val view = layoutInflater.inflate(R.layout.add_alert, null)
        bottomSheet=AddAlertBinding.inflate(layoutInflater)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bottomSheet.btnCancleAlert.setOnClickListener {

            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.dismissWithAnimation=true
        dialog.setContentView(bottomSheet.root)

        return dialog
    }

    fun showBottomSheet(){

        binding.addAlert.setOnClickListener{
            addAlertBottomSheet().show()
        }
    }
}
