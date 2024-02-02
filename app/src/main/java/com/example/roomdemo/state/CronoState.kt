package com.example.roomdemo.state

data class CronoState(
  val active: Boolean = false,
  val showSaveButton: Boolean = false,
  val showTextField: Boolean = false,
  val title: String = "",
)