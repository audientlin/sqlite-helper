package com.audient.sqlitehelper.widget

import com.audient.libsuper.utils.setBackgroundColor
import javafx.scene.control.TextField

class CopyableLabel(text: String = "") : TextField(text) {
    init {
        isEditable = false
        setBackgroundColor("#00000000")
    }
}