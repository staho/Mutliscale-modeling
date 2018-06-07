package Model.AutomatonSchemas

import Model.Base.Grid
import javafx.application.Platform
import javafx.embed.swing.SwingFXUtils
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import java.awt.geom.Rectangle2D
import java.awt.image.BufferedImage
import kotlin.concurrent.thread

class CellGrowTask(var gc: GraphicsContext, var grid: Grid) : Runnable{
    val width = gc.canvas.width
    val height = gc.canvas.height

    override fun run() {
        updateFromGrid()

    }


    private fun updateFromGrid() {
            val nb = grid.neighbourhood
            var x = true
            val bi = BufferedImage(width.toInt(), height.toInt(), BufferedImage.TYPE_INT_RGB)
            val g2d = bi.createGraphics()
            g2d.background = java.awt.Color.WHITE
            gc.fill = Color.WHITE


        while(x){
            grid.cells.forEachIndexed { i, cellsRow ->
                    cellsRow.forEachIndexed { j, cell ->
                        if (cell.state) {
                            for (index in nb.computeIndexes(i, j)) {
                                val curCell = grid.cells[index.x][index.y]
                                if (!curCell.state) {
                                    curCell.newState = true
                                    curCell.color = cell.color
                                    grid.cellsToUpdate.add(curCell)
                                }
                            }
                            grid.cellsToUpdate.add(cell)
                        }
                    }

            }
            if(grid.cellsToUpdate.isEmpty()) x = false


            for (cell in grid.cellsToUpdate) {
                if (cell.newState) {
                    g2d.color = cell.color.toAwtColor()
                    val rect = Rectangle2D.Double(cell.x, cell.y, cell.width, cell.height)
                    g2d.fill(rect)
                    cell.state = cell.newState
                }

            }

            gc.drawImage(SwingFXUtils.toFXImage(bi, null), 0.0, 0.0)
            grid.cellsToUpdate.clear()
        }

    }
}