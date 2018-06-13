package Controller.ViewControllers

import Model.AutomatonSchemas.CellGrowTask
import Model.AutomatonSchemas.MonteCarloTask
import Model.Base.Color
import Model.Base.Grid
import Model.Base.McPreferences
import Model.Interfaces.NeighbourHoodInterface
import Model.NeighbourHood.MooreNeighbourhood
import Model.NeighbourHood.PentagonalNeighbourhood
import Model.NeighbourHood.VonNeumann
import javafx.fxml.FXML
import javafx.scene.control.ComboBox
import javafx.scene.canvas.Canvas
import javafx.scene.control.CheckBox
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import java.util.*

class MainLayoutController {

    private val neighbourTypes: List<String> = listOf("Moore", "Von Neumann",  "Pentagonal L", "Pentagonal R", "Hexagonal L", "Hexagonal R")
    private val idChooseTypes: List<String> = listOf("Neighbour", "Grid")

    @FXML
    private lateinit var neighbourCombo: ComboBox<String>

    @FXML
    private lateinit var randomizationType: ComboBox<String>

    @FXML
    private lateinit var canvas: Canvas

    private lateinit var grid: Grid

    private lateinit var cellGrowTask: CellGrowTask
    private lateinit var monteCarloTask: MonteCarloTask
    private lateinit var neighbourhood: NeighbourHoodInterface
    private var bcs: Boolean = true
    private var mcPreferences: McPreferences = McPreferences()

    @FXML
    private lateinit var idChooseComboBox: ComboBox<String>

    @FXML
    private lateinit var xTextField: TextField

    @FXML
    private lateinit var bcsCheckBox: CheckBox

    @FXML
    private lateinit var oneTryCheckBox: CheckBox

    @FXML
    private lateinit var yTextField: TextField

    @FXML
    private lateinit var randomizeField: TextField

    @FXML
    fun initialize() {
        initComboboxes()

        bcsCheckBox.isSelected = true
        oneTryCheckBox.isSelected = mcPreferences.oneTry

        val gc = canvas.graphicsContext2D
        gc.clearRect(0.0,0.0, canvas.width, canvas.height)
        gc.fill = javafx.scene.paint.Color.WHITE
        gc.fillRect(0.0,0.0, canvas.width, canvas.height)

        val xNo = xTextField.text.toInt()
        val yNo = yTextField.text.toInt()

        neighbourhood = MooreNeighbourhood(xNo, yNo, bcs)

        grid = Grid(xNo, yNo, canvas.width, canvas.height, neighbourhood)

    }

    @FXML
    fun handleStartClick() {
        val height = canvas.height
        val width = canvas.width

        val gc = canvas.graphicsContext2D
        gc.clearRect(0.0,0.0, width, height)

        cellGrowTask = CellGrowTask(gc, grid)
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

        idChooseComboBox.items.addAll(idChooseTypes)
        idChooseComboBox.selectionModel.select(1)
    }

    @FXML
    fun handleClearClick(){
        val xNo = xTextField.text.toInt()
        val yNo = yTextField.text.toInt()
        val gc = canvas.graphicsContext2D
        gc.clearRect(0.0,0.0, canvas.width, canvas.height)
        handleNbChoose()

        grid = Grid(xNo, yNo, canvas.width, canvas.height, neighbourhood)
    }

    @FXML
    fun handleMonteCarloClick() {

        monteCarloTask = MonteCarloTask(canvas.graphicsContext2D, grid, mcPreferences)
        val thread = Thread(monteCarloTask)

        thread.start()
    }

    @FXML
    fun handleStop() {
        monteCarloTask.running = false
        cellGrowTask.running = false
    }

    @FXML
    fun handleMouseClick(event: MouseEvent) {
        val x = event.x
        val y = event.y

        val random = Random()

        var cell = grid.findCell(x, y)
        val color = Color(random.nextInt(255),random.nextInt(255),random.nextInt(255))

        grid.addCell(cell, color)
        println(cell.ID)

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
            handleNbChoose()
            grid = Grid(xNo, yNo, canvas.width, canvas.height, neighbourhood)
            canvas.graphicsContext2D.clearRect(0.0, 0.0, canvas.width, canvas.height)

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
                val i = rand.nextInt(grid.nXCells)
                val j = rand.nextInt(grid.nYCells)
                val color = Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255))
                val cell = grid.addCell(i, j, color)
                drawRectangle(cell)
            }
        } catch (e: Exception){

        }
    }

    @FXML
    fun handleNbChoose() {
        try{
            val xNo = xTextField.text.toInt()
            val yNo = yTextField.text.toInt()
            val index = neighbourCombo.selectionModel.selectedIndex
            when(index) {
                0 -> {
                    grid.neighbourhood = MooreNeighbourhood(xNo, yNo, bcs)
                }
                1 -> {
                    grid.neighbourhood = VonNeumann(xNo, yNo, bcs)
                }
                2->{
                    grid.neighbourhood = PentagonalNeighbourhood(xNo, yNo, bcs, true)
                }
                3->{
                    grid.neighbourhood = PentagonalNeighbourhood(xNo, yNo, bcs, false)
                }
            }
            neighbourhood = grid.neighbourhood

            println(neighbourTypes[index])
        } catch (e: Exception){

        }

    }

    @FXML
    fun handleBcsChange(){
        bcs = bcsCheckBox.isSelected
        handleNbChoose()
    }

    @FXML
    fun handleIdSelectionChange(){
        val index = idChooseComboBox.selectionModel.selectedIndex
        when(index) {
            0 -> mcPreferences.chooseMaxNb = true
            1 -> mcPreferences.chooseMaxNb = false
        }
    }

    @FXML
    fun handleOneTryChange(){
        mcPreferences.oneTry = oneTryCheckBox.isSelected
    }

    @FXML
    fun mcHammerStart() {
        try {
            var randNo = randomizeField.text.toInt()
            monteCarloTask = MonteCarloTask(canvas.graphicsContext2D, grid, mcPreferences)
            monteCarloTask.randomizeGrid(randNo)
            val thread = Thread(monteCarloTask)

            thread.start()

        } catch (e: Exception){}


    }



}
