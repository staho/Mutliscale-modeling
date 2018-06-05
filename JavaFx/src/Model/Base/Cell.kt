package Model.Base

class Cell(val x: Double, val y: Double, val height: Double, val width: Double, val indexI: Int = -1, val indexJ: Int = -1) {
    val xEnd = x + width
    val yEnd = y + height
    var state = false
    var newState: Boolean = true
    lateinit var color: Color


    public fun checkIfInRect(mouseX: Float, mouseY: Float): Boolean {
        if(mouseX >= x && mouseX <= xEnd) {
            if(mouseY >= y && mouseY <= yEnd){
                return true
            }
        }
        return false
    }
}