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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


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
    Surface(shadowElevation = 1.dp) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 120.dp)
                    .height(100.dp)
                    .fillMaxWidth()
                    .border(
                        width = 1.33.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(10.dp)
                    )

            ) {

                DigitalTime(modifier = Modifier
                    .weight(3f)
                    .padding(start = 20.dp),
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

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Preview(name = "Light Mode")
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
    val (text, setText) = remember { mutableStateOf("$hour:$minute $amOrPm") }
    var showSlider by remember { mutableStateOf(false) }

    ClickableText(
        text = AnnotatedString(text),
        style = TextStyle(fontSize = 25.sp, color = Color.White),
        modifier = modifier,
        onClick = {
            showSlider = true
            Log.d("DigitalTime ClickableText", "was clicked")
        }
    )

    // WORK HERE TO MODIFY BOTH SLIDERS TO MAKE THEM USABLE WHEN THE TIME IS CLICKED< ALSO
    // HIDE ORIGINAL TIME WHEN CLICKED AND MAKE IT TO WHERE showSlider IS SET BACK
    // TO FALSE
    if (showSlider) {
        // just make this into it's own composable that uses the sliders
        // put sliders in scroll viewZ
        TextSlider()
        IntSlider()
    }
}

@Composable
fun TimeSelection() {
    MalarmTheme {
        // filler
    }
}

// make a list of clickable texts in a column that will each set a variable
// that holds whatever value that was in the text slider (am or pm).  Do the same for
// IntSlider but make it hold integers instead.
@Composable
fun TextSlider(options: List<String> = listOf("AM", "PM")) {

    // will eventually need to be stored in permanent storage
    var amOrPm = "AM"

    Column {
        for (i in 0..1) {
            val (text, setText) = remember { mutableStateOf(options[i]) }
            ClickableText(
                text = AnnotatedString(text),
                style = TextStyle(fontSize = 25.sp, color = Color.White),
                onClick = {
                    amOrPm = options[i]
                    Log.d("test", amOrPm)
                }
            )
        }
    }
}

@Preview
@Composable
fun TextSliderPreview() {
    MalarmTheme{
        TextSlider()
    }
}

@Composable
fun IntSlider() {
    MalarmTheme {

        val (clicked, setClicked) = remember { mutableStateOf(false) }
        val (text, setText) = remember { mutableStateOf("Not clicked.") }

        var time by remember { mutableIntStateOf(0) }

        Column {
            for (i in 0..60) {
                ClickableText(
                    text = if (i >= 10) AnnotatedString(i.toString()) else AnnotatedString("0$i"),
                    style = TextStyle(fontSize = 25.sp, color = Color.White),
                    onClick = {
                        setClicked(!clicked)
                        time = i
                        if (clicked) {
                            setText("Clicked.")
                        } else {
                            setText("Not clicked.")
                        }
                        Log.d("Time:", "$time")
                    },
                    modifier = Modifier.clickable { }
                )
            }
        }
    }
}

@Preview
@Composable
fun IntSliderPreview() {
    MalarmTheme{
        IntSlider()
    }
}