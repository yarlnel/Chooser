package drop.kansino.coin.di.module.app

import drop.kansino.coin.di.module.activity.ActivityModule
import drop.kansino.coin.di.module.fragment.FragmentModule
import drop.kansino.coin.di.module.navigation.NavigationModule
import dagger.Module

@Module(includes = [
    ActivityModule::class,
    FragmentModule::class,
    NavigationModule::class
])
interface AppModule
