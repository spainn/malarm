package com.example.malarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.malarm.ui.theme.MalarmTheme
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import android.icu.util.Calendar
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var hour by remember { mutableStateOf("0") }
            var minute by remember { mutableStateOf("0") }
            var second by remember { mutableStateOf("0") }
            var amOrPm by remember { mutableStateOf("0") }

            // keep track of the time
            LaunchedEffect(Unit) {
                while (true) {
                    val cal = Calendar.getInstance()
                    hour = cal.get(Calendar.HOUR).run {
                        if (this.toString().length == 1) "0$this" else "$this"
                    }
                    minute = cal.get(Calendar.MINUTE).run {
                        if (this.toString().length == 1) "0$this" else "$this"
                    }
                    second = cal.get(Calendar.SECOND).run {
                        if (this.toString().length == 1) "0$this" else "$this"
                    }
                    amOrPm = cal.get(Calendar.AM_PM).run {
                        if (this == Calendar.AM) "AM" else "PM"
                    }

                    delay(1000)
                }
            }


            MalarmTheme {
                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    Greeting("Android")
//                }

                Alarm()
            }
        }
    }
}

@Composable
fun Alarm(modifier: Modifier = Modifier) {
    Surface(shape = MaterialTheme.shapes.large, shadowElevation = 1.dp) {
        Box(
            modifier = Modifier
                .padding(1.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Row {
                DigitalTime("01", "37", "AM")
                Spacer(modifier = Modifier.width(40.dp))
                DigitalTime("02", "37", "AM")
            }
        }
    }

}

@Preview
@Composable
fun AlarmPreview() {
    MalarmTheme {
        Alarm()
    }
}

@Composable
fun DigitalTime(
    hour: String,
    minute: String,
    amOrPm: String
) {
    Text(
        text = "$hour:$minute $amOrPm",
        style = MaterialTheme.typography.titleLarge
    )
}