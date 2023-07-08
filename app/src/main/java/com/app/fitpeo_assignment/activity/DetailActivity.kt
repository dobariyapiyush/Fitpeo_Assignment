package com.app.fitpeo_assignment.activity

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.app.fitpeo_assignment.R
import com.app.fitpeo_assignment.base.BaseActivity
import com.app.fitpeo_assignment.base.BaseBindingActivity
import com.app.fitpeo_assignment.databinding.ActivityDetailBinding
import com.app.fitpeo_assignment.network.core.PHOTO_TITLE
import com.app.fitpeo_assignment.network.core.PHOTO_URL
import com.squareup.picasso.Picasso

class DetailActivity : BaseBindingActivity<ActivityDetailBinding>() {

    override fun setBinding(): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun getActivityContext(): BaseActivity {
        return this@DetailActivity
    }

    override fun initView() {
        super.initView()
        val photoTitle = intent.getStringExtra(PHOTO_TITLE)
        val photoUrl = intent.getStringExtra(PHOTO_URL)
        with(mBinding) {
            textView.text = photoTitle
            Picasso.get()
                .load(photoUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .into(imageView)
        }
    }

    override fun initViewListener() {
        super.initViewListener()

        setClickListener(
            mBinding.clickBack
        )
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v) {
            mBinding.clickBack -> {
                finish()
            }
        }
    }
}