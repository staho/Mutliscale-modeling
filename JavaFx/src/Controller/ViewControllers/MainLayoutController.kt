package Controller.ViewControllers

import Model.Base.Grid
import javafx.fxml.FXML
import javafx.scene.control.ComboBox
import javafx.scene.canvas.Canvas

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

    @FXML
    fun initialize() {
        initComboboxes()
    }

    fun initComboboxes() {
        neighbourCombo.items.addAll(neighbourTypes)
    }


}
