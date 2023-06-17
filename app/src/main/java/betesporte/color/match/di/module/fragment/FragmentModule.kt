package betesporte.color.match.di.module.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import betesporte.color.match.presentation.fragments.GameFragment

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun gameFragment(): GameFragment
}
