package com.riftar.common.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.riftar.common.databinding.LayoutLoadingDialogBinding

class LoadingDialog(context: Context) : Dialog(context) {

    private lateinit var binding: LayoutLoadingDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(false)
        binding = LayoutLoadingDialogBinding.inflate(layoutInflater)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
    }

    companion object {
        @JvmStatic
        fun getInstance(context: Context): LoadingDialog {
            return LoadingDialog(context)
        }
    }
}