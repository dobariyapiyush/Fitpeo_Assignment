package com.app.fitpeo_assignment.utility

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.app.fitpeo_assignment.R

fun Activity.networkError(
    onNetworkAvailable: () -> Unit,
    negativeClickListener: () -> Unit
) {
    if (!isOnline()) {
        showNetworkErrorPopup(onNetworkAvailable, negativeClickListener)
    } else {
        onNetworkAvailable()
    }
}

fun Context.isOnline(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork?.let {
            connectivityManager.getNetworkCapabilities(it)
        }
        networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}

fun Activity.showNetworkErrorPopup(
    onNetworkAvailable: () -> Unit,
    negativeClickListener: () -> Unit
) {
    commonDialog(
        layoutResId = R.layout.dialog_network_error,
        cancelable = false,
        title = getString(R.string.network_error),
        message = getString(R.string.check_your_connection),
        positiveClickListener = { dialog, _ ->
            dialog.dismiss()
            networkError(onNetworkAvailable, negativeClickListener)
        },
        negativeClickListener = {
            negativeClickListener()
        }
    )
}