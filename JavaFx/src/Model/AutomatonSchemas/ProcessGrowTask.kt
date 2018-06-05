package Model.AutomatonSchemas

import Model.Base.Color
import Model.Base.Grid

class ProcessGrowTask (var grid: Grid, val lock: java.lang.Object): Runnable {

    override fun run() {
        checkCells()
    }



    private fun checkCells() = synchronized(lock) {

        val nb = grid.neighbourhood

        grid.cells.forEachIndexed { i, cellsRow ->
            cellsRow.forEachIndexed { j, cell ->
                if(cell.state) {
                    for (index in nb.computeIndexes(i, j)) {

                        val curCell = grid.cells[index.x][index.y]
                        if (!curCell.state) {
                            curCell.state = true
                            curCell.color = cell.color
                            grid.cellsToUpdate.add(curCell)
                        }
                    }
                }
            }
        }

        lock.notifyAll()

    }
}