package com.app.fitpeo_assignment.activity

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.fitpeo_assignment.adapter.PhotoAdapter
import com.app.fitpeo_assignment.base.BaseActivity
import com.app.fitpeo_assignment.base.BaseBindingActivity
import com.app.fitpeo_assignment.databinding.ActivityMainBinding
import com.app.fitpeo_assignment.network.client.DaggerAppComponent
import com.app.fitpeo_assignment.network.viewmodel.PhotoViewModel
import com.app.fitpeo_assignment.network.viewmodel.PhotoViewModelFactory

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

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
        fetchPhotos()
    }

    private fun fetchPhotos() {
        val appComponent = DaggerAppComponent.create()
        appComponent.inject(this)

        val factory: PhotoViewModelFactory = appComponent.providePhotoViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[PhotoViewModel::class.java]

        adapter = PhotoAdapter()
        with(mBinding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        viewModel.photos.observe(this, { photos ->
            adapter.submitList(photos)
            adapter.showShimmer = false
            adapter.notifyDataSetChanged()
        })

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
                finishAffinity()
            }
        }
    }

}
