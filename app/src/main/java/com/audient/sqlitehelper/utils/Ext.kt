package com.audient.libsuper.utils

import javafx.event.ActionEvent
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Paint
import javafx.stage.Stage

fun Pane.addAll(vararg elements: Node): Pane {
    children.addAll(elements)
    return this
}

fun Button.setOnClick(onClick: () -> Unit) {
    setOnAction {
        onClick.invoke()
    }
}

fun Node.setOnDoubleClick(onDoubleClick: () -> Unit) {
    addEventHandler(MouseEvent.MOUSE_CLICKED) { event ->
        if (event.clickCount == 2 && event.button == MouseButton.PRIMARY) {
            onDoubleClick.invoke()
        }
    }
}

fun <T> ListView<T>.setOnItemClick(onItemClick: (datum: T) -> Unit) {
    selectionModel.selectedItemProperty().addListener { observable, oldValue, newValue ->
        onItemClick.invoke(newValue)
    }
}

fun TextField.setOnEnter(onEnter: (tf: TextField) -> Unit) {
    setOnKeyReleased {
        if (it.code == KeyCode.ENTER) {
            onEnter.invoke(this)
        }
    }
}

fun TextField.setOnValueChanged(onChanged: (newValue: String) -> Unit) {
    textProperty().addListener { observable, oldValue, newValue ->
        onChanged.invoke(newValue)
    }
}

/**
 * @param colorText 如：#9B59B6
 */
fun Region.setBackgroundColor(colorText: String) {
    background = Background(BackgroundFill(Paint.valueOf(colorText), CornerRadii(0.0), null))
}

fun Stage.closeByActionEvent(actionEvent: ActionEvent) {
    ((actionEvent.source as Parent).scene.window as Stage).close()
}

fun showAlertDialogError(message: String) {
    Alert(Alert.AlertType.ERROR).apply {
        headerText = null
        contentText = message
    }.showAndWait()
}