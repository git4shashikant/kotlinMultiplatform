package org.addwit.kotlin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kotlinmultiplatform.composeapp.generated.resources.Res
import kotlinmultiplatform.composeapp.generated.resources.compose_multiplatform
import kotlinx.datetime.Clock
import kotlinx.datetime.IllegalTimeZoneException
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
@Preview
fun App() {
    MaterialTheme {
        var location by remember { mutableStateOf("No location selected") }
        var timeAtLocation: String? by remember { mutableStateOf("No location selected") }

        var showContent by remember { mutableStateOf(false) }
        var showDropdown by remember { mutableStateOf(false) }
        val greeting = remember { Greeting().greet() }

        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Today's date is ${todayDate()}",
                modifier = Modifier.padding(20.dp),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Text(location)

            Row(modifier = Modifier.padding(start = 20.dp, top = 10.dp)) {
                DropdownMenu(
                    expanded = showDropdown,
                    onDismissRequest = { showDropdown = false}
                ) {
                    countries.forEach { (name, zone) ->
                        DropdownMenuItem(
                            text = {   Text(name)},
                            onClick = {
                                location = "$name $zone"
                                timeAtLocation = currentTimeAt(zone)
                                showDropdown = false
                                showContent = true
                            }
                        )
                    }
                }
            }

            Row(modifier = Modifier.padding(start = 20.dp, top = 10.dp)) {
                Button(onClick = { showDropdown = !showDropdown }) {
                    Text("Select location!")
                }

                Button(onClick = { showContent = false
                location = ""}) {
                    Text("Clear!")
                }
            }

            AnimatedVisibility(showContent) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(timeAtLocation?: "")
                    Text("Compose: $greeting")
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                }
            }
        }
    }
}

data class Country(val name: String, val zone: String)
val countries = listOf(
    Country("Japan", "Asia/Tokyo"),
        Country("France", "Europe/Paris"),
    Country("Mexico", "America/Mexico_City"),
    Country("Indonesia", "Asia/Jakarta"),
    Country("Egypt", "Africa/Cairo"),
)

fun todayDate(): String {
    fun LocalDateTime.format() = toString().substringBefore('T')

    val now = Clock.System.now()
    val zone = TimeZone.currentSystemDefault()
    return now.toLocalDateTime(zone).format()
}

fun currentTimeAt(location: String): String? {
    fun LocalTime.formatted() = "$hour:$minute:$second"

    return try {
        val time = Clock.System.now()
        val zone = TimeZone.of(location)
        val localTime = time.toLocalDateTime(zone).time
        "The time in $location is ${localTime.formatted()}"
    } catch (ex: IllegalTimeZoneException) {
        println(ex)
        null
    }
}