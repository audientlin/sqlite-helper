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
    Application.launch(StackPaneDemo::class.java)
}

/**
 * 栈布局，可用于图片预览什么的，增加一个view，删除一个view什么的
 */
class StackPaneDemo : Application() {
    override fun start(primaryStage: Stage?) {
        val root = StackPane().apply {
            prefWidth = 800.0
            prefHeight = 600.0
        }.addAll(
                Button("button1"),
                Button("button2"),
                Button("button3")
        )

        primaryStage?.scene = Scene(root).apply {
            stylesheets.add("org/kordamp/bootstrapfx/bootstrapfx.css")
        }
        primaryStage?.title = "BootStrapFXDemo"
        primaryStage?.sizeToScene()
        primaryStage?.show()
    }
}