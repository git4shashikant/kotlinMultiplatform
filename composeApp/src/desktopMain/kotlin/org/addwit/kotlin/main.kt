package org.addwit.kotlin

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val state = rememberWindowState(
        size = DpSize(400.dp, 600.dp),
        position = WindowPosition(300.dp, 300.dp)
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "KotlinMultiplatform",
        state = state,
        alwaysOnTop = true
    ) {
        App()
    }
}