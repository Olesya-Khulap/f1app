package com.example.f1app2.di

import android.content.Context
import androidx.room.Room
import com.example.f1app2.data.local.F1Database
import com.example.f1app2.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideF1Database(@ApplicationContext context: Context): F1Database {
        return Room.databaseBuilder(
            context.applicationContext,
            F1Database::class.java,
            F1Database.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideRaceDao(database: F1Database): RaceDao {
        return database.raceDao()
    }

    @Provides
    fun provideDriverDao(database: F1Database): DriverDao {
        return database.driverDao()
    }

    @Provides
    fun provideTeamDao(database: F1Database): TeamDao {
        return database.teamDao()
    }

    @Provides
    fun provideConstructorDao(database: F1Database): ConstructorDao {
        return database.constructorDao()
    }

    @Provides
    fun provideTeamDriverDao(database: F1Database): TeamDriverDao {
        return database.teamDriverDao()
    }
}
