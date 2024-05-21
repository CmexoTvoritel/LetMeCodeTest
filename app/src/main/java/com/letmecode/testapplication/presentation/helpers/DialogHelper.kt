package com.letmecode.testapplication.presentation.helpers

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.letmecode.testapplication.databinding.CustomSimpleAlertDialogBinding
import com.letmecode.testapplication.resources.R
import dagger.hilt.android.scopes.ActivityScoped
import java.util.Calendar
import javax.inject.Inject

@ActivityScoped
class DialogHelper @Inject constructor(
    private val activity: Activity
) {

    private var currentDialog: Dialog? = null

    fun showInternetErrorDialog() {
        showSimpleDialog(
            title = R.string.dialog_internet_error_title,
            subtitle = R.string.dialog_internet_error_subtitle,
            positiveText = R.string.dialog_internet_button
        ) {}
    }

    fun showCalendarForSearch(dateCallback: (day: Int, month: Int, year: Int) -> Unit) {
        showCalendarDialog(dateCallback = dateCallback)
    }

    private fun showSimpleDialog(
        @StringRes title: Int,
        @StringRes subtitle: Int = -1,
        @StringRes positiveText: Int? = null,
        positiveCallback: () -> Unit
    ) {
        currentDialog?.cancel()
        currentDialog = Dialog(activity).apply {
            val binding = CustomSimpleAlertDialogBinding.inflate(activity.layoutInflater)
            setContentView(binding.root)
            window?.let {
                it.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                if(positiveText != null) {
                    binding.csadButtonPositive.text = activity.getString(positiveText)
                }
                binding.csadTitle.text = activity.getString(title)
                binding.csadSubtitle.also { textView ->
                    if (subtitle == -1) {
                        textView.visibility = View.GONE
                    } else {
                        textView.text = activity.getString(subtitle)
                    }
                }
                it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                binding.csadButtonPositive.setOnClickListener {
                    positiveCallback.invoke()
                    dismiss()
                }
            }
            show()
        }
    }

    private fun showCalendarDialog(dateCallback: (day: Int, month: Int, year: Int) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            activity,
            { _, selectedYear, selectedMonth, selectedDay ->
                calendar.set(selectedYear, selectedMonth, selectedDay)
                dateCallback.invoke(selectedDay, selectedMonth + 1, selectedYear)
            }, year, month, day)
        datePickerDialog.show()
    }
}