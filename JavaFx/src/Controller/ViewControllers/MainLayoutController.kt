package Controller.ViewControllers

import Model.AutomatonSchemas.CellGrowTask
import Model.AutomatonSchemas.ProcessGrowTask
import Model.Base.Color
import Model.Base.Grid
import Model.NeighbourHood.MooreNeighbourhood
import javafx.event.Event
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.ComboBox
import javafx.scene.canvas.Canvas
import javafx.scene.control.Cell
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent

class MainLayoutController {

    private val neighbourTypes: List<String> = listOf("Von Neumann", "Moore", "Pentagonal L", "Pentagonal R", "Hexagonal L", "Hexagonal R")

    @FXML
    private lateinit var neighbourCombo: ComboBox<String>

    @FXML
    private lateinit var randomizationType: ComboBox<String>

    @FXML
    private lateinit var boundaryCondition: ComboBox<String>

    @FXML
    private lateinit var canvas: Canvas

    private lateinit var grid: Grid

    private lateinit var cellGrowTask: CellGrowTask
    private lateinit var processGrowTask: ProcessGrowTask
    val lock: java.lang.Object = Object()

    @FXML
    private lateinit var xTextField: TextField

    @FXML
    private lateinit var yTextField: TextField

    @FXML
    fun initialize() {
//        grid = Grid(1,1,2.0,2.0, MooreNeighbourhood(1 ,1))
        initComboboxes()

    }

    @FXML
    fun handleStartClick() {
        val xNo = xTextField.text.toInt()
        val yNo = yTextField.text.toInt()
        val gc = canvas.graphicsContext2D


        grid = Grid(xNo, yNo, canvas.width, canvas.height, MooreNeighbourhood(xNo, yNo))
        var cell = grid.cells.get(xNo/2).get(yNo/2)
        cell.state = true
        cell.color = Color(1,0,0)

         cell = grid.cells.get(xNo - 1).get(yNo - 1)
        cell.state = true
        cell.color = Color(0,1,0)

         cell = grid.cells.get(3).get(4)
        cell.state = true
        cell.color = Color(0,0,1)

        cellGrowTask = CellGrowTask(gc, grid, lock)
        val thread = Thread(cellGrowTask)

//        processGrowTask = ProcessGrowTask(grid, lock)
//        val thread2 = Thread(processGrowTask)

        thread.start()
//        thread2.start()
    }

    @FXML
    fun handlePauseClick() {
//        cellGrowTask?.cancel()
    }



    fun initComboboxes() {
        neighbourCombo.items.addAll(neighbourTypes)
    }

    @FXML
    fun handleMouseClick(event: MouseEvent) {
        val x = event.x
        val y = event.y



    }


}
