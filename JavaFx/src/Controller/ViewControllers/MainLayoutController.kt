package Controller.ViewControllers

import Model.AutomatonSchemas.CellGrowTask
import Model.Base.Grid
import Model.NeighbourHood.MooreNeighbourhood
import javafx.fxml.FXML
import javafx.scene.control.ComboBox
import javafx.scene.canvas.Canvas
import javafx.scene.control.TextField

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

    @FXML
    private lateinit var xTextField: TextField

    @FXML
    private lateinit var yTextField: TextField

    @FXML
    fun initialize() {
        grid = Grid(1,1,2.0,2.0, MooreNeighbourhood())
        initComboboxes()
    }

    @FXML
    fun handleStartClick() {
        val xNo = xTextField.text.toInt()
        val yNo = yTextField.text.toInt()
        var gc = canvas.graphicsContext2D


        grid = Grid(xNo, yNo, canvas.width, canvas.height, MooreNeighbourhood())

        cellGrowTask = CellGrowTask(gc, grid)
        val thread = Thread(cellGrowTask)

        thread.start()
    }



    fun initComboboxes() {
        neighbourCombo.items.addAll(neighbourTypes)
    }


}
