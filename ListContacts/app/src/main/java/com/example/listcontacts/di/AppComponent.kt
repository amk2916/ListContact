package com.example.listcontacts.di

import android.content.Context
import androidx.room.Room
import com.example.listcontacts.ContactRepository
import com.example.listcontacts.ContactRepositoryImpl
import com.example.listcontacts.ContactsDatabase
import com.example.listcontacts.data.local.ContactDao
import com.example.listcontacts.view.feature.details.ContentActivity
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(
    modules = [
        DataBaseModule::class,
    ]
)
interface AppComponent {
    fun plus(activity: ContentActivity)
}

@Module(
    includes = [
        RepositoriesModule::class
    ]
)
class DataBaseModule(private val context: Context) {

    @Provides
    fun provideContactsDatabase(): ContactsDatabase = Room
        .databaseBuilder(
            context,
            ContactsDatabase::class.java,
            "database_c"
        )
        .allowMainThreadQueries()
        .build()

    @Provides
    fun provideContactDao(
        db: ContactsDatabase
    ): ContactDao = db.getContactDao()

}


@Module
abstract class RepositoriesModule {

    @Binds
    abstract fun bindsContactRepository(impl: ContactRepositoryImpl): ContactRepository
}
