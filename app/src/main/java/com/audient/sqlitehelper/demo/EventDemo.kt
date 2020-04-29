package com.audient.libsuper.demo

import javafx.application.Application;
import javafx.geometry.Insets
import javafx.scene.Scene;
import javafx.scene.control.Button
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import javafx.stage.Stage;

fun main() {
    Application.launch(EventDemo::class.java)
}

class EventDemo : Application() {
    override fun start(primaryStage: Stage?) {
        val root = VBox().apply {
            padding = Insets(10.0)

            children.addAll(
                    Button("click me!").apply {
                        styleClass.addAll("lead", "btn-danger")
                        setPrefSize(200.0, 50.0)

                        // 单击事件
                        setOnAction {
                            println("单击")
                        }

                        // 双击、多击、左键、中建、右键等等事件
                        addEventHandler(MouseEvent.MOUSE_CLICKED) { event ->
                            // 左键双击(同时会回调单击)
                            if (event.clickCount == 2 && event.button == MouseButton.PRIMARY) {
                                println("双击")
                            }
                        }

                        // 鼠标进入、移出事件
                        hoverProperty().addListener { observable, oldValue, newValue ->
                            println(if (newValue == true) "鼠标进入了" else "鼠标移出了")
                        }
                    }
            )
        }

        primaryStage?.scene = Scene(root).apply {
            stylesheets.add("org/kordamp/bootstrapfx/bootstrapfx.css")

            // 键盘事件
            setOnKeyReleased { event ->
                println(event.text)
            }

            // 快捷键
            val kc = KeyCodeCombination(KeyCode.W, KeyCodeCombination.ALT_DOWN)
            accelerators.put(kc, Runnable {
                println("按下了快捷键")
            })
        }
        primaryStage?.title = "BootStrapFXDemo"
        primaryStage?.sizeToScene()
        primaryStage?.show()
    }
}