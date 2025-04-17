package org.example.hiddenpixel.model

import javafx.scene.image.Image
import javafx.scene.image.PixelReader

/**
 * Provides functionality to decode hidden messages from steganographic images.
 * Extracts data from the least significant bits of pixel color channels.
 */
class Decoder {
    companion object {
        /**
         * Decodes a hidden message from an image using the specified channels
         *
         * @param img The image containing the hidden message
         * @param channels Map of channels to use (a, r, g, b) with boolean values
         * @return The decoded message as a string
         */
        fun decode(img: Image, channels: Map<Char, Boolean>): String {
            val pxlReader = img.pixelReader
            val wdt = img.width.toInt()
            val hdt = img.height.toInt()

            val messageLength = decodeMsgLength(pxlReader)
            val totalBits = messageLength * 8
            val message_sb = StringBuilder()
            var counter = totalBits

            for (y in 0 until hdt) {
                for (x in 0 until wdt) {
                    if (y == 0 && x < 8) continue // Skip length pixels
                    if (counter <= 0) break

                    val pxl = pxlReader.getArgb(x, y)

                    if (channels.getValue('a') && counter > 0) {
                        val a = (pxl shr 24) and 0xFF
                        val lsb = a and 1
                        message_sb.append(lsb)
                        counter--
                    }
                    if (channels.getValue('r') && counter > 0) {
                        val r = (pxl shr 16) and 0xFF
                        val lsb = r and 1
                        message_sb.append(lsb)
                        counter--
                    }
                    if (channels.getValue('g') && counter > 0) {
                        val g = (pxl shr 8) and 0xFF
                        val lsb = g and 1
                        message_sb.append(lsb)
                        counter--
                    }
                    if (channels.getValue('b') && counter > 0) {
                        val b = pxl and 0xFF
                        val lsb = b and 1
                        message_sb.append(lsb)
                        counter--
                    }
                }
                if (counter <= 0) break
            }

            return if (message_sb.length >= totalBits) {
                val binaryMessage = message_sb.substring(0, totalBits)
                binaryToString(binaryMessage)
            } else {
                ""
            }
        }

        /**
         * Extracts the message length from the first 8 pixels of the image
         *
         * @param pr The pixel reader of the image
         * @return The length of the encoded message
         */
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

        /**
         * Converts a binary string to a regular string
         * Groups binary data into 8-bit chunks and converts each to a character
         *
         * @param binary The binary string to convert
         * @return The resulting text string
         */
        private fun binaryToString(binary: String): String {
            return binary.chunked(8)
                .map { chunk ->
                    chunk.toInt(2).toChar()
                }
                .joinToString("")
        }
    }
}