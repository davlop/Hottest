package com.davlop.hottest.di

import android.app.Application
import androidx.room.Room
import com.davlop.hottest.data.source.local.AppDatabase
import com.davlop.hottest.data.source.local.room.ProductDao
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "db").build()

    @Singleton
    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao = database.productDao()


    @Singleton
    @Provides
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseStorage() = FirebaseStorage.getInstance()

}