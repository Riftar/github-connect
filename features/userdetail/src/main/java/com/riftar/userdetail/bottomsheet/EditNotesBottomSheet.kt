package com.riftar.userdetail.bottomsheet

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.riftar.common.R
import com.riftar.common.constant.ViewConstant
import com.riftar.common.databinding.LayoutSnackbarBinding
import com.riftar.common.helper.showOrHide
import com.riftar.userdetail.SaveNotesState
import com.riftar.userdetail.UserDetailViewModel
import com.riftar.userdetail.databinding.LayoutEditNotesBottomsheetBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditNotesBottomSheet : BottomSheetDialogFragment() {
    private var userId = 0
    private var onSuccess: (String, String) -> Unit = { _, _ -> }
    private lateinit var binding: LayoutEditNotesBottomsheetBinding
    private val viewModel: UserDetailViewModel by viewModel()
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
            R.style.RoundedTopCornerBottomSheetDialogTheme
        )
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewListener()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.saveNotesState.collect { state ->
                when (state) {
                    SaveNotesState.Initial -> {}
                    SaveNotesState.Loading -> {
                        changeLayoutState(isEnable = false)
                    }

                    is SaveNotesState.Success -> {
                        changeLayoutState(isEnable = true)
                        onSuccess.invoke(binding.etNotes.text.toString(), state.message)
                        dismiss()
                    }

                    is SaveNotesState.Error -> {
                        changeLayoutState(isEnable = true)
                        showErrorSnackBar(message = state.message)
                    }
                }

            }
        }
    }

    private fun changeLayoutState(isEnable: Boolean) {
        with(binding) {
            isCancelable = isEnable
            ivBack.isEnabled = isEnable
            btnSave.isEnabled = isEnable
            etNotes.isEnabled = isEnable
            progressBar.showOrHide(!isEnable, View.INVISIBLE)
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
                    viewModel.saveNotes(userId, text)
                }
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun showErrorSnackBar(
        message: String = ViewConstant.SNACKBAR_DEFAULT_MESSAGE
    ) {
        dialog?.window?.decorView?.let {
            val snackBar = Snackbar.make(it, message, ViewConstant.SNACKBAR_DURATION)
            val customSnackBarBinding =
                LayoutSnackbarBinding.inflate(LayoutInflater.from(requireContext()))
            with(customSnackBarBinding) {
                tvMessage.text = message
                ivAction.setOnClickListener {
                    snackBar.dismiss()
                }
            }
            snackBar.view.setBackgroundColor(Color.TRANSPARENT)
            (snackBar.view as Snackbar.SnackbarLayout).apply {
                setPadding(0, 0, 0, 0)
                addView(customSnackBarBinding.root)
            }
            snackBar.show()
        }
    }

    companion object {
        fun newInstance(userId: Int, onSuccess: ((String, String) -> Unit)): EditNotesBottomSheet {
            return EditNotesBottomSheet().apply {
                this.userId = userId
                this.onSuccess = onSuccess
            }
        }
    }
}