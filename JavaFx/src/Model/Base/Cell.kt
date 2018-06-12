package Model.Base

class Cell(val x: Double, val y: Double, val height: Double, val width: Double, var ID: Int = -1) {
    val xEnd = x + width
    val yEnd = y + height
    var state = false
    var newState: Boolean = true
    lateinit var color: Color

}