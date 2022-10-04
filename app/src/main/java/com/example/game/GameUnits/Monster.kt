package com.example.game.GameUnits

data class Monster(
    var mX: Int,
    val mY: Int,
    val mNr: Int,
    var mDirection: Directions
) {
    fun checkCanGoRight(listOfWalls: List<Wall>): Boolean {

        listOfWalls.forEach() { Wall ->
            return !(mX + 1 == Wall.mX && mY == Wall.mY)
        }
        return false
    }

    fun goHorizontal(listOfWalls: List<Wall>): Int {

        var canGoRight: Boolean = false
        var canGoLeft: Boolean = false

        if (mDirection==Directions.RIGHT){
            listOfWalls.forEach() lb@{ Wall ->
                if (mX + 1 == Wall.mX && mY == Wall.mY){
                    canGoRight = false
                    mDirection = Directions.LEFT
                    return mX
                }
                else canGoRight = true
            }
        }
        if (mDirection==Directions.LEFT){
            listOfWalls.forEach() lb@{ Wall ->
                if (mX - 1 == Wall.mX && mY == Wall.mY){
                    canGoLeft = false
                    mDirection = Directions.RIGHT
                    return mX
                }
                else canGoLeft = true
            }
        }


        if (canGoRight) return mX.plus(1)
        if (canGoLeft) return mX.minus(1)
        else return mX
    }

    fun goVertical(listOfWalls: List<Wall>): Int {

        var canGoUp: Boolean = false
        var canGoDown: Boolean = false

        if (mDirection==Directions.UP){
            listOfWalls.forEach() lb@{ Wall ->
                if (mY - 1 == Wall.mY && mX == Wall.mX){
                    canGoUp = false
                    mDirection = Directions.DOWN
                    return mY
                }
                else canGoUp = true
            }
        }
        if (mDirection==Directions.DOWN){
            listOfWalls.forEach() lb@{ Wall ->
                if (mY + 1 == Wall.mY && mX == Wall.mX){
                    canGoDown = false
                    mDirection = Directions.UP
                    return mY
                }
                else canGoDown = true
            }
        }


        if (canGoUp) return mY.minus(1)
        if (canGoDown) return mY.plus(1)
        else return mY
    }

}