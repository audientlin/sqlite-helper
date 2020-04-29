package com.audient.libsuper.base

import javafx.scene.Scene
import javafx.stage.Stage

abstract class BaseActivity<T : BaseView>(val stage: Stage = Stage()) {

    private var viewNullable: T? = null
    val view: T get() = viewNullable!!

    abstract fun getBaseView(): T

    fun start() {
        viewNullable = getBaseView()

        stage.scene = Scene(view.getRootView()).apply {
            stylesheets.add("org/kordamp/bootstrapfx/bootstrapfx.css")
        }
        stage.sizeToScene()
        stage.show()

        initSomething()
        initListeners()
    }

    open fun initSomething() {}
    open fun initListeners() {}
}