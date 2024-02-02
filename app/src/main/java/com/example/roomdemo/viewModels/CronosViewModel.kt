package com.example.roomdemo.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdemo.models.Cronos
import com.example.roomdemo.repositories.CronoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CronosViewModel @Inject constructor(private val repository: CronoRepository) : ViewModel() {
  private val _cronosList = MutableStateFlow<List<Cronos>>(emptyList())
  val cronosList = _cronosList.asStateFlow()

  fun lauchList() {
    viewModelScope.launch(Dispatchers.IO) {
      repository.getAllCronos().collect {
        if (it.isEmpty()) {
          _cronosList.value = emptyList()
        } else {
          _cronosList.value = it
        }
      }
    }
  }

  fun addCrono(crono: Cronos) = viewModelScope.launch { repository.addCrono(crono) }
  fun updateCrono(crono: Cronos) = viewModelScope.launch { repository.updateCrono(crono) }
  fun deleteCrono(crono: Cronos) = viewModelScope.launch { repository.deleteCrono(crono) }
}