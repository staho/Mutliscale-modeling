package Model.Base

import Model.Interfaces.NeighbourHoodInterface

class Grid(var nXCells: Int, var nYCells: Int, val width: Float, val height: Float, val neighbourhood: NeighbourHoodInterface) {

    val cellWidth: Float = nXCells/width
    val cellHeight: Float = nYCells/height

    var cells: MutableList<Cell> = mutableListOf()

    fun setCell(x: Float, y: Float) {
        var cell = Cell(x, y, cellHeight, cellWidth)
        cells.add(cell)
    }

}