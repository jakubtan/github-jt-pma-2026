package com.example.myapp001b_dicethrowjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp001b_dicethrowjetpackcompose.ui.theme.MyApp001b_DiceThrowJetpackComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                DiceApp()
            }
        }
    }
}

@Composable
fun DiceApp() {
    val diceSymbols = listOf("⚀", "⚁", "⚂", "⚃", "⚄", "⚅")
    var diceValue by remember { mutableStateOf(1) }
    var isRolling by remember { mutableStateOf(false) }

    // Přidaná logika: Stav pro informační text
    var resultText by remember { mutableStateOf("Click to Roll!") }

    val scope = rememberCoroutineScope()

    val backgroundColor = Color(0xFFF5F3FF)
    val primaryColor = Color(0xFF352060)
    // Přidaná barva pro text
    val secondaryColor = Color(0xFF5E4B8A)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .safeDrawingPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Roll the Dice!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor
        )
        Text(
            text = diceSymbols[diceValue - 1],
            fontSize = 120.sp,
            color = primaryColor,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        // Přidaná logika: Textové pole pro zpětnou vazbu
        Text(
            text = resultText,
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic,
            color = secondaryColor,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            enabled = !isRolling,
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
                contentColor = Color.White
            ),
            onClick = {
                isRolling = true
                resultText = "Rolling..."

                scope.launch {
                    repeat(10) {
                        diceValue = (1..6).random()
                        delay(250)
                    }
                    // Finální hodnota
                    diceValue = (1..6).random()
                    // Zobrazení výsledku
                    resultText = "You rolled a $diceValue!"
                    isRolling = false
                }
            }
        )
        {
            Text(
                text = "Roll",
                fontSize = 26.sp
            )
        }
    }
}