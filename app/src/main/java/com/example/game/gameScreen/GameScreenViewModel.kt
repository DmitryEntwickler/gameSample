package com.example.game.gameScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.game.GameUnits.Directions
import com.example.game.GameUnits.Monster
import com.example.game.GameUnits.Wall
import com.example.game.GlobalStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameScreenViewModel : ViewModel() {

    var mGameActive = MutableLiveData(false)
    var mButtonText = MutableLiveData("start")

    val mListOfMonsters = MutableLiveData(
        listOf(
            Monster(10, 3, mNr = 1, mDirection = Directions.LEFT),
            Monster(14, 8, mNr = 2, mDirection = Directions.RIGHT),
            Monster(4, 11, mNr = 3, mDirection = Directions.LEFT),
            Monster(8, 15, mNr = 4, mDirection = Directions.RIGHT),
            Monster(17, 17, mNr = 5, mDirection = Directions.RIGHT),

            Monster(4, 15, mNr = 6, mDirection = Directions.DOWN),
            Monster(12, 4, mNr = 6, mDirection = Directions.UP)
        )
    )

    val mListOfWalls = mutableListOf<Wall>(
        Wall(10, 8),
        Wall(6, 3),
        Wall(5, 5),
        Wall(9, 14),
    )

    init {
        for (x in 0..(GlobalStorage.mNumberOfSquares - 1).toInt()) mListOfWalls.add(Wall(x, 0))
        for (x in 0..(GlobalStorage.mNumberOfSquares - 1).toInt()) mListOfWalls.add(Wall(x, (GlobalStorage.mNumberOfSquares - 1).toInt()))
        for (y in 0..(GlobalStorage.mNumberOfSquares - 1).toInt()) mListOfWalls.add(Wall(0, y))
        for (y in 0..(GlobalStorage.mNumberOfSquares - 1).toInt()) mListOfWalls.add(Wall((GlobalStorage.mNumberOfSquares - 1).toInt(), y))
    }



    fun onButtonClick() {

        if (mGameActive.value == false) {
            mGameActive.value = true
            mButtonText.value = "stop"

            viewModelScope.launch {
                while (mGameActive.value == true) {
                    delay(150)

                    // each timerTick create new ListOfMonsters out of the old one
                    mListOfMonsters.value?.let { oldList ->
                        mListOfMonsters.value = oldList.map {
                            it.copy(mX = it.goHorizontal(mListOfWalls), mY = it.goVertical(mListOfWalls) )
                        }
                    }
                }
            }

        } else {
            mGameActive.value = false
            mButtonText.value = "start"
        }


    }
}