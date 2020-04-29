package com.audient.libsuper.base

import javafx.application.Application
import javafx.stage.Stage

lateinit var app: Application

open class BaseApplication : Application() {
    override fun start(primaryStage: Stage) {
        app = this
    }
}