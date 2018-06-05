package Model.Base

import javafx.scene.paint.Color

class Color(var red: Int, var green: Int, var blue: Int) {
    fun toFxColor(): Color {
        return Color(red.toDouble(), green.toDouble(), blue.toDouble(), 1.0)
    }
    fun toAwtColor() : java.awt.Color {
        return java.awt.Color(red, green, blue)
    }
}