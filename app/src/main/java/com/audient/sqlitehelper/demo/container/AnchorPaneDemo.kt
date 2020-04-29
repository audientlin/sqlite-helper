package com.audient.libsuper.demo.container

import com.audient.libsuper.utils.addAll
import javafx.application.Application;
import javafx.geometry.Insets
import javafx.scene.Scene;
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Paint
import javafx.stage.Stage;

fun main() {
    Application.launch(AnchorPaneDemo::class.java)
}

class AnchorPaneDemo : Application() {
    override fun start(primaryStage: Stage?) {
        val root = AnchorPane().apply {
            prefWidth = 800.0
            prefHeight = 600.0
            padding = Insets(10.0)
            background = Background(BackgroundFill(Paint.valueOf("#3498DB"), CornerRadii(0.0), null))
        }.addAll(
                // 相当于padding，会跟随窗口的拉伸而放大缩小
                Button("button1").apply {
                    AnchorPane.setLeftAnchor(this, 10.0)
                    AnchorPane.setRightAnchor(this, 10.0)
                    AnchorPane.setTopAnchor(this, 10.0)
                    AnchorPane.setBottomAnchor(this, 10.0)
                },

                Button("button2").apply {
                    AnchorPane.setTopAnchor(this, 100.0)
                    AnchorPane.setLeftAnchor(this, 100.0)
                }
        )

        primaryStage?.scene = Scene(root).apply {
            stylesheets.add("org/kordamp/bootstrapfx/bootstrapfx.css")
        }
        primaryStage?.title = "BootStrapFXDemo"
        primaryStage?.sizeToScene()
        primaryStage?.show()
    }
}