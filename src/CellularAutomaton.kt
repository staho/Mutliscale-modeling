import processing.core.PApplet
import processing.event.MouseEvent

class CellularAutomaton : PApplet() {

    var rectHeight = 0f
    var rectWidth = 0f
    val gridSize = 100
    var paused = true
    var gameOfLife = GameOfLife(0,0f,0f)

    override fun settings() {
        size(800, 800)
    }

    override fun setup() {
        rectHeight = (height/gridSize).toFloat()
        rectWidth = (width/gridSize).toFloat()

        gameOfLife = GameOfLife(gridSize, rectHeight, rectWidth)

        background(255)
        frameRate(5f)


        val gridList = gameOfLife.grid
        for(listOfRects in gridList) {
            for (rect in listOfRects) {
                if (rect.state) fill(0)
                else fill(255)

                rect(rect.x, rect.y, rect.height, rect.width)

            }
        }
    }

    override fun draw() {
        if(!paused) {
            gameOfLife.nextStep()
            val gridList = gameOfLife.grid
            for(listOfRects in gridList) {
                for (rect in listOfRects) {
                    if (rect.state) fill(0)
                    else fill(255)

                    rect(rect.x, rect.y, rect.height, rect.width)

                }
            }
        }

    }

    override fun mouseClicked(event: MouseEvent?) {
        println(mouseX, mouseY)
        var rect = findRectangle(mouseX.toFloat(), mouseY.toFloat())
        if(rect != null){
            rect.state = !rect.state
            if (rect.state) fill(0)
            else fill(255)

            rect(rect.x, rect.y, rect.height, rect.width)


        }

    }

    fun findRectangle(mouseX: Float, mouseY: Float): Rectangle? {
        val gridList = gameOfLife.grid
        for(listOfRects in gridList) {
            for (rect in listOfRects) {
                if(rect.checkIfInRect(mouseX, mouseY)){
                    return rect
                }
            }
        }
        return null
    }

    override fun keyPressed() {
        if(key == ' '){
            paused = !paused
            println("Paused or unpaused")
        } else if(key == 'r'){
            val gridList = gameOfLife.grid
            for(listOfRects in gridList) {
                for (rect in listOfRects) {
                    rect.state = false
                }
            }
        }
        println(key)
    }

}


fun main(args: Array<String>) {
    PApplet.main("CellularAutomaton")
}
