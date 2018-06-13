package Model.Base

import Model.Interfaces.NeighbourHoodInterface

class Grid(var nXCells: Int, var nYCells: Int, val width: Double, val height: Double, var neighbourhood: NeighbourHoodInterface) {

    val cellWidth: Double = width/nXCells
    val cellHeight: Double = height/nYCells

    var cells: MutableList<MutableList<Cell>> = mutableListOf()

    var cellsCounter: MutableMap<Int, Int> = mutableMapOf()

    var cellIdToColor: MutableMap<Int, Color> = mutableMapOf()

    @Volatile
    var cellsToUpdate: MutableList<Cell> = mutableListOf()

    init {
        for (i in 0 until nXCells){
            cells.add(mutableListOf())
            for (j in 0 until nYCells){
                cells[i].add(j, Cell(i * cellWidth, j * cellHeight, cellHeight, cellWidth))
            }
        }
    }

    fun findCell(x: Double, y: Double): Cell {
        val i = Math.floor(x/cellWidth).toInt()
        val j = Math.floor((y/cellHeight)).toInt()

        return cells[i][j]
    }

    fun addCell(i: Int, j: Int, color: Color, state: Boolean = true): Cell {
        val cell = cells[i][j]
        cell.state = state
        cell.color = color
        cell.ID = cellsCounter.size
        cellsCounter[cell.ID] = 1
        cellIdToColor[cell.ID] = color

        return cell
    }

    fun addCell(cell: Cell, color: Color, state: Boolean = true) {
        cell.state = state
        cell.color = color
        cell.ID = cellsCounter.size
        cellsCounter[cell.ID] = 1
        cellIdToColor[cell.ID] = color

    }

    fun addCellWithId(cell: Cell, id: Int, state: Boolean = true) {
        cell.state = state
        cell.color = cellIdToColor[id]!!
        cell.ID = id

        if(cellsCounter.containsKey(cell.ID)){
            cellsCounter[cell.ID] = cellsCounter[cell.ID]!!.plus(1)

        } else {
            cellsCounter[cell.ID] = 1
//            cellIdToColor[cell.ID] = color
        }

    }


}