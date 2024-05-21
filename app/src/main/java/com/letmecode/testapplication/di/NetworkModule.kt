package com.letmecode.testapplication.di

import com.letmecode.data.RetrofitClient
import com.letmecode.data.reviews.repository.ReviewsRepositoryImpl
import com.letmecode.domain.repository.ReviewsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(): RetrofitClient {
        return RetrofitClient()
    }

    @Singleton
    @Provides
    fun provideReviewsApiService(
        retrofitClient: RetrofitClient
    ) = retrofitClient.provideReviewsApiService()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindReviewsRepository(reviewsRepositoryImpl: ReviewsRepositoryImpl): ReviewsRepository
}