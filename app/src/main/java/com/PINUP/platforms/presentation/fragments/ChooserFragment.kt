package com.PINUP.platforms.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import com.PINUP.platforms.R
import com.PINUP.platforms.databinding.FragmentChooserBinding
import com.PINUP.platforms.presentation.common.fragment.BaseFragment
import com.PINUP.platforms.presentation.utils.onclick
import com.PINUP.platforms.presentation.view.chooser.ChooserSideEffect
import com.PINUP.platforms.presentation.view.chooser.ChooserState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChooserFragment : BaseFragment<FragmentChooserBinding>(FragmentChooserBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        lifecycleScope.launch {
            with(binding.chooser.viewModel.container) {
                stateFlow.collectLatest(::handleState)
            }
        }
        lifecycleScope.launch {
            with(binding.chooser.viewModel.container) {
                sideEffectFlow.collect(::handleSideEffect)
            }
        }
    }


    private fun handleState(state: ChooserState) = with(state) {
        when(state.btnState) {
            ChooserState.BtnState.Red -> {
                binding.btnNext.isGone = false
                binding.btnNext.background = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.btn_shape_red
                )
            }
            ChooserState.BtnState.Green -> {
                binding.btnNext.isGone = false
                binding.btnNext.background = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.btn_shape_green
                )
            }

            ChooserState.BtnState.None -> {
                binding.btnNext.isGone = true
            }
        }
    }
    private fun handleSideEffect(sideEffect: ChooserSideEffect) = when(sideEffect) {
        is ChooserSideEffect.GameFinished -> onGameFinished(sideEffect.score)
    }


    private fun onGameFinished(score: Int) = with(binding) {
        chooser.pauseGame()
        dialogContainer.isGone = false
        val prefs = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val oldMaxScore = prefs.getInt("MAX_SCORE", 0)
        if (score > oldMaxScore) with(prefs.edit()) {
            putInt("MAX_SCORE", score)
            apply()
        }
        maxScore.text = "MaxScore: $oldMaxScore"
        lastScore.text = "LastScore: $score"
    }

    private fun setUpListeners() = with(binding) {
        btnNext onclick chooser.viewModel::nextLevel
        btnResume onclick {
            dialogContainer.isGone = true
            binding.chooser.viewModel.restart()
            binding.chooser.resumeGame()
        }
    }
}