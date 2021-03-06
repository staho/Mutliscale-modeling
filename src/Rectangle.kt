class Rectangle(val x: Float, val y: Float, val height: Float, val width: Float, val indexI: Int, val indexJ: Int) {
    val xEnd = x + width
    val yEnd = y + height
    var state = false
    var newState: Boolean = true

    public fun checkIfInRect(mouseX: Float, mouseY: Float): Boolean {
        if(mouseX >= x && mouseX <= xEnd) {
            if(mouseY >= y && mouseY <= yEnd){
                return true
            }
        }
        return false
    }
}