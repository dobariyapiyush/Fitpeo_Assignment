package com.app.fitpeo_assignment.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.app.fitpeo_assignment.base.BaseActivity
import com.app.fitpeo_assignment.base.BaseBindingActivity
import com.app.fitpeo_assignment.databinding.ActivitySplashBinding
import com.app.fitpeo_assignment.utility.networkError


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseBindingActivity<ActivitySplashBinding>() {

    override fun setBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun getActivityContext(): BaseActivity {
        return this@SplashActivity
    }

    override fun initView() {
        super.initView()
        networkError(
            onNetworkAvailable = {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            },
            negativeClickListener = {
                finishAffinity()
            }
        )
    }
}