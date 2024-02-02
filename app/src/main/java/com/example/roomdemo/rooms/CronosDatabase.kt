package com.example.roomdemo.rooms

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomdemo.models.Cronos

@Database(entities = [Cronos::class], version = 1, exportSchema = false)
abstract class CronosDatabase : RoomDatabase() {
  abstract fun cronosDao(): CronosDatabaseDao
}