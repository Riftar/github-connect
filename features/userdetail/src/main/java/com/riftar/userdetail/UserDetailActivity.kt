package com.riftar.userdetail

import android.os.Bundle
import coil.load
import com.riftar.common.base.BaseActivity
import com.riftar.common.constant.NavigationConstant.USERNAME_INTENT
import com.riftar.common.constant.NavigationConstant.USER_ID_INTENT
import com.riftar.common.helper.setGone
import com.riftar.common.helper.setOrHide
import com.riftar.common.helper.showOrHide
import com.riftar.domain.userdetail.model.UserDetail
import com.riftar.userdetail.bottomsheet.EditNotesBottomSheet
import com.riftar.userdetail.databinding.ActivityUserDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailActivity : BaseActivity<ActivityUserDetailBinding>() {
    private val viewModel: UserDetailViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val userId = intent.getIntExtra(USER_ID_INTENT, 0)
        val userName = intent.getStringExtra(USERNAME_INTENT).orEmpty()
        viewModel.getUserDetail(userId, userName)
    }

    override fun getViewBinding() = ActivityUserDetailBinding.inflate(layoutInflater)
    override fun initToolbar() = binding.toolbar

    override fun observeViewModel() {
        viewModel.userDetail.observe(this) { userDetail ->
            showData(userDetail)
        }
        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) showLoadingDialog() else hideLoadingDialog()
        }
        viewModel.errorMessage.observe(this) { errorMsg ->
            showErrorSnackBar(errorMsg)
        }
    }

    override fun initViewListener() {
        with(binding) {
            ivEditNotes.setOnClickListener {
                EditNotesBottomSheet
                    .newInstance { notes ->
                        with(binding) {
                            tvNotesPlaceholder.showOrHide(notes.isEmpty())
                            tvNotes.setOrHide(notes)
                        }
                    }
                    .show(supportFragmentManager, EditNotesBottomSheet::class.java.toString())
            }
        }
        setTitleVisibilityOnScroll()
    }

    private fun setTitleVisibilityOnScroll() {
        val userName = intent.getStringExtra(USERNAME_INTENT).orEmpty()
        var isShow = true
        var scrollRange = -1
        binding.appbar.addOnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                binding.toolbarTitle.setOrHide(userName)
                isShow = true
            } else if (isShow) {
                binding.toolbarTitle.setGone()
                isShow = false
            }
        }
    }

    private fun showData(userDetail: UserDetail) {
        with(binding) {
            ivAvatar.load(userDetail.avatarUrl)
            tvUserName.text = userDetail.userName
            tvFullName.text = userDetail.name
            tvBio.setOrHide(userDetail.bio)
            tvBio.setOrHide(userDetail.bio)
            tvFollowers.text = getString(R.string.title_followers, userDetail.followers)
            tvFollowing.text = getString(R.string.title_following, userDetail.following)
            tvBlog.setOrHide(userDetail.blog)
            tvCompany.setOrHide(userDetail.company)
            tvLocation.setOrHide(userDetail.location)
            tvNotesPlaceholder.showOrHide(userDetail.notes.isEmpty())
            tvNotes.setOrHide(userDetail.notes)
        }
    }
}