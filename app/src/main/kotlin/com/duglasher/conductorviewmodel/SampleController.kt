package com.duglasher.conductorviewmodel

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.TextView


class SampleController : ViewModelController() {

    private val viewModel by viewModels<SampleViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        val textView = TextView(container.context).apply {
            setTextColor(0xFF000000.toInt())
            textSize = 26F
        }

        val view = FrameLayout(container.context).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            background = ColorDrawable(0xFFFFFFFF.toInt())

            addView(textView, FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.CENTER))
        }

        // LiveData has no observers even after a configuration change
        Log.d(TAG, "hasObservers should be false: ${viewModel.getLiveData().hasObservers()}")

        viewModel.getLiveData().observe(viewLifecycleOwner, {
            textView.text = "ViewModel created at: $it"
        })

        return view
    }

    private companion object {
        private const val TAG = "SampleController"
    }

}