package com.example.roomdemo.repositories

import com.example.roomdemo.models.Cronos
import com.example.roomdemo.rooms.CronosDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CronoRepository @Inject constructor(private val cronosDatabaseDao: CronosDatabaseDao) {
  suspend fun addCrono(crono: Cronos) = cronosDatabaseDao.insert(crono)
  suspend fun updateCrono(crono: Cronos) = cronosDatabaseDao.update(crono)
  suspend fun deleteCrono(crono: Cronos) = cronosDatabaseDao.delete(crono)
  fun getAllCronos(): Flow<List<Cronos>> =
    cronosDatabaseDao.getCronos().flowOn(Dispatchers.IO).conflate()
  fun getCronoById(id: Long): Flow<Cronos> =
    cronosDatabaseDao.getCronosById(id).flowOn(Dispatchers.IO).conflate()
}