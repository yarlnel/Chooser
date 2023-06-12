package drop.kansino.coin.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import drop.kansino.coin.R
import drop.kansino.coin.databinding.FragmentGameBinding
import drop.kansino.coin.presentation.common.backpress.BackPressedStrategyOwner
import drop.kansino.coin.presentation.common.fragment.BaseFragment
import drop.kansino.coin.presentation.test.TestGameSideEffect
import drop.kansino.coin.presentation.utils.onclick

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
