package com.audient.libsuper.utils

import java.io.File
import javax.swing.filechooser.FileSystemView

object FileUtils {

    // C:\Users\ddy\Desktop
    val desktop: File by lazy { FileSystemView.getFileSystemView().homeDirectory }

    // D:\sync\prosjava\RJMqttHelper
    val userDir: File? by lazy {
        try {
            File(System.getProperty("user.dir"))
        } catch (e: Exception) {
            null
        }
    }

    // C:\Users\ddy
    val userHome: File? by lazy {
        try {
            File(System.getProperty("user.home"))
        } catch (e: Exception) {
            null
        }
    }
}