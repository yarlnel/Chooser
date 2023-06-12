package servolne.cima.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import com.google.android.material.color.utilities.Score
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import servolne.cima.R
import servolne.cima.databinding.FragmentGameBinding
import servolne.cima.presentation.common.backpress.BackPressedStrategyOwner
import servolne.cima.presentation.common.fragment.BaseFragment
import servolne.cima.presentation.test.TestGameSideEffect
import servolne.cima.presentation.utils.onclick
import kotlin.coroutines.CoroutineContext

class GameFragment : BaseFragment<FragmentGameBinding>(
    FragmentGameBinding::inflate
), BackPressedStrategyOwner, CoroutineScope {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        launch {
            binding
                .gameView
                .viewModel
                .container
                .sideEffectFlow
                .collect(::handleSideEffects)
        }
    }

    private fun handleSideEffects(sideEffect: TestGameSideEffect) = when(sideEffect) {
        is TestGameSideEffect.GameFinished -> onGameFinished(sideEffect.score)
    }

    private fun onGameFinished(score: Int) = with(binding) {
        dialogContainer.isGone = false
        txtScore.text = getString(R.string.score_template, score)
        gameView.pauseGame()
    }

    private fun setUpViews() = with(binding) {
        btnResume onclick {
            dialogContainer.isGone = true
            gameView.newGame()
        }
    }

    override fun handleBackPress() {
        requireActivity().finish()
    }

    private val job = Job()
    override val coroutineContext = Dispatchers.Main + job

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
