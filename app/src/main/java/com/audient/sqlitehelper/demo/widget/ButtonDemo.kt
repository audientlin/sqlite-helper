package com.audient.libsuper.demo.widget

import javafx.application.Application;
import javafx.geometry.Insets
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.stage.Stage;

fun main() {
    Application.launch(ButtonDemo::class.java)
}

class ButtonDemo : Application() {
    override fun start(primaryStage: Stage?) {
        val root = VBox().apply {
            padding = Insets(10.0)

            children.addAll(
                    Button("click me!").apply {
                        //                        styleClass.addAll("lead", "btn-danger")
                        setPrefSize(300.0, 100.0)

                        // 文字大小，不起作用
                        font = Font.font(20.0)
                        // 文字颜色
                        textFill = Paint.valueOf("#8E44AD")

                        // 设置背景，insets相当于padding
                        background = Background(BackgroundFill(Paint.valueOf("#3498DB"), CornerRadii(5.0), Insets(10.0)))
                        // 设置边框
                        border = Border(BorderStroke(Paint.valueOf("#E74C3C"), BorderStrokeStyle.DASHED, CornerRadii(5.0), BorderWidths(2.0)))

                        // 单击
                        setOnAction {
                            println("单击")
                        }

                        // 双击、多击、左键、中建、右键等等
                        addEventHandler(MouseEvent.MOUSE_CLICKED) { event ->
                            // 左键双击(同时会回调单击)
                            if (event.clickCount == 2 && event.button == MouseButton.PRIMARY) {
                                println("双击")
                            }
                        }
                    }
            )
        }

        primaryStage?.scene = Scene(root).apply {
            stylesheets.add("org/kordamp/bootstrapfx/bootstrapfx.css")
        }
        primaryStage?.title = "BootStrapFXDemo"
        primaryStage?.sizeToScene()
        primaryStage?.show()
    }
}