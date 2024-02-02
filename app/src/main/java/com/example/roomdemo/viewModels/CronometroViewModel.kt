package com.example.roomdemo.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdemo.repositories.CronoRepository
import com.example.roomdemo.state.CronoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CronometroViewModel @Inject constructor(private val repository: CronoRepository) :
  ViewModel() {
  var state by mutableStateOf(CronoState())
    private set

  var cronoJob by mutableStateOf<Job?>(null)
    private set

  var time by mutableLongStateOf(0L)

  fun getCronoById(id: Long) {
    viewModelScope.launch(Dispatchers.IO) {
      repository.getCronoById(id).collect() {
        time = it.crono
        state  = state.copy(title = it.title)
      }
    }
  }

  fun onValue(value: String) {
    state = state.copy(title = value)
  }

  fun start() {
    state = state.copy(active = true)
  }

  fun pause() {
    state = state.copy(active = false, showSaveButton = true)
  }

  fun stop() {
    cronoJob?.cancel()
    time = 0
    state = state.copy(
      active = false,
      showSaveButton = false,
      showTextField = false
    )
  }

  fun showTextField() {
    state = state.copy(showTextField = true)
  }

  fun cronos() {
    if (state.active) {
      cronoJob?.cancel()
      cronoJob = viewModelScope.launch {
        while (true) {
          delay(1000)
          time += 1000
        }
      }
    } else {
      cronoJob?.cancel()
    }
  }
}