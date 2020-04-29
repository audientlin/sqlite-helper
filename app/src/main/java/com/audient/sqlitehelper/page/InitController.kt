package com.audient.sqlitehelper.page

import com.audient.libsuper.utils.closeByActionEvent
import com.audient.libsuper.utils.showAlertDialogError
import com.audient.sqlitehelper.bean.Env
import com.audient.sqlitehelper.mLocal
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.net.URL
import java.util.*

class InitStageWrapper(private val stage: Stage = Stage()) {
    fun show() {
        val loader = FXMLLoader(javaClass.classLoader.getResource("init.fxml"))
        val root = loader.load<Parent>()

        loader.getController<InitController>().apply {
            this.stage = this@InitStageWrapper.stage
        }

        stage.title = "睿家MQTT助手"
        stage.scene = Scene(root).apply {
            stylesheets.add("org/kordamp/bootstrapfx/bootstrapfx.css")
        }
        stage.sizeToScene()
        stage.show()
    }
}

class InitController : Initializable {
    lateinit var stage: Stage

    @FXML var tfDevelUrl: TextField? = null
    @FXML var tfDevelUsername: TextField? = null
    @FXML var tfDevelPassword: TextField? = null
    @FXML var tfProductlUrl: TextField? = null
    @FXML var tfProductUsername: TextField? = null
    @FXML var tfProductPassword: TextField? = null

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        tfDevelUrl?.text = mLocal.mqttDevel.url
        tfDevelUsername?.text = mLocal.mqttDevel.username
        tfDevelPassword?.text = mLocal.mqttDevel.password

        tfProductlUrl?.text = mLocal.mqttProduct.url
        tfProductUsername?.text = mLocal.mqttProduct.username
        tfProductPassword?.text = mLocal.mqttProduct.password
    }

    fun onBtnDevelControlClick(actionEvent: ActionEvent) {
        val url = tfDevelUrl?.text?.takeIf { it.isNotBlank() } ?: run {
            showAlertDialogError("url不能为空")
            return
        }
        val username = tfDevelUsername?.text?.takeIf { it.isNotBlank() } ?: run {
            showAlertDialogError("username不能为空")
            return
        }
        val password = tfDevelPassword?.text?.takeIf { it.isNotBlank() } ?: run {
            showAlertDialogError("password不能为空")
            return
        }

        mLocal.mqttDevel.url = url
        mLocal.mqttDevel.username = username
        mLocal.mqttDevel.password = password
        mLocal.save()

        MainStageWrapper().show(Env.Devel)
        stage.close()
    }

    fun onBtnProductControlClick(actionEvent: ActionEvent) {
        val url = tfProductlUrl?.text?.takeIf { it.isNotBlank() } ?: run {
            showAlertDialogError("url不能为空")
            return
        }
        val username = tfProductUsername?.text?.takeIf { it.isNotBlank() } ?: run {
            showAlertDialogError("username不能为空")
            return
        }
        val password = tfProductPassword?.text?.takeIf { it.isNotBlank() } ?: run {
            showAlertDialogError("password不能为空")
            return
        }

        mLocal.mqttProduct.url = url
        mLocal.mqttProduct.username = username
        mLocal.mqttProduct.password = password
        mLocal.save()

        MainStageWrapper().show(Env.Product)
        stage.closeByActionEvent(actionEvent)
    }
}