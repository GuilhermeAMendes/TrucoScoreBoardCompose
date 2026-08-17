package br.edu.ifsp.scl.sc3034119.trucoscoreboardcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun TrucoScreen(modifier: Modifier = Modifier){
    var scoreTeamOne by remember { mutableIntStateOf(0) }
    var scoreTeamTwo by remember { mutableIntStateOf(0) }
    val LIMIT_OF_POINTS: Int = 12

    fun getMatchWinner(scoreOne: Int, scoreTwo: Int, limit: Int = LIMIT_OF_POINTS): String? {
        return when {
            scoreTeamOne >= LIMIT_OF_POINTS -> "Equipe 1°"
            scoreTeamTwo >= LIMIT_OF_POINTS -> "Equipe 2°"
            else -> null
        }
    }

    var matchWinner = getMatchWinner(scoreTeamOne, scoreTeamTwo)
    var isEndGame = matchWinner != null

    Text(
        text = "Placar da Partida",
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun TrucoScreenPreview(){
    TrucoScreen(modifier = Modifier)
}