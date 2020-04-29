package com.audient.sqlitehelper

import com.audient.libsuper.base.BaseApplication
import com.audient.sqlitehelper.page.InitStageWrapper
import javafx.application.Application
import javafx.stage.Stage

fun main() {
    Application.launch(App::class.java)
}

@Suppress("SpellCheckingInspection")
class App : BaseApplication() {

    override fun start(primaryStage: Stage) {
        super.start(primaryStage)

        InitStageWrapper(primaryStage).show()
    }
}