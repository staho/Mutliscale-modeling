package Controller.ViewControllers

import Model.AutomatonSchemas.CellGrowTask
import Model.AutomatonSchemas.ProcessGrowTask
import Model.Base.Color
import Model.Base.Grid
import Model.NeighbourHood.MooreNeighbourhood
import Model.NeighbourHood.VonNeumann
import javafx.embed.swing.SwingFXUtils
import javafx.event.Event
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.ComboBox
import javafx.scene.canvas.Canvas
import javafx.scene.control.Cell
import javafx.scene.control.TextField
import javafx.scene.input.InputEvent
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import java.awt.geom.Rectangle2D
import java.awt.image.BufferedImage
import java.util.*

class MainLayoutController {

    private val neighbourTypes: List<String> = listOf("Moore", "Von Neumann",  "Pentagonal L", "Pentagonal R", "Hexagonal L", "Hexagonal R")

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
    private lateinit var randomizeField: TextField

    @FXML
    fun initialize() {
        initComboboxes()

        val gc = canvas.graphicsContext2D
        gc.clearRect(0.0,0.0, canvas.width, canvas.height)
        gc.fill = javafx.scene.paint.Color.WHITE
        gc.fillRect(0.0,0.0, canvas.width, canvas.height)

        val xNo = xTextField.text.toInt()
        val yNo = yTextField.text.toInt()

        grid = Grid(xNo, yNo, canvas.width, canvas.height, MooreNeighbourhood(xNo, yNo))

    }

    @FXML
    fun handleStartClick() {
        val height = canvas.height
        val width = canvas.width

        val gc = canvas.graphicsContext2D
        gc.clearRect(0.0,0.0, width, height)

        cellGrowTask = CellGrowTask(gc, grid, lock)
        val thread = Thread(cellGrowTask)

        thread.start()

    }

    @FXML
    fun handlePauseClick() {
//        cellGrowTask?.cancel()
    }



    fun initComboboxes() {
        neighbourCombo.items.addAll(neighbourTypes)
        neighbourCombo.selectionModel.selectFirst()
    }

    @FXML
    fun handleClearClick(){
        val xNo = xTextField.text.toInt()
        val yNo = yTextField.text.toInt()
        val gc = canvas.graphicsContext2D
        gc.clearRect(0.0,0.0, canvas.width, canvas.height)

        grid = Grid(xNo, yNo, canvas.width, canvas.height, MooreNeighbourhood(xNo, yNo))
    }

    @FXML
    fun handleMouseClick(event: MouseEvent) {
        val x = event.x
        val y = event.y

        val random = Random()

        var cell = grid.findCell(x, y)
        cell.state = true
        cell.color = Color(random.nextInt(255),random.nextInt(255),random.nextInt(255))

        drawRectangle(cell)

    }

    fun drawRectangle(cell: Model.Base.Cell) {
        val gc = canvas.graphicsContext2D

        gc.fill = cell.color.toFxColor()
        gc.fillRect(cell.x, cell.y, cell.width, cell.height)

    }

    @FXML
    fun handleNoChange(){
        try {
            val xNo = xTextField.text.toInt()
            val yNo = yTextField.text.toInt()
            grid = Grid(xNo, yNo, canvas.width, canvas.height, MooreNeighbourhood(xNo, yNo))

        } catch (except: Exception) {
        }

    }

    @FXML
    fun handleRandomEnter(event: KeyEvent) {
        if(event.code == KeyCode.ENTER) {
            randomizer()
        }
    }

    @FXML
    fun handleRandomButton() {
        randomizer()
    }

    private fun randomizer() {
        try {
            val randomNo = randomizeField.text.toInt()
            val rand = Random()

            for (i in 0 until randomNo) {
                val cell = grid.cells[rand.nextInt(grid.nXCells)][rand.nextInt(grid.nYCells)]
                cell.state = true
                cell.color = Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255))
                drawRectangle(cell)
            }
        } catch (e: Exception){

        }
    }

    @FXML
    fun handleNbChoose() {
        val xNo = xTextField.text.toInt()
        val yNo = yTextField.text.toInt()
        val index = neighbourCombo.selectionModel.selectedIndex
        when(index) {
            0 ->  grid.neighbourhood = MooreNeighbourhood(xNo, yNo)
            1 -> grid.neighbourhood = VonNeumann(xNo, yNo)
        }
    }


}
