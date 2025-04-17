package org.example.hiddenpixel.model

import javafx.scene.image.Image

/**
 * Main data model for the application.
 * Handles the original image, secret message, and channel selection.
 * Provides encoding and decoding functionality.
 */
class Model {
    private var origImg: Image? = null
    private var message: String? = null
    private var channels: Map<Char, Boolean>? = null

    /**
     * Width of the current image in pixels
     */
    val width
        get() = origImg?.width?.toInt() ?: 0

    /**
     * Height of the current image in pixels
     */
    val height
        get() = origImg?.height?.toInt() ?: 0

    /**
     * Number of currently selected color channels
     */
    val numChannels
        get() = channels?.values?.filter { it }?.size ?: 0

    /**
     * Sets the original image to be used for encoding
     *
     * @param image The source image
     */
    fun setOrigImg(image: Image) {
        this.origImg = image
    }

    /**
     * Sets the message to be encoded into the image
     *
     * @param message The secret message to encode
     */
    fun setMessage(message: String) {
        this.message = message
    }

    /**
     * Sets which color channels to use for encoding/decoding
     *
     * @param channels Map of channel characters ('r','g','b','a') to boolean values
     */
    fun setChannels(channels: Map<Char, Boolean>) {
        this.channels = channels
    }

    /**
     * Encodes the message into the original image
     *
     * @return The encoded image, or null if any required data is missing
     */
    fun encode(): Image? {
        return if (origImg == null || message == null || channels == null) null
        else Encoder.encode(message!!, origImg!!, channels!!)
    }

    /**
     * Decodes a message from an image
     *
     * @param image The image containing the hidden message
     * @return The decoded message, or null if decoding failed
     */
    fun decode(image: Image): String? {
        return if (image != null && channels != null) {
            Decoder.decode(image, channels!!)
        } else null
    }
}