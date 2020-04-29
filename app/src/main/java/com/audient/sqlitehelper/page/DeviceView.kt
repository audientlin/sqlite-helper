package com.audient.sqlitehelper.page

import com.audient.sqlitehelper.ToastUtil
import com.audient.libsuper.base.BaseView
import com.audient.libsuper.base.app
import com.audient.libsuper.utils.*
import com.audient.sqlitehelper.bean.*
import com.audient.sqlitehelper.utils.downloadFile
import com.audient.sqlitehelper.widget.CopyableLabel
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.control.*
import javafx.scene.layout.*

abstract class DeviceView : BaseView() {

    lateinit var btnConnect: Button
    lateinit var tfCmd: TextField
    lateinit var tfFilter: TextField
    lateinit var lv: ListView<Message>
    lateinit var topView: VBox
    lateinit var lineCountLabel: Label
    lateinit var btnClear: Button

    override fun getRootView(): Parent {
        return BorderPane().apply {
            setPrefSize(1000.0, 800.0)
            padding = Insets(10.0)

            top = VBox().apply {
                topView = this
                spacing = 10.0
                padding = Insets(0.0, 0.0, 10.0, 0.0)
            }.addAll(
                    Button().apply {
                        btnConnect = this
                        prefWidthProperty().bind(topView.widthProperty())
                    },

                    FlowPane().addAll(
                            Button("卡增量").apply { setOnClick { onSendPlatformCmd(1) } },
                            Button("卡全量").apply { setOnClick { onSendPlatformCmd(2) } },
                            Button("脸增量").apply { setOnClick { onSendPlatformCmd(3) } },
                            Button("脸全量").apply { setOnClick { onSendPlatformCmd(4) } },
                            Button("平台配置更新").apply { setOnClick { onSendPlatformCmd(5) } },
                            Button("开门凭证增量").apply { setOnClick { onSendPlatformCmd(7) } },
                            Button("开门凭证全量").apply { setOnClick { onSendPlatformCmd(8) } },
                            Button("远程开门").apply { setOnClick { onSendPlatformCmd(6) } },
                            Button("设备状态更新").apply { setOnClick { onSendPlatformCmd(9) } },
                            Button("系统截屏").apply { setOnClick { onSendPlatformCmd(90) } },
                            Button("应用截屏").apply { setOnClick { onSendPlatformCmd(91) } },
                            Button("应用状态").apply { setOnClick { onSendPlatformCmd(92) } },
                            Button("设备状态").apply { setOnClick { onSendPlatformCmd(93) } },
                            Button("基础配置").apply { setOnClick { onSendPlatformCmd(94) } },
                            Button("人脸配置").apply { setOnClick { onSendPlatformCmd(95) } },
                            Button("设备配置").apply { setOnClick { onSendPlatformCmd(96) } },
                            Button("平台配置").apply { setOnClick { onSendPlatformCmd(97) } },
                            Button("全部信息").apply { setOnClick { onSendPlatformCmd(98) } },
                            Button("重启设备").apply { setOnClick { onSendPlatformCmd(99) } },
                            Button("恢复出厂").apply { setOnClick { onSendPlatformCmd(1024) } },

                            Button("业务配置更新").apply { setOnClick { onSendAppCmd(100) } },
                            Button("房屋节点更新").apply { setOnClick { onSendAppCmd(104) } },
                            Button("检查公告更新").apply { setOnClick { onSendAppCmd(996) } },
                            Button("检查应用更新").apply { setOnClick { onSendAppCmd(998) } },
                            Button("应用重启").apply { setOnClick { onSendAppCmd(999) } },

                            Button("日志1").apply { setOnClick { onSendDeviceCmd("pullLog", "1") } },
                            Button("日志2").apply { setOnClick { onSendDeviceCmd("pullLog", "2") } },
                            Button("facepass.db").apply { setOnClick { onSendDeviceCmd("pull", "/sdcard/facepass.db") } },
                            Button("rejia.db").apply { setOnClick { onSendDeviceCmd("pull", "/data/data/com.rejia.doors/databases/rejia.db") } }
                    ),

                    BorderPane().apply {
                        // 输入命令
                        center = TextField().apply {
                            tfCmd = this
                            styleClass.addAll("lead")
                            promptText = "普通命令如：set pullLog=1，shell命令如：ls /cache/，按回车执行"
                        }
                        // filter
                        right = TextField().apply {
                            tfFilter = this
                            styleClass.addAll("lead")
                            promptText = "filter(只支持字符串)"
                        }
                    }
            )

            bottom = FlowPane().apply {
                padding = Insets(10.0, 0.0, 0.0, 0.0)
            }.addAll(
                    Label().apply { lineCountLabel = this },
                    Button("清空").apply { btnClear = this }
            )

            center = ListView<Message>().apply {
                lv = this
                placeholder = Label("没有数据")

                setCellFactory { param: ListView<Message>? ->
                    object : ListCell<Message>() {
                        override fun updateItem(item: Message?, empty: Boolean) {
                            super.updateItem(item, empty)

                            if (!empty && item != null) {
                                try {
                                    item.local?.let {
                                        graphic = getLocalMessageView(item)
                                    } ?: run {
                                        graphic = when (Action.valueOfOrNull(item.action)) {
                                            Action.openLog -> getOpenDoorMessageView(item)
                                            Action.callback -> getCallbackMessageView(item)
                                            else -> getSingleLineMessageView(item)
                                        }
                                    }
                                } catch (e: Exception) {
                                    graphic = getLocalMessageView(Message(local = "【处理异常】${e.message}"))
                                }
                            } else {
                                text = null
                                graphic = null
                            }
                        }
                    }
                }
            }
        }
    }

    abstract fun onSendPlatformCmd(notify: Int)
    abstract fun onSendAppCmd(notify: Int)
    abstract fun onSendDeviceCmd(cmdKey: String, cmdValue: String)

    private fun getOpenDoorMessageView(message: Message): Node {
        val snapUrl = message.try2GetOpenLogSnapUrl()
        return VBox().apply {
            children.add(
                    CopyableLabel(message.getShowText()).apply {
                        setOnDoubleClick {
                            ClipboardUtils.addString(message.original)
                            ToastUtil.toast("已复制")
                        }
                    }
            )
            snapUrl?.let {
                children.add(
//                        ImageView(snapUrl).apply {
//                            isPreserveRatio = true
//                            fitHeight = 100.0
//                            setOnMouseClicked {
//                                println("onMouseClicked")
//                            }
//                        }
                        Button("打开图片$it").apply {
                            setOnClick {
                                app.hostServices.showDocument(it)
                            }
                        }
                )
            }
        }
    }

    private fun getCallbackMessageView(message: Message): Node {
        val shell = message.try2GetShellMessage()
        val url = message.try2GetFileUrl()
        val pictureUrl = message.try2GetPictureUrl()
        return VBox().apply {
            children.add(
                    CopyableLabel(message.getShowText()).apply {
                        setOnDoubleClick {
                            ClipboardUtils.addString(message.original)
                            ToastUtil.toast("已复制")
                        }
                    }
            )
            shell?.let {
                children.add(Label(it))
            }
            url?.let {
                children.add(
                        Button("下载到桌面$url").apply {
                            setOnClick {
                                //                                app.hostServices.showDocument(url)
                                downloadFile(url)
                            }
                        }
                )
            }
            pictureUrl?.let {
                children.add(
                        Button("打开图片$it").apply {
                            setOnClick {
                                app.hostServices.showDocument(it)
                            }
                        }
                )
            }
        }
    }

    private fun getSingleLineMessageView(message: Message): Node {
        return CopyableLabel(message.getShowText()).apply {
            setOnDoubleClick {
                ClipboardUtils.addString(message.original)
                ToastUtil.toast("已复制")
            }
        }
    }

    // 本地消息
    private fun getLocalMessageView(message: Message): Node {
        return CopyableLabel(message.getShowText())
    }
}