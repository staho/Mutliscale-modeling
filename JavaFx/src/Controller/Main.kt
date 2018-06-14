package Controller

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import java.io.IOException

class MultiScale : Application() {
    companion object {

        @JvmStatic fun main(args: Array<String>) {
            Application.launch(MultiScale::class.java, *args)
        }
    }

    lateinit var primaryStage: Stage

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        this.primaryStage = primaryStage
        primaryStage.title = "Multiscale modeling"
        initMainView()


    }


    private fun initMainView() {
        try {
            val loader = FXMLLoader()
            loader.location = this.javaClass.getResource("../view/MainLayout.fxml")
            val anchorPane: AnchorPane = loader.load()
            val scene = Scene(anchorPane)
            primaryStage.scene = scene
            primaryStage.show()


        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}