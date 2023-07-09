package com.app.fitpeo_assignment.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import com.app.fitpeo_assignment.base.BaseActivity
import com.app.fitpeo_assignment.base.BaseBindingActivity
import com.app.fitpeo_assignment.databinding.ActivitySplashBinding
import com.app.fitpeo_assignment.utility.networkError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                redirectToActivity()
            },
            negativeClickListener = {
                finishAffinity()
            }
        )
    }

    private fun redirectToActivity() {
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            delay(2000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}