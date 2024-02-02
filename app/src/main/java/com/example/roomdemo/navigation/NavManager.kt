package com.example.roomdemo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.roomdemo.views.AddView
import com.example.roomdemo.views.EditView
import com.example.roomdemo.views.HomeView

@Composable
fun NavManager() {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = "Home") {
    composable("Home") {
      HomeView(navController)
    }
    composable("AddView") {
      AddView(navController)
    }
    composable(
      "EditView/{id}", arguments = listOf(
        navArgument("id") { type = NavType.LongType })
    ) {
      val id = it.arguments?.getLong("id") ?: 0
      EditView(navController, id)
    }
  }
}