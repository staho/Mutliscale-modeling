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

class MonteCarloTask(var gc: GraphicsContext, var grid: Grid, val prefs: McPreferences = McPreferences()): Runnable {
    private val width = gc.canvas.width
    private val height = gc.canvas.height
    @Volatile var running = true

    override fun run() {
        monteCarlo()
        println("MonteCarloTask has ended on thread: ${Thread.currentThread().id}")

    }

    private fun monteCarlo() {
        val nb = grid.neighbourhood
        var x = true
        var first = true
        val bi = BufferedImage(width.toInt(), height.toInt(), BufferedImage.TYPE_INT_RGB)
        val g2d = bi.createGraphics()
        g2d.background = java.awt.Color.WHITE
        gc.fill = Color.WHITE


        while(x && running){
            grid.cells.forEachIndexed { i, cellsRow ->
                cellsRow.forEachIndexed { j, cell ->
                    val indexes = nb.computeIndexes(i, j)

                    if (cell.state && indexes.isNotEmpty()) {
                        if(first) grid.cellsToUpdate.add(cell)
                        val currEnergy = calculateEnergy(indexes, i, j)

                        var newId: Int
                        var newEnergy: Int

                        loop@do {
                            newId = randomizeCell(indexes)
                            newEnergy = calculateEnergy(indexes, newId)

                            if(newEnergy <= currEnergy){
                                grid.cellsToUpdate.add(cell)
                                cell.ID = newId
                                cell.color = grid.cellIdToColor[cell.ID]!!

                                break@loop
                            }

                        } while (!prefs.oneTry)

                    }
                }

            }

            if(grid.cellsToUpdate.isEmpty()) x = false
            first = false
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

            //selecting random neighbour
            val index = random.nextInt(indexes.size)
            return grid.cells[indexes[index].x][indexes[index].y].ID

        } else {
            return random.nextInt(grid.cellsCounter.size)
        }
    }
}