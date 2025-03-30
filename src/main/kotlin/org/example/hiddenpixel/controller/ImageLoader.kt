package org.example.hiddenpixel.controller

import javafx.stage.FileChooser
import org.example.hiddenpixel.HelloApplication
import java.io.File
import java.nio.file.Paths
import java.util.*


class ImageLoader {

    private val fc = FileChooser()

    fun loadImage(): javafx.scene.image.Image? {
        fc.selectedExtensionFilter = FileChooser.ExtensionFilter("PNG", "*.png")

        val picDir = Paths.get(getPicDir()).toFile()
        fc.initialDirectory = if (picDir.exists() && picDir.isDirectory){
            picDir
        }else{
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

    private fun getPicDir(): String {
        val userHome = System.getProperty("user.home")

        return if (System.getProperty("os.name").lowercase(Locale.getDefault()).contains("win")) {
                Paths.get(userHome, "Pictures").toString()
            } else if (System.getProperty("os.name").lowercase(Locale.getDefault()).contains("mac")) {
                Paths.get(userHome, "Pictures").toString()
            } else {
                Paths.get(userHome, "Pictures").toString()
            }

    }
}