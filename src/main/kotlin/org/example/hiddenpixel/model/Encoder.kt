package org.example.hiddenpixel.model

import javafx.scene.image.Image
import javafx.scene.image.PixelReader
import javafx.scene.image.PixelWriter
import javafx.scene.image.WritableImage

/**
 * Provides functionality to encode secret messages into images using steganography.
 * Uses least significant bit (LSB) technique to hide data in pixel color channels.
 */
class Encoder {
    companion object {
        /**
         * Encodes a message into an image using the provided color channels
         *
         * @param msg The message to encode
         * @param img The source image
         * @param channels Map of channels to use (a, r, g, b) with boolean values
         * @return A new image with the encoded message
         */
        fun encode(msg: String, img: Image, channels: Map<Char, Boolean>): Image {
            val binMsg = msg.map {
                it.code.toString(2).padStart(8, '0')
            }.joinToString("")
            
            var binMsgPtr = 0

            val wdt = img.width.toInt()
            val hdt = img.height.toInt()

            val pxlReader = img.pixelReader
            val writableImage = WritableImage(wdt, hdt)
            val pxlWriter = writableImage.pixelWriter

            for (y in 0 until hdt) {
                for (x in 0 until wdt) {

                    if (y == 0 && x == 0) {
                        encodeMsgLength(msg.length, pxlReader, pxlWriter)
                        continue
                    } else if (y == 0 && x < 8) continue

                    val pxl = pxlReader.getArgb(x, y)

                    if (binMsgPtr < binMsg.length) {
                        // Extract channels:
                        val a = (pxl shr 24) and 0xFF
                        val r = (pxl shr 16) and 0xFF
                        val g = (pxl shr 8) and 0xFF
                        val b = pxl and 0xFF

                        var (newA, newR, newG, newB) = listOf(a, r, g, b)
                        if (channels.getValue('a') && binMsgPtr < binMsg.length) {
                            newA = (a and 0xFE) or ((binMsg[binMsgPtr].digitToInt()) and 1)
                            binMsgPtr++
                        }
                        if (channels.getValue('r') && binMsgPtr < binMsg.length) {
                            newR = (r and 0xFE) or ((binMsg[binMsgPtr].digitToInt()) and 1)
                            binMsgPtr++
                        }
                        if (channels.getValue('g') && binMsgPtr < binMsg.length) {
                            newG = (g and 0xFE) or ((binMsg[binMsgPtr].digitToInt()) and 1)
                            binMsgPtr++
                        }
                        if (channels.getValue('b') && binMsgPtr < binMsg.length) {
                            newB = (b and 0xFE) or ((binMsg[binMsgPtr].digitToInt()) and 1)
                            binMsgPtr++
                        }

                        // Reconstruct modified ARGB value
                        val newPxl = (newA shl 24) or (newR shl 16) or (newG shl 8) or newB
                        pxlWriter.setArgb(x, y, newPxl)
                    } else {
                        pxlWriter.setArgb(x, y, pxl)
                    }
                }
            }
            return writableImage
        }

        /**
         * Encodes the message length in the first row of pixels
         * Stores the length as a binary number in the LSB of the red channel
         *
         * @param length The length of the message to encode
         * @param pr The pixel reader of the source image
         * @param pw The pixel writer of the target image
         */
        private fun encodeMsgLength(length: Int, pr: PixelReader, pw: PixelWriter) {
            val lengthBin = length.toString(2).padStart(8, '0')

            for (i in 0 until 8) {
                val pxl = pr.getArgb(i, 0)

                val a = (pxl shr 24) and 0xFF
                val r = (pxl shr 16) and 0xFF
                val g = (pxl shr 8) and 0xFF
                val b = pxl and 0xFF

                val newR = (r and 0xFE) or ((lengthBin[i].digitToInt()) and 1)
                val newPxl = (a shl 24) or (newR shl 16) or (g shl 8) or b

                pw.setArgb(i, 0, newPxl)
            }
        }
    }
}