package Model.AutomatonSchemas

import Model.Base.Grid
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class CellGrowTask(var gc: GraphicsContext, var grid: Grid) : Runnable{

    override fun run() {
        val width = gc.canvas.width
        val height = gc.canvas.height

        gc.fill = Color.BLACK
        for(cells in grid.cells){
            for(cell in cells){
                if(cell.state) {
                    gc.fillRect(cell.x, cell.y, cell.width, cell.height)
                    println(cell.width)
                }

            }
        }


    }
}