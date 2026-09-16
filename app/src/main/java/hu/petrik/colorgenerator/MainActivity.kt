package hu.petrik.colorgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.petrik.colorgenerator.ui.theme.ColorGeneratorTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //dark mode off

            ColorGeneratorTheme(darkTheme = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ColorGenerator(
                        Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ColorGenerator(Modifier: Modifier) {


    var redText by remember {
        mutableStateOf("0")
    }

    var blueText by remember {
        mutableStateOf("0")
    }

    var greenText by remember {
        mutableStateOf("0")
    }

    var red by remember {
        mutableStateOf(0)
    }

    var blue by remember {
        mutableStateOf(0)
    }

    var green by remember {
        mutableStateOf(0)
    }

    var colorBackground = Color(
        red = red,
        green = green,
        blue = blue
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Text(
            "RGB Color Generator",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Adj meg egy 0 és 255 közötti értéket!"
        )
        RGBInput(
            label = "R",
            value = redText,
            onValueChange = {
                redText = it
            }
        )
        RGBInput(
            label = "G",
            value = greenText,
            onValueChange = {
                greenText = it
            }
        )
        RGBInput(
            label = "B",
            value = blueText,
            onValueChange = {
                blueText = it
            }
        )
        Button(
            onClick = {
                red = redText.toIntOrNull()
                    ?.coerceIn(0, 255)
                    ?: 0
                green = greenText.toIntOrNull()
                    ?.coerceIn(0, 255)
                    ?: 0
                blue = blueText.toIntOrNull()
                    ?.coerceIn(0, 255)
                    ?: 0
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Szín keverése")
        }
        Button(
            onClick = {
                red = Random.nextInt(0, 256)
                green = Random.nextInt(0, 256)
                blue = Random.nextInt(0, 256)

                redText = red.toString()
                greenText = green.toString()
                blueText = blue.toString()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Véletlen szín generálás"
            )
        }

        ColorPreview(
            red = red,
            green = green,
            blue = blue,
            color= colorBackground
        )
    }
}

@Composable
fun RGBInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement =
            Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = label,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(30.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text("0 - 255")
            },
            keyboardOptions =
                KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun getTextColor(
    red: Int,
    green: Int,
    blue: Int
) : Color {

    val brightness = (red+green+blue)/3

    return if (brightness > 128) {
        Color.Black
    } else {
        Color.White
    }
}

@Composable
fun ColorPreview(
    red: Int,
    green: Int,
    blue: Int,
    color: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "RGB($red,$green,$blue)",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = getTextColor(red,green,blue)
            )
            Text(
                text =
                    "#%02X%02x%02X".format(
                        red,green,blue
                    ),
                fontSize = 20.sp,
                color = getTextColor(red,green,blue)
            )
        }
    }
}
