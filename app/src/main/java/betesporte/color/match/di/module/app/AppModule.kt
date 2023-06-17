package betesporte.color.match.di.module.app

import betesporte.color.match.di.module.activity.ActivityModule
import betesporte.color.match.di.module.fragment.FragmentModule
import betesporte.color.match.di.module.navigation.NavigationModule
import dagger.Module

@Module(includes = [
    ActivityModule::class,
    FragmentModule::class,
    NavigationModule::class
])
interface AppModule
