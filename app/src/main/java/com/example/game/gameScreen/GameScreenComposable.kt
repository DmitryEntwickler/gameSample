package com.example.game.gameScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game.GlobalStorage

@Composable
fun GameScreenComposable(mGameScreenViewModel: GameScreenViewModel) {

    val mListOfMosters by mGameScreenViewModel.mListOfMonsters.observeAsState()
    val mListOfWalls = mGameScreenViewModel.mListOfWalls

    val mNumberOfSquares = GlobalStorage.mNumberOfSquares
    val mButtonText by mGameScreenViewModel.mButtonText.observeAsState()
    var mCanvasWidth: Float? by remember { mutableStateOf(0F) }
    var mCanvasHeight: Float? by remember { mutableStateOf(0F) }
    var mSquareSize: Int? by remember { mutableStateOf(0) }
    var mGameFieldSize: Int? by remember { mutableStateOf(0) }

    Column {
        Text("Canvas Size in px: $mCanvasWidth x $mCanvasHeight")
        Text("Field: $mNumberOfSquares x $mNumberOfSquares squares")
        Text("Square Size in px: $mSquareSize x $mSquareSize")
        Text("Game Field Size in px: $mGameFieldSize x $mGameFieldSize")

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1F)
        )
        {
            val canvasWidth = size.width    // in Pixel
            val canvasHeight = size.height  // in Pixel
            val squareSize = (canvasWidth / mNumberOfSquares).toInt()
            val gameFieldSize = (squareSize  * mNumberOfSquares).toInt()

            // values for output
            mGameFieldSize = gameFieldSize
            mCanvasWidth = canvasWidth
            mCanvasHeight = canvasHeight
            mSquareSize = squareSize   // in Pixel

            // draw Game Filed
            drawRect(
                color = Color.LightGray,
                topLeft = Offset(x = 0F, y = 0F),
                size = Size(gameFieldSize.toFloat(), gameFieldSize.toFloat())
            )

            // draw all Walls
            mListOfWalls.forEach(){
                drawRect(
                    color = Color.Black,
                    topLeft = Offset(x = (it.mX * squareSize).toFloat(), y = (it.mY * squareSize).toFloat()),
                    size = Size(squareSize.toFloat(), squareSize.toFloat())
                )
            }

            // Net vertical
            for (x in 0..gameFieldSize step (gameFieldSize / mNumberOfSquares).toInt()) {
                drawLine(
                    color = Color.White,
                    start = Offset(x = x.toFloat(), 0F),
                    end = Offset(x = x.toFloat(), y = gameFieldSize.toFloat())
                )
            }
            // Net horizontal
            for (y in 0..gameFieldSize step (gameFieldSize / mNumberOfSquares).toInt()) {
                drawLine(
                    color = Color.White,
                    start = Offset(x = 0F, y = y.toFloat()),
                    end = Offset(x = gameFieldSize.toFloat(), y = y.toFloat())
                )
            }

            // draw all Monsters
            mListOfMosters?.let {
                it.forEach() { monster ->
                    println("-> $it")
                    drawRect(
                        color = Color.Blue,
                        topLeft = Offset(
                            monster.mX * squareSize.toFloat(),
                            monster.mY * squareSize.toFloat(),
                        ),
                        size = Size(squareSize.toFloat(), squareSize.toFloat())
                    )
                }
            }

        } // Canvas Ende

        OutlinedButton(
            onClick = { mGameScreenViewModel.onButtonClick() },
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color.Blue),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
        ) {

            mButtonText?.let {
                Text(
                    text = it,
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }
        }


    }   // End of Column

}
