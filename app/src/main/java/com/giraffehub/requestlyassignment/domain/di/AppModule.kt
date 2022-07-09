package com.giraffehub.requestlyassignment.domain.di

import android.content.Context
import com.giraffehub.requestlyassignment.BuildConfig
import com.giraffehub.requestlyassignment.data.network.service.CharactersService
import com.giraffehub.requestlyassignment.domain.repository.CharactersRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.requestly.android.okhttp.api.RQCollector
import io.requestly.android.okhttp.api.RQInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkhttpClient(@ApplicationContext appContext: Context): OkHttpClient {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(logging)
        }

        val collector = RQCollector(context=appContext)
        val rqInterceptor = RQInterceptor.Builder(appContext).collector(collector).build()
        client.addInterceptor(rqInterceptor).build()

        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideCharacterService(retrofit: Retrofit): CharactersService {
        return retrofit.create(CharactersService::class.java)
    }

    @Provides
    @Singleton
    fun provideCharactersRespository(charactersService: CharactersService): CharactersRepository {
        return CharactersRepository(charactersService)
    }
}