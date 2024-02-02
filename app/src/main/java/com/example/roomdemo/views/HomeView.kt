package com.example.roomdemo.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.roomdemo.components.CronCard
import com.example.roomdemo.components.FloatButton
import com.example.roomdemo.components.MainTitle
import com.example.roomdemo.components.formatTimer
import com.example.roomdemo.models.Cronos
import com.example.roomdemo.viewModels.CronosViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController) {
  Scaffold(topBar = {
    CenterAlignedTopAppBar(
      title = { MainTitle(title = "CRONO APP") },
      colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
  }, floatingActionButton = {
    FloatButton {
      navController.navigate("AddView")
    }
  }) {
    ContentHomeView(it, navController)
  }
}

@Composable
fun ContentHomeView(
  it: PaddingValues, navController: NavController, dbVm: CronosViewModel = hiltViewModel()
) {
  LaunchedEffect(key1 = Unit) {
    dbVm.lauchList()
  }

  Column(modifier = Modifier.padding(it)) {
    val cronosList: List<Cronos> by dbVm.cronosList.collectAsState()
    val strings: List<String> = listOf("", "2")

    LazyColumn {
      items(cronosList) { item ->
        val delete = SwipeAction(icon = rememberVectorPainter(image = Icons.Default.Delete),
          background = Color.Red,
          onSwipe = { dbVm.deleteCrono(item) })

        SwipeableActionsBox(endActions = listOf(delete), swipeThreshold = 270.dp) {
          CronCard(title = item.title, crono = formatTimer(item.crono)) {
            navController.navigate("EditView/${item.id}")
          }
        }
      }
    }
  }
}
