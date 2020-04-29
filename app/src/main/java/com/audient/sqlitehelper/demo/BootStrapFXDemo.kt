package com.audient.libsuper.demo

import javafx.application.Application;
import javafx.geometry.Insets
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox
import javafx.stage.Stage;

fun main() {
    Application.launch(BootStrapFXDemo::class.java)
}

class BootStrapFXDemo : Application() {
    override fun start(primaryStage: Stage?) {
        val root = VBox().apply {
            padding = Insets(10.0)

            children.add(Button("click me!").apply {
                styleClass.addAll("lead", "btn-danger")
                setPrefSize(200.0, 50.0)
            })
        }

        primaryStage?.scene = Scene(root).apply {
            stylesheets.add("org/kordamp/bootstrapfx/bootstrapfx.css")
        }
        primaryStage?.title = "BootStrapFXDemo"
        primaryStage?.sizeToScene()
        primaryStage?.show()
    }
}