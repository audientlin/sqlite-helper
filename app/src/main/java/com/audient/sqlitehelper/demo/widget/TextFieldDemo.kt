package com.audient.libsuper.demo.widget

import com.audient.libsuper.utils.addAll
import javafx.application.Application;
import javafx.beans.value.ChangeListener
import javafx.geometry.Insets
import javafx.scene.Scene;
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.control.Tooltip
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.stage.Stage;

fun main() {
    Application.launch(TextFieldDemo::class.java)
}

class TextFieldDemo : Application() {
    override fun start(primaryStage: Stage?) {
        val root = VBox().apply {
            padding = Insets(10.0)
        }.addAll(
                TextField().apply {
                    styleClass.addAll("lead")
                    setPrefSize(200.0, 50.0)

                    // 设置文字大小无效，不知道为什么，用Text可以替代。

                    // hint
                    promptText = "请输入用户名"
                    isFocusTraversable = false

                    // 鼠标放上去会提示
                    tooltip = Tooltip("这是Tooltip提示").apply {
                        font = Font.font(40.0)
                    }

                    // 监听文本输入
                    textProperty().addListener { observable, oldValue, newValue ->
                        println("oldValue=$oldValue newValue=$newValue")
                    }

                    // 监听文本选择
                    selectedTextProperty().addListener { observable, oldValue, newValue ->
                        println("oldValue=$oldValue newValue=$newValue")
                    }
                },

                // 密码框
                PasswordField().apply {
                    styleClass.addAll("lead")
                    setPrefSize(200.0, 50.0)
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