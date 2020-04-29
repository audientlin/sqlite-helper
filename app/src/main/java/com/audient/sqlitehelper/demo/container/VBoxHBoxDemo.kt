package com.audient.libsuper.demo.container

import com.audient.libsuper.utils.addAll
import javafx.application.Application;
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene;
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Paint
import javafx.stage.Stage;

fun main() {
    Application.launch(VBoxHBoxDemo::class.java)
}

class VBoxHBoxDemo : Application() {
    override fun start(primaryStage: Stage?) {
        val root = VBox().apply {
            prefWidth = 800.0
            prefHeight = 600.0
            padding = Insets(10.0)
            background = Background(BackgroundFill(Paint.valueOf("#3498DB"), CornerRadii(0.0), null))

            // 设置子view的间隔
            spacing = 10.0

            // 居中
//            alignment = Pos.CENTER
            // 右边居中
//            alignment = Pos.CENTER_RIGHT
        }.addAll(
                Button("button").apply {
                    // 设置margin
                    VBox.setMargin(this, Insets(10.0))
                },
                Button("button"),
                Button("button")
        )

        primaryStage?.scene = Scene(root).apply {
            stylesheets.add("org/kordamp/bootstrapfx/bootstrapfx.css")
        }
        primaryStage?.title = "BootStrapFXDemo"
        primaryStage?.sizeToScene()
        primaryStage?.show()
    }
}