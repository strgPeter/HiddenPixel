package org.example.hiddenpixel.model

import javafx.scene.image.Image
import javafx.scene.image.PixelReader

class Decoder {
    companion object {
        fun decode(img: Image, channels: Map<Char, Boolean>): String {
            val pxlReader = img.pixelReader

            val wdt = img.width.toInt()
            val hdt = img.height.toInt()

            val messageLength = decodeMsgLength(pxlReader)
            println(messageLength)

            val message_sb = StringBuilder()

            var counter =  Math.ceil((messageLength*8).toDouble() / channels.filter { it.value }.size).toInt()

            for (y in 0 until hdt) {
                for (x in 0 until wdt) {

                    // Skip the first 8 pixels that contain the message length
                    if (y == 0 && x < 8) continue

                    val pxl = pxlReader.getArgb(x,y)

                    if (channels.getValue('a')){
                        val a = (pxl shr 24) and 0xFF
                        val lsb = a and 1
                        message_sb.append(lsb)
                        counter--
                    }
                    if (channels.getValue('r')){
                        val r = (pxl shr 16) and 0xFF
                        val lsb = r and 1
                        message_sb.append(lsb)
                        counter--
                    }
                    if (channels.getValue('g')){
                        val g = (pxl shr 8) and 0xFF
                        val lsb = g and 1
                        message_sb.append(lsb)
                        counter--
                    }
                    if (channels.getValue('b')){
                        val b = pxl and 0xFF
                        val lsb = b and 1
                        message_sb.append(lsb)
                        counter--
                    }

                    if (counter <= 0) {
                        val binaryMessage = message_sb.toString()
                        return binaryToString(binaryMessage)
                    }
                }
            }
            //TODO : fix this return
            return ""

        }

        private fun decodeMsgLength(pr: PixelReader): Int {
            val sb = StringBuilder()

            for (i in 0 until 8) {
                val pxl = pr.getArgb(i, 0)

                val r = (pxl shr 16) and 0xFF

                val lsb = r and 1

                sb.append(lsb)
            }
            return sb.toString().toInt(2)
        }

        private fun binaryToString(binary: String): String {
            return binary.chunked(8)
                .map { chunk ->
                    chunk.toInt(2).toChar()
                }
                .joinToString("")
        }
    }
}