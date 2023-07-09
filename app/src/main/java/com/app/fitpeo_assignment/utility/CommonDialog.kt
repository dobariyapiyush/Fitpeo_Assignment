package com.app.fitpeo_assignment.utility

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.app.fitpeo_assignment.R
import ind.pb59.androapps.system_lockscreens.utilities.visible

@SuppressLint("RestrictedApi")
fun Context.commonDialog(
    layoutResId: Int,
    cancelable: Boolean,
    title: String,
    message: String,
    positiveClickListener: (dialog: Dialog, dialogView: View) -> Unit,
    negativeClickListener: (View) -> Unit,
    outsideTouchListener: DialogInterface.OnCancelListener? = null
) {
    val dialogView = LayoutInflater.from(this).inflate(layoutResId, null)

    val builder = AlertDialog.Builder(this, R.style.CustomAlertDialogStyle)
        .setView(dialogView)
        .setCancelable(cancelable)

    val dpi = this.resources.displayMetrics.density
    builder.setView(
        dialogView,
        (19 * dpi).toInt(),
        (5 * dpi).toInt(),
        (14 * dpi).toInt(),
        (5 * dpi).toInt()
    )

    val dialog = builder.create()
    dialog.show()

    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    val positiveButton = dialogView.findViewById<TextView>(R.id.dialog_button_positive)
    val negativeButton = dialogView.findViewById<TextView>(R.id.dialog_button_negative)
    val txtTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
    val txtMessage = dialogView.findViewById<TextView>(R.id.dialog_message)

    if (title.isNotEmpty())
        txtTitle.text = title

    if (message.isNotEmpty()) {
        txtMessage.visible
        txtMessage.text = message
    }

    positiveButton.setOnClickListener {
        positiveClickListener(dialog, dialogView)
    }

    negativeButton.setOnClickListener {
        negativeClickListener(dialogView)
        dialog.dismiss()
    }

    dialog.setCanceledOnTouchOutside(cancelable)
    dialog.setOnCancelListener(outsideTouchListener)
}