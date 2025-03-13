package org.example.hiddenpixel.model

import javafx.scene.image.Image
import javafx.scene.image.WritableImage


class Model {
    private var origImg : Image? = null
    private var message: String? = null

    val width
        get() = origImg?.width?.toInt() ?: 0

    val height
        get() = origImg?.height?.toInt() ?: 0

    fun getOrigImg() = origImg
    fun setOrigImg(image : Image) {
        this.origImg = image
    }

    fun getMessage() = message
    fun setMessage(message: String) {
        this.message = message
    }

    fun encode(): Image?{
        val msg = message ?: return null
        val img = origImg ?: return null

        val binMsg = msg.map {
            it.code.toString(2).padStart(8, '0') }.joinToString("")
        var binMsgPtr = 0

        val wdt = img.width.toInt()
        val hdt = img.height.toInt()

        val pxlReader = img.pixelReader

        val writableImage = WritableImage(wdt, hdt)
        val pxlWriter = writableImage.pixelWriter

        for (y in 0 until hdt) {
            for (x in 0 until wdt) {
                val pxl = pxlReader.getArgb(x,y)

                if (binMsgPtr < binMsg.length){
                    // Extract channels:
                    val a = (pxl shr 24) and 0xFF
                    val r = (pxl shr 16) and 0xFF
                    val g = (pxl shr 8) and 0xFF
                    val b = pxl and 0xFF

                    // Modify LSB:
                    val newA = (a and 0xFE) or ((binMsg[binMsgPtr++].digitToInt()) and 1)
                    val newR = (r and 0xFE) or ((binMsg[binMsgPtr++].digitToInt()) and 1)
                    val newG = (g and 0xFE) or ((binMsg[binMsgPtr++].digitToInt()) and 1)
                    val newB = (b and 0xFE) or ((binMsg[binMsgPtr++].digitToInt()) and 1)

                    // Reconstruct modified ARGB value
                    val newPxl = (newA shl 24) or (newR shl 16) or (newG shl 8) or newB

                    pxlWriter.setArgb(x, y, newPxl)
                }else{
                    pxlWriter.setArgb(x, y, pxl)
                }

            }
        }

        return writableImage
    }
}