package betesporte.color.match.di.module.fragment

import betesporte.color.match.presentation.fragments.ChooserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import betesporte.color.match.presentation.fragments.GameFragment

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun gameFragment(): GameFragment

    @ContributesAndroidInjector
    fun chooserFragment(): ChooserFragment
}
