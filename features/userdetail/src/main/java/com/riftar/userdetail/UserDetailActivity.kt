package com.riftar.userdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.riftar.userdetail.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserDetailBinding
    private var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        toolbar = binding.toolbar
        setContentView(binding.root)
//        setupToolbar(toolbar)
//        initViewListener()
//        observeViewModel()
//        setupView()
    }

    private fun setupToolbar(toolbar: Toolbar?) {
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            supportActionBar?.let {
                it.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
                it.setDisplayHomeAsUpEnabled(true)
                it.setHomeButtonEnabled(true)
            }
            toolbar.setTitleTextAppearance(this, com.riftar.common.R.style.Title)
            toolbar.setNavigationOnClickListener { finish() }
        }
    }
}