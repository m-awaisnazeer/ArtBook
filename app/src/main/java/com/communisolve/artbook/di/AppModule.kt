package com.communisolve.artbook.di

import android.content.Context
import androidx.room.Room
import com.communisolve.artbook.api.RetrofitAPI
import com.communisolve.artbook.roomdb.ArtDatabase
import com.communisolve.artbook.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun injectRoomDatabase(@ApplicationContext context: Context)=
        Room.databaseBuilder(
            context,ArtDatabase::class.java,"ArtBookDB"
        ).build()

    @Singleton
    @Provides
    fun injectDao(
        database: ArtDatabase
    ) = database.artDao()

    fun injectRetrofitAPI():RetrofitAPI{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitAPI::class.java)
    }


}