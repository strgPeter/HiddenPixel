package org.example.hiddenpixel

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class HelloApplication : Application() {

    lateinit var stage: Stage

    override fun start(stage: Stage) {

        instance = this
        this.stage = stage

        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("hello-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 850.0, 500.0)
        scene.stylesheets.add(javaClass.getResource("style.css")?.toExternalForm())
        stage.title = "HiddenPxl"
        stage.isResizable = false
        stage.scene = scene
        stage.show()
    }

    companion object {
        private var instance: HelloApplication? = null

        fun getStage(): Stage? {
            return instance?.stage
        }
    }

}

fun main() {
    Application.launch(HelloApplication::class.java)
}