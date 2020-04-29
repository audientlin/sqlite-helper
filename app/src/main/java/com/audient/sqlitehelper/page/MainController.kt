package com.audient.sqlitehelper.page

import com.alibaba.fastjson.JSON
import com.audient.libsuper.utils.addAll
import com.audient.libsuper.utils.setOnClick
import com.audient.libsuper.utils.setOnItemClick
import com.audient.libsuper.utils.showAlertDialogError
import com.audient.sqlitehelper.Device
import com.audient.sqlitehelper.bean.Env
import com.audient.sqlitehelper.mLocal
import com.github.kevinsawicki.http.HttpRequest
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.stage.Stage
import javafx.util.Callback
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class MainStageWrapper(private val stage: Stage = Stage()) {
    fun show(e: Env) {
        env = e

        val loader = FXMLLoader(javaClass.classLoader.getResource("main.fxml"))
        val root = loader.load<Parent>()

        loader.getController<MainController>().apply {
            this.stage = this@MainStageWrapper.stage
        }

        stage.title = "睿家MQTT助手【${env.label}】"
        stage.scene = Scene(root).apply {
            stylesheets.add("org/kordamp/bootstrapfx/bootstrapfx.css")
        }
        stage.sizeToScene()
        stage.show()
    }
}

private lateinit var env: Env

class MainController : Initializable {
    lateinit var stage: Stage

    @FXML var tfTmpDeviceId: TextField? = null
    @FXML var tfTmpDeviceCode: TextField? = null
    @FXML var tfTmpDeviceName: TextField? = null
    @FXML var lv: ListView<Device>? = null

    private val devices: ArrayList<Device> by lazy {
        when (env) {
            Env.Devel -> mLocal.devicesDevel
            Env.Product -> mLocal.devicesProduct
        }
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        lv?.cellFactory = Callback<ListView<Device>?, ListCell<Device>?> {
            object : ListCell<Device>() {
                override fun updateItem(item: Device?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (!empty && item != null) {
                        graphic = HBox().apply {
                            alignment = Pos.CENTER_LEFT
                            spacing = 5.0
                        }.addAll(
                                Label("【${item.deviceId}】【${item.deviceCode}】【${item.name}】").apply {
                                    styleClass.addAll("lead")
                                    HBox.setHgrow(this, Priority.ALWAYS)
                                },
                                Button("打开").apply {
                                    styleClass.addAll("lead")
                                    setOnClick {
                                        control(item)
                                    }
                                },
                                Button("删除").apply {
                                    styleClass.addAll("lead")
                                    setOnClick {
                                        devices.remove(item)
                                        mLocal.save()
                                        lv?.items?.remove(item)
                                    }
                                }
                        )
                    } else {
                        text = null
                        graphic = null
                    }
                }
            }
        }
        lv?.items = FXCollections.observableArrayList(devices)
    }

    fun onBtnCheckDeviceCode(actionEvent: ActionEvent) {
        val deviceId = tfTmpDeviceId?.text?.takeIf { it.isNotBlank() } ?: run {
            showAlertDialogError("设备号不能为空")
            return
        }
        handleCheckDeviceCode(deviceId)
    }

    fun onBtnControl(actionEvent: ActionEvent) {
        val deviceId = tfTmpDeviceId?.text?.takeIf { it.isNotBlank() } ?: run {
            showAlertDialogError("设备号不能为空")
            return
        }
        val deviceCode = tfTmpDeviceCode?.text?.takeIf { it.isNotBlank() } ?: run {
            showAlertDialogError("设备code不能为空")
            return
        }
        val deviceName = tfTmpDeviceName?.text?.takeIf { it.isNotBlank() } ?: run {
            showAlertDialogError("设备备注不能为空")
            return
        }

        val device = Device(deviceName, deviceId, deviceCode)

        if (devices.none { it.deviceId == deviceId && it.deviceCode == deviceCode && it.name == deviceName }) {
            devices.add(device)
            mLocal.save()
            lv?.items?.add(device)
        }
    }

    private fun handleCheckDeviceCode(deviceId: String) {
    }

    private fun control(device: Device) {
        DeviceActivity(device, env).start()
    }
}

