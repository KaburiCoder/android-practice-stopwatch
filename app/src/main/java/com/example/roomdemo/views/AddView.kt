package com.example.roomdemo.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.roomdemo.R
import com.example.roomdemo.components.CircleButton
import com.example.roomdemo.components.FloatButton
import com.example.roomdemo.components.MainIconButton
import com.example.roomdemo.components.MainTextField
import com.example.roomdemo.components.MainTitle
import com.example.roomdemo.components.formatTimer
import com.example.roomdemo.models.Cronos
import com.example.roomdemo.viewModels.CronometroViewModel
import com.example.roomdemo.viewModels.CronosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddView(navController: NavController) {
  Scaffold(topBar = {
    CenterAlignedTopAppBar(title = { MainTitle(title = "ADD CRONO") },
      colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
      navigationIcon = {
        MainIconButton(icon = Icons.Default.ArrowBack) {
          navController.popBackStack()
        }
      })
  }, floatingActionButton = {
    FloatButton {
      navController.navigate("AddView")
    }
  }) {
    Column {
      ContentAddView(it, navController)
    }
  }
}

@Composable
fun TestView(vm: CronometroViewModel = hiltViewModel()) {
  val state = vm.state

  LaunchedEffect(key1 = state.active) {
    vm.cronos()
  }

  Box(
    modifier = Modifier
      .padding(10.dp)
      .background(color = Color.Red)
  ) {
    Text(
      text = formatTimer(timer = vm.time), fontSize = 50.sp, fontWeight = FontWeight.Bold
    )
    Button(onClick = { vm.start() }) {
      Text(text = "dadadsada")
    }
  }
}

@Composable
fun ContentAddView(
  it: PaddingValues,
  navController: NavController,
  vm: CronometroViewModel = hiltViewModel(),
  dbVm: CronosViewModel = hiltViewModel()
) {
  val state = vm.state

  LaunchedEffect(key1 = state.active) {
    vm.cronos()
  }

  Column(
    modifier = Modifier
      .padding(it)
      .padding(top = 30.dp)
      .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = formatTimer(timer = vm.time), fontSize = 50.sp, fontWeight = FontWeight.Bold
    )

    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(vertical = 16.dp)) {
      CircleButton(
        icon = R.drawable.play, enabled = !state.active
      ) {
        vm.start()
      }

      CircleButton(
        icon = R.drawable.pause, enabled = state.active
      ) {
        vm.pause()
      }

      CircleButton(
        icon = R.drawable.stop, enabled = state.showSaveButton
      ) {
        vm.stop()
      }

      CircleButton(
        icon = R.drawable.save, enabled = state.showSaveButton
      ) {
        vm.showTextField()
      }
    }

    if (state.showTextField) {
      MainTextField(value = state.title, onValueChange = { vm.onValue(it) }, label = "Title")

      Button(onClick = {
        dbVm.addCrono(
          Cronos(
            title = state.title, crono = vm.time
          )
        )
        vm.stop()
        navController.popBackStack()
      }) {
        Text(text = "저장")
      }
    }
  }
}