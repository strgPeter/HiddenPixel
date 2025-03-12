package org.example.hiddenpixel

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.ToggleButton
import javafx.scene.image.ImageView

class HelloController {
    lateinit var imageView: ImageView

    @FXML
    private lateinit var welcomeText: Label

    @FXML
    private fun onHelloButtonClick() {
        welcomeText.text = "Welcome to JavaFX Application!"
    }

    fun onLoadImageClick(actionEvent: ActionEvent) {

    }

    fun onSaveImageClick(actionEvent: ActionEvent) {

    }

    fun onDecodeClick(actionEvent: ActionEvent) {

    }

    fun onEncodeClick(actionEvent: ActionEvent) {

    }
}