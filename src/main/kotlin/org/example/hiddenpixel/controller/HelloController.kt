package org.example.hiddenpixel.controller

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.ToggleButton
import javafx.scene.image.Image
import javafx.scene.image.ImageView

import org.example.hiddenpixel.model.Model

class HelloController {



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
    lateinit var alphaToggle: ToggleButton

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
        outputTextArea.clear()
        val img = imageLoader.loadImage()
        if (img != null) {
            model.setOrigImg(img)
            imageView.image = img
            outputTextArea.clear()
            outputTextArea.text = "> Max ${model.height * model.width * 4} characters"
        }else{
            outputTextArea.text = "> Something went wrong loading image!"
        }
    }

    @FXML
    fun onEncodeClick(actionEvent: ActionEvent) {
        val msg: String? = secretMessageField.text

        if (msg.isNullOrEmpty()) {
            outputTextArea.text = "> No Message!"
            return
        }

        model.setMessage(msg)

        val newImg: Image? = model.encode()

        if (newImg != null) {
            imageView.image = newImg
            outputTextArea.clear()
            outputTextArea.text = "> Encoded image successfully"
        }else{
            outputTextArea.text = "> Something went wrong while encoding!"
        }

    }

    @FXML
    fun onSaveImageClick(actionEvent: ActionEvent) {

    }



    @FXML
    fun onDecodeClick(actionEvent: ActionEvent) {

    }


}