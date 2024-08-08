package com.riftar.userdetail.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.riftar.userdetail.UserDetailViewModel
import com.riftar.userdetail.databinding.LayoutEditNotesBottomsheetBinding

class EditNotesBottomSheet : BottomSheetDialogFragment() {
    private var onSuccess: (String) -> Unit = {}
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
        viewModel.isSaveNoteSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(requireContext(), "Success add note!", Toast.LENGTH_SHORT).show()
                onSuccess.invoke(binding.etNotes.text.toString())
                dismiss()
            }
        }
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
        fun newInstance(onSuccess: ((String) -> Unit)): EditNotesBottomSheet {
            return EditNotesBottomSheet().apply {
                this.onSuccess = onSuccess
            }
        }
    }
}