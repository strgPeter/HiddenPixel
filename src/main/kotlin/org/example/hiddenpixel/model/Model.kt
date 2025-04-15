package org.example.hiddenpixel.model

import javafx.scene.image.Image


class Model {
    private var origImg : Image? = null
    private var message: String? = null
    private var channels: Map<Char, Boolean>? = null

    val width
        get() = origImg?.width?.toInt() ?: 0

    val height
        get() = origImg?.height?.toInt() ?: 0

    val numChannels
        get() = channels?.values?.filter { it }?.size ?: 0

    fun setOrigImg(image : Image) {
        this.origImg = image
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun setChannels(channels: Map<Char, Boolean>) {
        this.channels = channels
    }

    fun encode(): Image?{
        return if (origImg == null || message == null || channels == null) null
        else Encoder.encode(message!!, origImg!!, channels!!)
    }

    fun decode(image: Image): String? {
        return if (image != null && channels != null) {
            Decoder.decode(image, channels!!)
        } else null
    }




}