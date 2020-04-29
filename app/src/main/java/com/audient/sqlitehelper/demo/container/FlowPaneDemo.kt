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
    Application.launch(FlowPaneDemo::class.java)
}

class FlowPaneDemo : Application() {
    override fun start(primaryStage: Stage?) {
        val root = FlowPane().apply {
            prefWidth = 800.0
            prefHeight = 600.0

            // 水平、垂直间距
            hgap = 10.0
            vgap = 10.0

            // 设置方向
//            orientation = Orientation.VERTICAL
        }.addAll(
                // 空间不够，会自动移到下一行
                Button("button"),
                Button("button"),
                Button("button"),
                Button("button"),
                Button("button"),
                Button("button"),
                Button("button"),
                Button("button"),
                Button("button"),
                Button("button"),
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