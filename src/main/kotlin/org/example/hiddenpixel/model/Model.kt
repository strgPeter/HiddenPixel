package org.example.hiddenpixel.model

import javafx.scene.image.Image


class Model {
    private var orig_img : Image? = null

    public fun get_orig_img() = orig_img
    public fun set_orig_img(orig_img : Image) {
        this.orig_img = orig_img
    }
}