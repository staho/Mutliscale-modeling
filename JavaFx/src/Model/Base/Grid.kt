package Model.Base

import Model.Interfaces.NeighbourHoodInterface

class Grid(var nXCells: Int, var nYCells: Int, val width: Double, val height: Double, val neighbourhood: NeighbourHoodInterface) {

    val cellWidth: Double = width/nXCells
    val cellHeight: Double = height/nYCells

    var cells: MutableList<MutableList<Cell>> = mutableListOf()

    init {
        for (i in 0 until nXCells){
            cells.add(mutableListOf())
            for (j in 0 until nYCells){
                cells[i].add(j, Cell(i * cellWidth, j * cellHeight, cellHeight, cellWidth))
            }
        }
    }


}