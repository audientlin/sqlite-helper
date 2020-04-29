package com.audient.libsuper.demo.container

import com.audient.libsuper.utils.addAll
import com.audient.libsuper.utils.setBackgroundColor
import javafx.application.Application;
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene;
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Paint
import javafx.stage.Stage;

fun main() {
    Application.launch(BorderPaneDemo::class.java)
}

class BorderPaneDemo : Application() {
    override fun start(primaryStage: Stage?) {
        val root = BorderPane().apply {
            prefWidth = 800.0
            prefHeight = 600.0

            center = AnchorPane().apply {
                setBackgroundColor("#2ECC71")
            }

            top = AnchorPane().apply {
                prefHeight = 100.0
                setBackgroundColor("#3498DB")
            }

            bottom = AnchorPane().apply {
                prefHeight = 100.0
                setBackgroundColor("#9B59B6")
            }

            left = AnchorPane().apply {
                prefWidth = 100.0
                setBackgroundColor("#F39C12")
            }

            right = AnchorPane().apply {
                prefWidth = 100.0
                setBackgroundColor("#E74C3C")
            }
        }

        primaryStage?.scene = Scene(root).apply {
            stylesheets.add("org/kordamp/bootstrapfx/bootstrapfx.css")
        }
        primaryStage?.title = "BootStrapFXDemo"
        primaryStage?.sizeToScene()
        primaryStage?.show()
    }
}