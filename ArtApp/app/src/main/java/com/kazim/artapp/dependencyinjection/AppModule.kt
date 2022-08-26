package com.kazim.artapp.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kazim.artapp.R
import com.kazim.artapp.Repo.ArtRepository
import com.kazim.artapp.Repo.ArtRepositoryInterface
import com.kazim.artapp.api.retrofitApi
import com.kazim.artapp.room.ArtDao
import com.kazim.artapp.room.ArtDatabase
import com.kazim.artapp.util.util.BASE_URL
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDb(@ApplicationContext context:Context)=Room.databaseBuilder(context,ArtDatabase::class.java,"art_Db").build()

    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase)=database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitApi():retrofitApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(retrofitApi::class.java)
    }
    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) =Glide.with(context)
        .setDefaultRequestOptions(
                RequestOptions().placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
        )

    @Singleton
    @Provides
    fun injectNormalRepo(dao:ArtDao,api:retrofitApi) =ArtRepository(dao,api) as ArtRepositoryInterface
}