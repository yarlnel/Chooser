package servolne.cima.presentation.fragments

import servolne.cima.databinding.FragmentGameBinding
import servolne.cima.presentation.common.backpress.BackPressedStrategyOwner
import servolne.cima.presentation.common.fragment.BaseFragment

class GameFragment : BaseFragment<FragmentGameBinding>(
    FragmentGameBinding::inflate
), BackPressedStrategyOwner {

    override fun handleBackPress() {
        requireActivity().finish()
    }
}
