package br.edu.ifsp.scl.sc3034119.trucoscoreboardcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifsp.scl.sc3034119.trucoscoreboardcompose.ui.theme.TrucoScoreBoardComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrucoScoreBoardComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TrucoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

const val LIMIT_OF_POINTS = 12
const val HAND_OF_ELEVEN = 11

@Composable
fun TrucoScreen(modifier: Modifier = Modifier){
    var scoreTeamOne by remember { mutableIntStateOf(0) }
    var scoreTeamTwo by remember { mutableIntStateOf(0) }

    fun getMatchWinner(scoreOne: Int, scoreTwo: Int, limit: Int = LIMIT_OF_POINTS): String? {
        return when {
            scoreOne >= LIMIT_OF_POINTS -> "Equipe A"
            scoreTwo >= LIMIT_OF_POINTS -> "Equipe B"
            else -> null
        }
    }

    fun resetPoints() {
        scoreTeamOne = 0
        scoreTeamTwo = 0
    }

    fun calculateNewScore(currentScore: Int, pointsToAdd: Int): Int {
        return minOf(currentScore + pointsToAdd, LIMIT_OF_POINTS)
    }

    val matchWinner = getMatchWinner(scoreTeamOne, scoreTeamTwo)
    val isEndGame = matchWinner != null

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Placar da Partida",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TeamBox(teamName = "Equipe A", score = scoreTeamOne)
                ActionButtons(
                    onIncrementOne = { scoreTeamOne = calculateNewScore(scoreTeamOne, 1) },
                    onIncrementThree = { scoreTeamOne = calculateNewScore(scoreTeamOne, 3) },
                    isActionsEnabled = !isEndGame
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TeamBox(teamName = "Equipe B", score = scoreTeamTwo)
                ActionButtons(
                    onIncrementOne = { scoreTeamTwo = calculateNewScore(scoreTeamTwo, 1) },
                    onIncrementThree = { scoreTeamTwo = calculateNewScore(scoreTeamTwo, 3) },
                    isActionsEnabled = !isEndGame
                )
            }
        }

        GameAlertMessage(
            scoreTeamOne = scoreTeamOne,
            scoreTeamTwo = scoreTeamTwo,
            matchWinner = matchWinner,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ResetButton(
            reset = { resetPoints() },
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun TeamBox(
    teamName: String,
    score: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = teamName,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = score.toString(),
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TeamBoxPreview() {
    TeamBox(teamName = "Equipe A", score = 10, modifier = Modifier.padding(16.dp))
}

@Composable
fun ActionButtons(
    onIncrementOne: () -> Unit,
    onIncrementThree: () -> Unit,
    isActionsEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = onIncrementOne,
            enabled = isActionsEnabled
        ) {
            Text("+1 ponto")
        }

        Button(
            onClick = onIncrementThree,
            enabled = isActionsEnabled
        ) {
            Text("+3 pontos")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActionButtonsPreview(){
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ActionButtons(
            onIncrementOne = {},
            onIncrementThree = {},
            isActionsEnabled = true
        )
        ActionButtons(
            onIncrementOne = {},
            onIncrementThree = {},
            isActionsEnabled = false
        )
    }
}

@Composable
fun ResetButton(
    reset: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = reset,
        modifier = modifier.fillMaxWidth()
    ){Text(text = "Reiniciar")}
}

@Preview(showBackground = true)
@Composable
fun ResetButtonPreview(){
    ResetButton(reset = {})
}

@Composable
fun GameAlertMessage(
    scoreTeamOne: Int,
    scoreTeamTwo: Int,
    matchWinner: String?,
    modifier: Modifier = Modifier
){

    val alertMessage = when {
        matchWinner != null -> "Vencedor: $matchWinner!"
        scoreTeamOne == HAND_OF_ELEVEN && scoreTeamTwo == HAND_OF_ELEVEN -> "Mão de Ferro! (Ambas com 11)"
        scoreTeamOne == HAND_OF_ELEVEN -> "Equipe A na Mão de 11!"
        scoreTeamTwo == HAND_OF_ELEVEN -> "Equipe B na Mão de 11!"
        else -> ""
    }

    val messageColor: Color = matchWinner?.let { Color.Green } ?: Color.Red

    if (alertMessage.isNotEmpty()) {
        Text(
            text = alertMessage,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = messageColor,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth()
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GameAlertMessagePreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        GameAlertMessage(scoreTeamOne = 11, scoreTeamTwo = 8, matchWinner = null)
        GameAlertMessage(scoreTeamOne = 5, scoreTeamTwo = 11, matchWinner = null)
        GameAlertMessage(scoreTeamOne = 11, scoreTeamTwo = 11, matchWinner = null)
        GameAlertMessage(scoreTeamOne = 12, scoreTeamTwo = 8, matchWinner = "Equipe A")
    }
}


@Preview(showBackground = true)
@Composable
fun TrucoScreenPreview(){
    TrucoScreen(modifier = Modifier)
}