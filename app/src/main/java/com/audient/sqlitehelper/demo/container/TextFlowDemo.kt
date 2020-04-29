package com.audient.libsuper.demo.container

import com.audient.libsuper.utils.addAll
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.scene.text.TextFlow
import javafx.stage.Stage;

fun main() {
    Application.launch(TextFlowDemo::class.java)
}

/**
 * 文本流布局，宽度不够的时候，会自动换行，而且会把Text里面的文本拆开换行。
 */
class TextFlowDemo : Application() {
    override fun start(primaryStage: Stage?) {
        val root = TextFlow().apply {
            prefWidth = 800.0
            prefHeight = 600.0

            // 行距
            lineSpacing = 20.0
        }.addAll(
                Text("你好，").apply { font = Font.font(30.0) },
                Text("我是"),
                Text("哈哈哈"),
                Text("大家好"),
                Text("大家好"),
                Text("大家好"),
                Text("什么鬼")
        )

        primaryStage?.scene = Scene(root).apply {
            stylesheets.add("org/kordamp/bootstrapfx/bootstrapfx.css")
        }
        primaryStage?.title = "BootStrapFXDemo"
        primaryStage?.sizeToScene()
        primaryStage?.show()
    }
}