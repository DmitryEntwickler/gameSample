package com.example.game.gameScreen

import android.graphics.Bitmap
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game.GlobalStorage
import com.example.game.R

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

    var mImageWall = ImageBitmap.imageResource(id = R.drawable.wall)
    var mImageMonster = ImageBitmap.imageResource(id = R.drawable.monster)


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
                drawImage(
                    image = mImageWall,
                    srcSize = IntSize(30,30),
                    srcOffset = IntOffset.Zero,
                    dstOffset =  IntOffset(it.mX * squareSize, it.mY * squareSize),
                    dstSize =   IntSize(squareSize,squareSize),
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
                    drawImage(
                        image = mImageMonster,
                        srcSize = IntSize(30,30),
                        srcOffset = IntOffset.Zero,
                        dstOffset =  IntOffset(monster.mX * squareSize, monster.mY * squareSize),
                        dstSize =   IntSize(squareSize,squareSize),
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
