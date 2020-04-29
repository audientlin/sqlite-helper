package com.audient.libsuper.demo.widget

import com.audient.libsuper.utils.addAll
import javafx.application.Application;
import javafx.beans.value.ChangeListener
import javafx.geometry.Insets
import javafx.scene.Scene;
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.control.Tooltip
import javafx.scene.layout.VBox
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.stage.Stage;

fun main() {
    Application.launch(LabelDemo::class.java)
}

class LabelDemo : Application() {
    override fun start(primaryStage: Stage?) {
        val root = VBox().apply {
            padding = Insets(10.0)
        }.addAll(
                Label("我是一本标签").apply {
                    styleClass.addAll("lead")
                    setPrefSize(200.0, 50.0)

                    textFill = Paint.valueOf("#E74C3C")
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