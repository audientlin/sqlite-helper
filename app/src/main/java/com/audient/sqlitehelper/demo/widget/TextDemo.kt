package com.audient.libsuper.demo.widget

import com.audient.libsuper.utils.addAll
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage;

fun main() {
    Application.launch(TextDemo::class.java)
}

/**
 * 文本流布局，宽度不够的时候，会自动换行，而且会把Text里面的文本拆开换行。
 */
class TextDemo : Application() {
    override fun start(primaryStage: Stage?) {
        val root = FlowPane().apply {
            prefWidth = 800.0
            prefHeight = 600.0
        }.addAll(
                Text("你好，").apply {
                    font = Font.font(30.0)
                    fill = Paint.valueOf("#E67E22")
                },
                Text("哈哈哈，").apply {
                    font = Font.font(30.0)
                    fill = Paint.valueOf("#E67E22")
                    isUnderline = true
                },
                Text("你是什么东西哈\n什么鬼，").apply {
                    font = Font.font(30.0)
                    fill = Paint.valueOf("#E67E22")
                    lineSpacing = 10.0
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