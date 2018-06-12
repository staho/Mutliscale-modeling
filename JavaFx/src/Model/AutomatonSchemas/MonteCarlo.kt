package Model.AutomatonSchemas

import Model.Base.Grid
import Model.Base.IndexPoint
import Model.Base.McPreferences
import javafx.embed.swing.SwingFXUtils
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import java.awt.geom.Rectangle2D
import java.awt.image.BufferedImage
import java.util.*

class MonteCarlo(var gc: GraphicsContext, var grid: Grid, val prefs: McPreferences = McPreferences()): Runnable {
    val width = gc.canvas.width
    val height = gc.canvas.height

    override fun run() {
        monteCarlo()
    }

    private fun monteCarlo() {
        val nb = grid.neighbourhood
        var x = true
        val bi = BufferedImage(width.toInt(), height.toInt(), BufferedImage.TYPE_INT_RGB)
        val g2d = bi.createGraphics()
        g2d.background = java.awt.Color.WHITE
        gc.fill = Color.WHITE


        while(x){
            grid.cells.forEachIndexed { i, cellsRow ->
                cellsRow.forEachIndexed { j, cell ->
                    val indexes = nb.computeIndexes(i, j)

                    if (cell.state && indexes.isNotEmpty()) {
                        val currEnergy = calculateEnergy(indexes, i, j)

                        var newId: Int
                        var newEnergy: Int

                        loop@do {
                            newId = randomizeCell(indexes)
                            newEnergy = calculateEnergy(indexes, newId)

                            if(newEnergy <= currEnergy){
                                grid.cellsToUpdate.add(cell)
                                break@loop
                            }

                        } while (!prefs.oneTry)


                        cell.ID = newId
                        println(newId)
                        cell.color = grid.cellIdToColor[cell.ID]!!


                    }
                }

            }

            if(grid.cellsToUpdate.isEmpty()) x = false

            for (cell in grid.cellsToUpdate) {
                g2d.color = cell.color.toAwtColor()
                val rect = Rectangle2D.Double(cell.x, cell.y, cell.width, cell.height)
                g2d.fill(rect)

            }

            gc.drawImage(SwingFXUtils.toFXImage(bi, null), 0.0, 0.0)
            grid.cellsToUpdate.clear()

        }

    }

    private fun calculateEnergy(indexes: List<IndexPoint>, i: Int, j: Int) : Int {
        var energy = 0
        val cellId = grid.cells[i][j].ID

        for(index in indexes){
            if(grid.cells[index.x][index.y].ID != cellId) {
                energy++
            }
        }

        return energy

    }

    private fun calculateEnergy(indexes: List<IndexPoint>, cellId: Int) : Int {
        var energy = 0

        for(index in indexes){
            if(grid.cells[index.x][index.y].ID != cellId) {
                energy++
            }
        }

        return energy

    }

    private fun randomizeCell(indexes: List<IndexPoint>): Int {
        val random = Random()
        if(prefs.chooseMaxNb){
            var maxNb = grid.cells[indexes[0].x][indexes[0].y].ID
            var minNb = grid.cells[indexes[0].x][indexes[0].y].ID
            for (index in indexes){
                var tempId = grid.cells[index.x][index.y].ID

                if (tempId >= maxNb)
                    maxNb = tempId
                if(tempId < minNb)
                    minNb = tempId
            }

            return random.nextInt((maxNb - minNb) + 1) + minNb

        } else {
            return random.nextInt(grid.cellsCounter.size)
        }
    }
}