package com.audient.libsuper.utils

import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent

object ClipboardUtils {

    /**
     * 添加一个字符串到粘贴板
     */
    fun addString(text: String) {
        Clipboard.getSystemClipboard().setContent(ClipboardContent().apply {
            putString(text)
        })
    }
}