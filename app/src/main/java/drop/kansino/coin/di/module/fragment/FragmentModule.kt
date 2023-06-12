package drop.kansino.coin.di.module.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import drop.kansino.coin.presentation.fragments.GameFragment

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun gameFragment(): GameFragment
}
