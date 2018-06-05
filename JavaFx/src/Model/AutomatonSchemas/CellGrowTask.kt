package Model.AutomatonSchemas

import Model.Base.Grid
import javafx.application.Platform
import javafx.embed.swing.SwingFXUtils
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import java.awt.image.BufferedImage
import kotlin.concurrent.thread

class CellGrowTask(var gc: GraphicsContext, var grid: Grid, var lock: java.lang.Object) : Runnable{
    val width = gc.canvas.width
    val height = gc.canvas.height

    override fun run() {
//        drawGrid()
        updateFromGrid()

    }

    private fun drawGrid() {
        val bi = BufferedImage(width.toInt(), height.toInt(), BufferedImage.TYPE_INT_RGB)
        var g2d = bi.createGraphics()
        g2d.background = java.awt.Color.WHITE
        g2d.fillRect(0, 0, width.toInt(), height.toInt());
        g2d.color = java.awt.Color.BLACK
        var i = 0

        for(cellRow in grid.cells){
            for(cell in cellRow) {
                g2d.drawLine(cell.x.toInt(), cell.y.toInt(), cell.xEnd.toInt(), cell.y.toInt())
                g2d.drawLine(cell.x.toInt(), cell.y.toInt(), cell.x.toInt(), cell.yEnd.toInt())
                i++
                if(i % 1000 == 0){
                    gc.drawImage(SwingFXUtils.toFXImage(bi, null), 0.0, 0.0)
                }
            }
        }

        gc.drawImage(SwingFXUtils.toFXImage(bi, null), 0.0, 0.0)

    }

    private fun updateFromGrid() {
//        while (grid.cellsToUpdate.size == 0) {
//            lock.wait()
//        }
//
            val nb = grid.neighbourhood
            var x = true
            val bi = BufferedImage(width.toInt(), height.toInt(), BufferedImage.TYPE_INT_RGB)
            val g2d = bi.createGraphics()
            g2d.background = java.awt.Color.WHITE
            g2d.fillRect(0, 0, width.toInt(), height.toInt());
            gc.fill = Color.WHITE
            gc.clearRect(0.0,0.0, width, height)



        while(x){
            grid.cells.forEachIndexed { i, cellsRow ->
                    cellsRow.forEachIndexed { j, cell ->
                        if (cell.state) {
                            val indexes = nb.computeIndexes(i, j)
                            for (index in indexes) {
//                        println(index.x)

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
                if(grid.cellsToUpdate.size.equals(0)) x = false



//            gc.fill = Color.BLACK
            for (cell in grid.cellsToUpdate) {
                if (cell.newState) {
                    g2d.color = cell.color.toAwtColor()
                    g2d.fillRect(cell.x.toInt(), cell.y.toInt(), cell.width.toInt(), cell.height.toInt())

                    cell.state = cell.newState
                }

            }

            gc.drawImage(SwingFXUtils.toFXImage(bi, null), 0.0, 0.0)


//            Thread.sleep(100)
            grid.cellsToUpdate.clear()
        }

    }
}