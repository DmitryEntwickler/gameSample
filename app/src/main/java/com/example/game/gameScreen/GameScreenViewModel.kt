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
            Monster(11, 8, mNr = 1, mDirection = Directions.RIGHT),
            Monster(4, 11, mNr = 1, mDirection = Directions.LEFT)
        )
    )

    val mListOfWalls = mutableListOf<Wall>(
        Wall(15, 8),
        Wall(10, 8),
        Wall(3, 3),
        Wall(5, 5)
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
                    delay(250)

                    // each timerTick create new ListOfMonsters out of the old one
                    mListOfMonsters.value?.let { oldList ->
                        mListOfMonsters.value = oldList.map {
                            it.copy(mX = it.go(mListOfWalls))
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