package betesporte.color.match.di.module.activity

import betesporte.color.match.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

    @ContributesAndroidInjector
    fun mainActivity() : MainActivity
}
