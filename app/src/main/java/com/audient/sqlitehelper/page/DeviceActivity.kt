package com.audient.sqlitehelper.page

import com.alibaba.fastjson.JSON
import com.audient.sqlitehelper.helper.MqttHelper
import com.audient.libsuper.base.BaseActivity
import com.audient.libsuper.utils.setOnClick
import com.audient.libsuper.utils.setOnEnter
import com.audient.libsuper.utils.setOnValueChanged
import com.audient.sqlitehelper.Device
import com.audient.sqlitehelper.bean.*
import com.audient.sqlitehelper.mLocal
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.concurrent.ScheduledService
import javafx.concurrent.Task
import javafx.util.Duration
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Suppress("SpellCheckingInspection")
class DeviceActivity(private val device: Device, private val env: Env) : BaseActivity<DeviceView>() {

    private var mqttHelper: MqttHelper? = null

    private val items = FXCollections.observableArrayList<Message>()
    private var itemsFilter: ObservableList<Message>? = null
    private var filterText: String = ""

    private val mqttEnv by lazy {
        when (env) {
            Env.Devel -> mLocal.mqttDevel
            Env.Product -> mLocal.mqttProduct
        }
    }

    private val onSecondTickService by lazy {
        object : ScheduledService<Int>() {
            override fun createTask(): Task<Int> {
                return object : Task<Int>() {
                    @Throws(Exception::class)
                    override fun call(): Int? {
                        return 0
                    }

                    override fun updateValue(value: Int?) {
                        super.updateValue(value)
                        onSecondTick()
                    }
                }
            }
        }
    }

    override fun getBaseView(): DeviceView {
        return object : DeviceView() {
            override fun onSendAppCmd(notify: Int) {
                publishCmd(getAppCmd(notify))
            }

            override fun onSendDeviceCmd(cmdKey: String, cmdValue: String) {
                publishCmd(getDeviceCmd(cmdKey, cmdValue))
            }

            override fun onSendPlatformCmd(notify: Int) {
                publishCmd(getPlatformCmd(notify))
            }
        }
    }

    override fun initSomething() {
        super.initSomething()
        stage.title = "${device.deviceId}【${device.name}】"
        view.lv.items = items

        onSecondTickService.period = Duration(1000.0)
        onSecondTickService.delay = Duration(1000.0)
        onSecondTickService.start()

        try {
            mqttHelper = MqttHelper(device)
            mqttHelper?.onMessageListener = { original ->
                onMqttMessageReceived(original)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun initListeners() {
        super.initListeners()

        stage.setOnCloseRequest { event ->
            mqttHelper?.disconnect()
            onSecondTickService.cancel()
        }

        view.btnConnect.setOnClick {
            mqttHelper?.connect(mqttEnv)
//            onMqttMessageReceived("""
//    {"action":"openLog","content":{"allowOpen":"1","direction":0,"ext":{},"openTime":"1584729667971","openType":5,"operator":"0","personnelType":"1","score":0,"snapUrl":"http://images.cnitblog.com/blog/489550/201304/18085328-746b336c1d9c4ddfb13963e9e6820a6c.jpg","videoUrl":"http://acs-video.corelines.cn/20200321024107825.mp4"},"deviceCode":"27b6425a6489477e910f505e8f693cba","time":"1584729667973","tno":"3ff695f1fcd746b9b75665b00808d504","vendorCode":"rejia"}
//                            """.trimIndent())
        }

        view.tfCmd.setOnEnter {
            // set xxx=xxx
            val text = it.text.trim()
            if (text.startsWith("set")) {
                // 设置命令
                val cmdText = text.substring(3, text.length).trim()

                val arr = cmdText.split("=")
                if (arr.size != 2) {
                    addMessage(Message(local = "[$text]命令格式错误，必须是：set xxx=xxx"))
                    return@setOnEnter
                }

                val key = arr[0].trim()
                val value = arr[1].trim()
                publishCmd(getDeviceCmd(key, value))
            } else {
                publishCmd(getShellCmd(text))
            }
            it.clear()
        }

        view.tfFilter.setOnValueChanged { onFilterChanged(it) }

        view.btnClear.setOnClick {
            synchronized(index) {
                index = 0
                view.lv.items.clear()
                view.lineCountLabel.text = ""
            }
        }
    }

    private fun onSecondTick() {
        if (mqttHelper?.isConnected == true) {
            view.btnConnect.text = "已连接【${env.label}】"
            view.btnConnect.styleClass.removeAll("lead", "btn-danger")
            view.btnConnect.styleClass.addAll("lead", "btn-success")
            view.btnConnect.isDisable = true
        } else {
            view.btnConnect.text = "点击连接MQTT"
            view.btnConnect.styleClass.removeAll("lead", "btn-success")
            view.btnConnect.styleClass.addAll("lead", "btn-danger")
            view.btnConnect.isDisable = false
        }
    }

    private fun onMqttMessageReceived(original: String) {
        try {
            val message = JSON.parseObject(original, Message::class.java).apply {
                this.original = original
            }

            if (message.deviceCode == device.deviceCode) {
                addMessage(message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun publishCmd(cmd: String) {
        if (mqttHelper?.isConnected == true) {
            addMessage(Message(local = "【发送】$cmd"));
            mqttHelper!!.publish(cmd);
        } else {
            addMessage(Message(local = "=== MQTT未连接 ==="));
        }
    }

    private val addMessageContext = newSingleThreadContext("add-message")
    private var index = 0

    private fun addMessage(message: Message) {
        runBlocking {
            withContext(addMessageContext) {
                message.index = this@DeviceActivity.index++
                Platform.runLater {
                    if (filterText.isBlank()) {
                        // 木有filter
                        items.add(message)
                        view.lineCountLabel.text = "总行数：${view.lv.items.size}"
                    } else {
                        // 有filter
                        // 有filter也要add，这时候ListView用的是itemsFilter
                        items.add(message)

                        if (message.getShowText().contains(filterText, ignoreCase = true)) {
                            // 通过filter
                            itemsFilter?.add(message)
                            view.lineCountLabel.text = "总行数：${view.lv.items.size}"
                        }
                    }
                }
            }
        }
    }

    private fun onFilterChanged(newFilter: String) {
        filterText = newFilter
        if (filterText.isBlank()) {
            // 木有filter
            view.lv.items = items
            itemsFilter = null
        } else {
            // 有filter
            itemsFilter = items.filtered { it.getShowText().contains(filterText, true) }
            view.lv.items = itemsFilter
        }
    }
}
