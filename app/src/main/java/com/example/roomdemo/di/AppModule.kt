package com.example.roomdemo.di

import android.content.Context
import androidx.room.Room
import com.example.roomdemo.rooms.CronosDatabase
import com.example.roomdemo.rooms.CronosDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  @Singleton
  @Provides
  fun providesCronosDao(cronosDatabase: CronosDatabase): CronosDatabaseDao {
    return cronosDatabase.cronosDao()
  }

  @Singleton
  @Provides
  fun providesCronosDatabase(@ApplicationContext context: Context): CronosDatabase {
    return Room.databaseBuilder(
      context,
      CronosDatabase::class.java,
      "cronos_db"
    ).fallbackToDestructiveMigration()
      .build()
  }
}