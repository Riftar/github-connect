package com.riftar.userdetail.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.riftar.userdetail.databinding.LayoutEditNotesBottomsheetBinding

class EditNotesBottomSheet : BottomSheetDialogFragment() {
    private var initialNotes: String = ""
    lateinit var binding: LayoutEditNotesBottomsheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutEditNotesBottomsheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(
            DialogFragment.STYLE_NO_FRAME,
            com.riftar.common.R.style.RoundedTopCornerBottomSheetDialogTheme
        )
        super.onCreate(savedInstanceState)
    }

//    private fun setViewListener() {
//        with(binding) {
//            ivClose.setOnClickWithThrottle {
//                dismiss()
//            }
//            ivBack.setOnClickWithThrottle {
//                dismiss()
//            }
//            btnNext.setOnClickWithThrottle {
//                if (isFormValid()) {
//                    val url = "https://api.whatsapp.com/send?phone=+${etPhoneNumber.text}&text=${etMessage.text}"
//                    val intent = Intent(Intent.ACTION_VIEW).apply {
//                        data = Uri.parse(url)
//                    }
//                    startActivity(intent)
//                }
//            }
//        }
//    }

    companion object {
        fun newInstance(
            initialNotes: String
        ): EditNotesBottomSheet {
            return EditNotesBottomSheet().apply {
                this.initialNotes = initialNotes
            }
        }
    }
}