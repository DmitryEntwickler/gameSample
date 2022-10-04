package com.example.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.game.gameScreen.GameScreenComposable
import com.example.game.gameScreen.GameScreenViewModel
import com.example.game.ui.theme.GameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameTheme {
                Surface(color = MaterialTheme.colors.background) {
                    GameScreenComposable(mGameScreenViewModel = GameScreenViewModel())
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GameTheme {
        GameScreenComposable(mGameScreenViewModel = GameScreenViewModel())
    }
}