package drop.kansino.coin.di.module.activity

import drop.kansino.coin.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

    @ContributesAndroidInjector
    fun mainActivity() : MainActivity
}
