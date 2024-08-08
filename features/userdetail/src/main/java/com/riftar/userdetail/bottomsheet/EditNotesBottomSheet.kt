package com.riftar.userdetail.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.riftar.userdetail.UserDetailViewModel
import com.riftar.userdetail.databinding.LayoutEditNotesBottomsheetBinding

class EditNotesBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding: LayoutEditNotesBottomsheetBinding
    private val viewModel: UserDetailViewModel by activityViewModels()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewListener()
        observeViewModel()
    }

    private fun observeViewModel() {
    }

    private fun setViewListener() {
        with(binding) {
            ivBack.setOnClickListener {
                dismiss()
            }
            btnSave.setOnClickListener {
                val text = etNotes.text.toString()
                if (text.isNotEmpty()) {
                    viewModel.saveNotes(text)
                }
            }
        }
    }

    companion object {
        fun newInstance(): EditNotesBottomSheet {
            return EditNotesBottomSheet()
        }
    }
}