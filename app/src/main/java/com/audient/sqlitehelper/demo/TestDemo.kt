package com.audient.libsuper.demo

import com.audient.libsuper.utils.FileUtils
import com.audient.libsuper.utils.addAll
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*
import javafx.stage.Stage;

fun main() {
    println(FileUtils.userHome?.absolutePath ?: "")

//    Application.launch(TestDemo::class.java)
}

class TestDemo : Application() {
    override fun start(primaryStage: Stage?) {
        val root = VBox().apply {
        }.addAll(

        )

        primaryStage?.scene = Scene(root).apply {
            stylesheets.add("org/kordamp/bootstrapfx/bootstrapfx.css")
        }
        primaryStage?.title = "BootStrapFXDemo"
        primaryStage?.sizeToScene()
        primaryStage?.show()
    }
}