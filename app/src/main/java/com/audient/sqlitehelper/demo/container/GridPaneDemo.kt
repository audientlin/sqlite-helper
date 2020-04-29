package com.audient.libsuper.demo.container

import com.audient.libsuper.utils.addAll
import com.audient.libsuper.utils.setBackgroundColor
import javafx.application.Application;
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.Scene;
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Paint
import javafx.stage.Stage;

fun main() {
    Application.launch(GridPaneDemo::class.java)
}

class GridPaneDemo : Application() {
    override fun start(primaryStage: Stage?) {
        val root = GridPane().apply {
            prefWidth = 800.0
            prefHeight = 600.0

            add(Button("button"), 0, 0)
            add(Button("button"), 1, 0)
            add(Button("button"), 2, 0)
            add(Button("button"), 0, 1)
            add(Button("button"), 1, 1)
            add(Button("button"), 2, 1)

            hgap = 10.0
            vgap = 10.0
        }

        primaryStage?.scene = Scene(root).apply {
            stylesheets.add("org/kordamp/bootstrapfx/bootstrapfx.css")
        }
        primaryStage?.title = "BootStrapFXDemo"
        primaryStage?.sizeToScene()
        primaryStage?.show()
    }
}