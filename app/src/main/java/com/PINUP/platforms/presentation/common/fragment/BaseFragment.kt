package com.PINUP.platforms.presentation.common.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.viewbinding.ViewBinding
import com.PINUP.platforms.presentation.MainActivity
import dagger.android.support.DaggerFragment

abstract class BaseFragment<VB : ViewBinding> constructor(
    private val bindingBlock: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : DaggerFragment() {

    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewBinding = bindingBlock(inflater, container, false)
        this.binding = viewBinding

        return viewBinding.root
    }

    fun ultimateOnBackPressed() {
        val mainActivity = requireActivity() as? MainActivity ?: return
        mainActivity.ultimateOnBackPressed()
    }

    fun toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), message, duration).show()
    }
}
