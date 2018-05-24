package Controller

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.SplitPane
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import java.io.IOException

class MultiScale : Application() {
    companion object {

        @JvmStatic fun main(args: Array<String>) {
            Application.launch(MultiScale::class.java, *args)
        }
    }

    lateinit var primaryStage: Stage
    lateinit var rootLayout: BorderPane
    lateinit var mainView: SplitPane

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        this.primaryStage = primaryStage
        primaryStage.title = "Multiscale modeling"
//        primaryStage.icons.add(Image("https://cdn0.iconfinder.com/data/icons/personal-and-business-finance/64/1-27-512.png"))

        val resource = this.javaClass.getResource("../view/RootLayout.fxml")
        println(resource.toString())

        initRootLayout()
        initMainView()


    }

    private fun initRootLayout() {
        try {
            val loader = FXMLLoader()
            loader.location = this.javaClass.getResource("../View/RootLayout.fxml")

            rootLayout = loader.load<BorderPane>()

            val scene = Scene(rootLayout)
            primaryStage.scene = scene
            primaryStage.show()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun initMainView() {
        try {
            val loader = FXMLLoader()
            loader.location = this.javaClass.getResource("../view/MainLayout.fxml")
            var anchorPane: AnchorPane = loader.load()
            rootLayout.center = anchorPane



        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}