package com.audient.libsuper.demo.container

import javafx.application.Application;
import javafx.scene.Group
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

fun main() {
    Application.launch(GroupDemo::class.java)
}

/**
 * 只是一个组
 */
class GroupDemo : Application() {
    override fun start(primaryStage: Stage?) {
        val root = Group().apply {
            // 设置透明度
            opacity = 0.5

            children.addAll(
                    Button("按钮1").apply {
                        styleClass.addAll("lead", "btn-danger")
                        setPrefSize(200.0, 50.0)
                        layoutX = 10.0
                        layoutY = 10.0
                    },
                    Button("按钮2").apply {
                        styleClass.addAll("lead", "btn-danger")
                        setPrefSize(200.0, 50.0)
                        layoutX = 220.0
                        layoutY = 10.0
                    }
            )
        }

        // 检测某个点上是否有控件，只能检测左上角
        println(root.contains(0.0, 0.0))// false
        println(root.contains(10.0, 10.0))// true，按钮1的左上角
        println(root.contains(20.0, 20.0))// false，按钮1的内部

        primaryStage?.scene = Scene(root).apply {
            stylesheets.add("org/kordamp/bootstrapfx/bootstrapfx.css")
        }
        primaryStage?.title = "BootStrapFXDemo"
        primaryStage?.sizeToScene()
        primaryStage?.show()
    }
}