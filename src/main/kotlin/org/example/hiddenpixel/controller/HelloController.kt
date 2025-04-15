package org.example.hiddenpixel.controller

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.ToggleButton
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.image.WritableImage
import javafx.stage.FileChooser
import org.example.hiddenpixel.HelloApplication
import javax.imageio.ImageIO
import java.io.IOException

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
            val numChannels = updateChannels(alphaToggle.isSelected, redToggle.isSelected, greenToggle.isSelected, blueToggle.isSelected)
            if (numChannels > 0)
                outputTextArea.text = "> Max ${model.height * model.width * numChannels} characters"
        }else{
            outputTextArea.text = "> Something went wrong loading image!"
        }
    }

    @FXML
    fun onEncodeClick(actionEvent: ActionEvent) {
        val img: Image? = imageView.image
        if (img == null) {
            outputTextArea.text = "> No Image!"
            return
        }
        val msg: String? = secretMessageField.text
        if (msg.isNullOrEmpty()) {
            outputTextArea.text = "> No Message!"
            return
        }
        model.setMessage(msg)

        val numChannels = updateChannels(alphaToggle.isSelected, redToggle.isSelected, greenToggle.isSelected, blueToggle.isSelected)
        if (numChannels == 0) {
            outputTextArea.text = "> Select at least one channel!"
            return
        }

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
        val img = imageView.image
        if (img == null) {
            outputTextArea.text = "> No image to save!"
            return
        }

        val fileChooser = FileChooser()
        fileChooser.title = "Save Image"
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("PNG", "*.png"))

        val file = fileChooser.showSaveDialog(HelloApplication.getStage()?.scene?.window)

        if (file != null) {
            try {
                val width = img.width.toInt()
                val height = img.height.toInt()
                val bufferedImage = java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB)

                // Transfer pixels manually
                for (y in 0 until height) {
                    for (x in 0 until width) {
                        bufferedImage.setRGB(x, y, img.pixelReader.getArgb(x, y))
                    }
                }

                ImageIO.write(bufferedImage, "png", file)
                outputTextArea.text = "> Image saved successfully"
            } catch (e: IOException) {
                outputTextArea.text = "> Error saving image: ${e.message}"
            }
        }
    }



    @FXML
    fun onDecodeClick(actionEvent: ActionEvent) {
        val img: Image? = imageView.image
        if (img == null) {
            outputTextArea.text = "> No Image!"
            return
        }

        val numChannels = updateChannels(alphaToggle.isSelected, redToggle.isSelected, greenToggle.isSelected, blueToggle.isSelected)
        if (numChannels == 0) {
            outputTextArea.text = "> Select at least one channel!"
            return
        }

        try {
            val decodedMessage = model.decode(img)
            if (decodedMessage != null) {
                secretMessageField.text = decodedMessage
                outputTextArea.text = "> Message decoded successfully"
            } else {
                outputTextArea.text = "> Could not decode message!"
            }
        } catch (e: Exception) {
            outputTextArea.text = "> Error decoding: ${e.message}"
        }
    }

    fun onSelectChannel(actionEvent: ActionEvent) {
        val numChannels = updateChannels(alphaToggle.isSelected, redToggle.isSelected, greenToggle.isSelected, blueToggle.isSelected)
        if (numChannels > 0 && imageView.image != null)
            outputTextArea.text = "> Max ${model.height * model.width * numChannels} characters"
    }

    private fun updateChannels(a: Boolean, r: Boolean, g: Boolean, b: Boolean) : Int{
        model.setChannels(mapOf(
            'a' to a,
            'r' to r,
            'g' to g,
            'b' to b))
        return model.numChannels
    }


}