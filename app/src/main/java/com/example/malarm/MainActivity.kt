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
import androidx.compose.material3.Switch
import android.content.res.Configuration


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
                .padding(start = 30.dp) // all, horizontal, vertical, start, top, bottom
                .fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                DigitalTime(modifier = Modifier
                                .weight(3f),
                            hour = "01",
                            minute = "37",
                            amOrPm = "AM"
                )
                //Spacer(modifier = Modifier.weight(1f))

                var checked by remember { mutableStateOf(true) }
                Switch(
                    checked = checked,
                    modifier = Modifier
                        .weight(1f),
                    onCheckedChange = {
                        checked = it
                    }
                )
            }
        }
    }

}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun AlarmPreview() {
    MalarmTheme {
        Alarm()
    }
}

@Composable
fun DigitalTime(
    modifier: Modifier = Modifier,
    hour: String,
    minute: String,
    amOrPm: String

) {
    Text(
        text = "$hour:$minute $amOrPm",
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier
    )
}