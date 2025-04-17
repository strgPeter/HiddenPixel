package org.example.hiddenpixel.controller

import javafx.stage.FileChooser
import org.example.hiddenpixel.HelloApplication
import java.io.File
import java.nio.file.Paths
import java.util.*

/**
 * Handles loading image files through a file dialog.
 * Provides functionality to load PNG images from the user's file system.
 */
class ImageLoader {

    private val fc = FileChooser()

    /**
     * Opens a file dialog to let the user select a PNG image.
     * Dialog initially opens in the user's Pictures directory.
     *
     * @return The loaded Image object or null if no image was selected or an error occurred
     */
    fun loadImage(): javafx.scene.image.Image? {
        fc.selectedExtensionFilter = FileChooser.ExtensionFilter("PNG", "*.png")

        val picDir = Paths.get(getPicDir()).toFile()
        fc.initialDirectory = if (picDir.exists() && picDir.isDirectory) {
            picDir
        } else {
            File(System.getProperty("user.home"))
        }

        val file: File? = fc.showOpenDialog(HelloApplication.getStage()?.scene?.window)

        return file?.let {
            try {
                javafx.scene.image.Image(it.toURI().toString())
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    /**
     * Determines the path to the user's Pictures directory based on the operating system.
     *
     * @return String path to the Pictures directory
     */
    private fun getPicDir(): String {
        val userHome = System.getProperty("user.home")
        // All OS paths are the same, simplified this logic
        return Paths.get(userHome, "Pictures").toString()
    }
}