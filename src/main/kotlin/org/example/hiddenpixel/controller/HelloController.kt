package org.example.hiddenpixel.controller

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.ToggleButton
import javafx.scene.image.ImageView
import javafx.scene.shape.Rectangle

import org.example.hiddenpixel.model.Model

class HelloController {

    @FXML
    lateinit var imageBackground: Rectangle

    @FXML
    lateinit var secretMessageField: TextArea

    @FXML
    lateinit var loadImageButton: Button

    @FXML
    lateinit var outputTextArea: TextArea

    @FXML
    lateinit var blueToggle: ToggleButton

    @FXML
    lateinit var greenToggle: ToggleButton

    @FXML
    lateinit var redToggle: ToggleButton

    @FXML
    lateinit var encodeButton: Button

    @FXML
    lateinit var decodeButton: Button

    @FXML
    lateinit var saveImageButton: Button

    @FXML
    lateinit var imageView: ImageView

    private val model = Model()
    private val imageLoader = ImageLoader()


    @FXML
    fun onLoadImageClick(actionEvent: ActionEvent) {
        val img = imageLoader.loadImage()
        if (img != null) {
            model.set_orig_img(img)
            imageView.image = img
        }
    }

    @FXML
    fun onSaveImageClick(actionEvent: ActionEvent) {

    }

    @FXML
    fun onDecodeClick(actionEvent: ActionEvent) {

    }

    @FXML
    fun onEncodeClick(actionEvent: ActionEvent) {

    }
}