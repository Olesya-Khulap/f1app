package com.example.f1app2.di

import com.example.f1app2.domain.repository.F1Repository
import com.example.f1app2.data.repository.F1RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindF1Repository(
        f1RepositoryImpl: F1RepositoryImpl
    ): F1Repository
}
