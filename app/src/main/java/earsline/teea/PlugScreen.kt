package earsline.teea

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun QuizScreen() {

    Scaffold(topBar = {
        TopAppBar {
            Text("Winline")
        }
    }, backgroundColor = Color(0xff113773)) {
        Quiz(modifier = Modifier.padding(it))
    }
}

@Composable
fun Quiz(modifier: Modifier) {
    val facts = rememberSaveable {
        listOf(
            "Football was essentially born from Rugby. In 1882, new rules were added to Rugby that evolved the game so each team gets three tries to advance the ball five yards. Touchdowns were originally worth four points each.",
            "Larry Lewis is the oldest person to run a 100-yard dash in 17.8 seconds. He set the world record at 101 for runners over 100.",
            "The popular yo-yo toy began as a weapon in the Philippines in the 16th century before being distributed in the United States as a toy in 1929. It was four pounds and had a 20-foot-long cord at the time.",
            "Only six of all the World Cups have been won by the host country.",
            "On average soccer players run about seven miles each throughout the span of one game. This is a per-player statistic.",
            "The first recorded Ancient Olympic race, held in 776 BC, was won by Corubus of Elis, a chef. He won the stadion race which was a 200-yard sprint.",
            "A badminton shuttlecock weighs approximately 0.17 oz (5 g).",
            "Olympic Gold Medals are made, mostly, of sterling silver, not gold. In fact, they haven't been made of pure gold since 1912!",
        )
    }
    val images =
        rememberSaveable { listOf(R.drawable.basketball, R.drawable.football, R.drawable.tennis) }

    var shouldFinishQuiz by rememberSaveable { mutableStateOf(false) }

    var index by rememberSaveable { mutableStateOf(0) }
    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = images[Random.nextInt(0, images.size)]),
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        Text(
            text = if (!shouldFinishQuiz) "Did you know?" else "That's it for today",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )


        Text(
            text = if (!shouldFinishQuiz) facts[index] else "Come back tomorrow",
            fontSize = 33.sp,
            color = Color.White
        )
        if (!shouldFinishQuiz) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                    if (index != 0) index--
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

                IconButton(onClick = {
                    if (index < facts.size - 1) index++
                    else shouldFinishQuiz = true
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

    }
}