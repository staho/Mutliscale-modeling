class GameOfLife(var size: Int, var height: Float, var width: Float) {
    val grid: MutableList<MutableList<Rectangle>> = mutableListOf()

    init {

        var x = 0f
        var y = 0f

        for (i in 0 until size) {
            grid.add(mutableListOf())
            for(j in 0 until size) {
                grid.last().add(Rectangle(x, y, height, width, i, j))
                x += width
            }
            x = 0f
            y += height
        }
    }

    fun nextStep() {
        val toUpdate: MutableList<Rectangle> = mutableListOf()
        for(rects in grid){
            for(rect in rects){
                if(rect.indexI != 0 && rect.indexI != grid.size - 1 &&
                        rect.indexJ != 0 && rect.indexJ != rects.size - 1){
                    val count = countNeighbourhood(rect)
                    if((count == 2 && rect.state) || count == 3) {
                        rect.newState = true
                        toUpdate.add(rect)
                    }
                    else if(count < 2 || count > 3){
                        rect.newState = false
                        toUpdate.add(rect)
                    }

                }
            }
        }
        for(rect in toUpdate) rect.state = rect.newState
    }

    fun countNeighbourhood(rect: Rectangle): Int {
        var count = 0
        val i = rect.indexI
        val j = rect.indexJ

        for(x in i - 1 until i + 2){
            for(y in j - 1 until j + 2){
                if(grid[x][y].state){
                    if(i == x && y == j) {}
                    else count++
                }
            }
        }

        return count
    }



}