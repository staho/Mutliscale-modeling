package Model.Base

import javafx.scene.paint.Color

class Color(var red: Int, var green: Int, var blue: Int) {
    fun toFxColor(): Color {
        val r = red/255.0
        val g = green/255.0
        val b = blue/255.0
        return Color(r, g, b, 1.0)
    }
    fun toAwtColor() : java.awt.Color {
        return java.awt.Color(red, green, blue)
    }
}