package com.riftar.common.base

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.riftar.common.R
import com.riftar.common.databinding.LayoutSnackbarBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var binding: VB
    protected var toolbar: Toolbar? = null
    var loadingDialog: LoadingDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        toolbar = initToolbar()
        setContentView(binding.root)
        setupToolbar(toolbar)
        loadingDialog = LoadingDialog(this)
        initViewListener()
        observeViewModel()
    }

    open fun observeViewModel() {
    }

    open fun initViewListener() {
    }

    open fun initToolbar(): Toolbar? {
        return null
    }

    protected abstract fun getViewBinding(): VB

    protected fun showLoadingDialog() {
        loadingDialog?.show()
    }

    protected fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }

    override fun onDestroy() {
        loadingDialog?.dismiss()
        loadingDialog = null
        super.onDestroy()
    }

    private fun setupToolbar(toolbar: Toolbar?) {
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            supportActionBar?.let {
                it.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
                it.setDisplayHomeAsUpEnabled(true)
                it.setHomeButtonEnabled(true)
                // Hide title text
                it.setDisplayShowTitleEnabled(false)
            }
            toolbar.setNavigationOnClickListener { finish() }
        }
    }

    @SuppressLint("RestrictedApi")
    protected fun showErrorSnackBar(
        message: String = "Unknown Error",
    ) {
        val snackBar = Snackbar.make(findViewById(android.R.id.content), message, 5000)
        val customSnackBarBinding = LayoutSnackbarBinding.inflate(LayoutInflater.from(this))
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