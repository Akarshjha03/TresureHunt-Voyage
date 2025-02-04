package com.example.voyagegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voyagegame.ui.theme.VoyageGameTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VoyageGameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CaptainGame(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CaptainGame(modifier: Modifier = Modifier) {
    val treasureFound = remember { mutableStateOf(0) }
    val fuel = remember { mutableStateOf(5) }
    val direction = remember { mutableStateOf("North") }
    val gameStatus = remember { mutableStateOf("Exploring the seas...") }
    val treasuresToWin = 5

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Treasure Found: ${treasureFound.value}")
        Text(text = "Current Direction: ${direction.value}")
        Text(text = "Fuel Remaining: ${fuel.value}")
        Text(text = "Game Status: ${gameStatus.value}")

        if (fuel.value > 0 && treasureFound.value < treasuresToWin) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DirectionButton("Sail East", treasureFound, direction, fuel)
                DirectionButton("Sail West", treasureFound, direction, fuel)
                DirectionButton("Sail North", treasureFound, direction, fuel)
                DirectionButton("Sail South", treasureFound, direction, fuel)
            }
        } else {
            gameStatus.value = if (treasureFound.value >= treasuresToWin) {
                "Congratulations! You won the voyage!"
            } else {
                "Game Over! You're out of fuel!"
            }
            Button(onClick = {
                treasureFound.value = 0
                fuel.value = 5
                direction.value = "North"
                gameStatus.value = "Exploring the seas..."
            }) {
                Text("Restart Game")
            }
        }
    }
}


@Composable
fun DirectionButton(
    directionText: String,
    treasureFound: MutableState<Int>,
    direction: MutableState<String>,
    fuel: MutableState<Int>
) {
    Button(onClick = {
        direction.value = directionText.split(" ")[1]
        fuel.value -= 1
        if (Random.nextBoolean()) {
            treasureFound.value += 1
        } else if (Random.nextInt(10) < 3) { // 30% chance for a storm event
            fuel.value -= 1
        }
    }) {
        Text(text = directionText)
    }
}

@Preview(showBackground = true)
@Composable
fun CaptainGamePreview() {
    VoyageGameTheme {
        CaptainGame()
    }
}
