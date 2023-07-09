package com.app.fitpeo_assignment.ui.activity

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.fitpeo_assignment.R
import com.app.fitpeo_assignment.base.BaseActivity
import com.app.fitpeo_assignment.base.BaseBindingActivity
import com.app.fitpeo_assignment.databinding.ActivityMainBinding
import com.app.fitpeo_assignment.network.client.DaggerAppComponent
import com.app.fitpeo_assignment.network.viewmodel.PhotoViewModel
import com.app.fitpeo_assignment.network.viewmodel.PhotoViewModelFactory
import com.app.fitpeo_assignment.ui.adapter.PhotoAdapter
import com.app.fitpeo_assignment.utility.commonDialog
import javax.inject.Inject

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    @Inject
    lateinit var factory: PhotoViewModelFactory

    private lateinit var adapter: PhotoAdapter
    private lateinit var viewModel: PhotoViewModel

    override fun setBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getActivityContext(): BaseActivity {
        return this@MainActivity
    }

    override fun initView() {
        super.initView()
        val appComponent = DaggerAppComponent.create()
        appComponent.inject(this)

        viewModel = ViewModelProvider(this, factory)[PhotoViewModel::class.java]

        fetchData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchData() {
        adapter = PhotoAdapter()
        with(mBinding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        viewModel.photos.observe(this) { photos ->
            adapter.submitList(photos)
            adapter.showShimmer = false
            adapter.notifyDataSetChanged()
        }

        viewModel.fetchPhotos()
    }

    override fun initViewListener() {
        super.initViewListener()
        setClickListener(
            mBinding.clickExit
        )
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v) {
            mBinding.clickExit -> {
                onBackPressed()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        commonDialog(layoutResId = R.layout.dialog_exit,
            cancelable = true,
            title = getString(R.string.exit),
            message = getString(R.string.want_to_exit),
            positiveClickListener = { dialog, _ ->
                dialog.dismiss()
                finishAffinity()
            },
            negativeClickListener = {
            })
    }
}
