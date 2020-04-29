package com.audient.libsuper.demo.widget

import javafx.application.Application;
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene;
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage;
import javafx.util.Callback

fun main() {
    Application.launch(ListViewDemo::class.java)
}

data class User(val name: String, val age: Int)

class ListViewDemo : Application() {
    override fun start(primaryStage: Stage?) {
        val root = VBox().apply {
            padding = Insets(10.0)

            val listView = ListView<User>(FXCollections.observableArrayList()).apply {
                setPrefSize(400.0, 500.0)
                placeholder = Label("没有数据")
            }

            // 设置可编辑
            listView.isEditable = true
//            listView.cellFactory = TextFieldListCell.forListView()

            // 数据格式化
//            listView.cellFactory = TextFieldListCell.forListView(object : StringConverter<User>() {
//                override fun fromString(string: String?): User {
//                    return User("不可编辑", 0)
//                }
//
//                override fun toString(`object`: User?): String {
//                    return `object`?.username ?: ""
//                }
//            })
            // 数据格式化、UI
            listView.cellFactory = Callback<ListView<User>?, ListCell<User>?> {

                it?.setOnEditStart {
                    println("监听到编辑开始...")
                }

                object : ListCell<User>() {

                    override fun startEdit() {
                        super.startEdit()
                        // listView设置为可编辑后，双击item或者在item上按回车的时候回调
                        println("开始编辑...")
                    }

                    override fun commitEdit(newValue: User?) {
                        super.commitEdit(newValue)
                        // TextField提交的时候调用
                        println("提交编辑")
                    }

                    override fun cancelEdit() {
                        super.cancelEdit()
                        println("取消编辑")
                    }

                    override fun updateItem(item: User?, empty: Boolean) {
                        super.updateItem(item, empty)
                        if (!empty) {
                            this.graphic = HBox(10.0).apply {
                                alignment = Pos.CENTER_LEFT

                                children.addAll(
                                        ImageView("ic_switch_open_blue.png").apply {
                                            isPreserveRatio = true// 保持宽高比
                                            fitHeight = 20.0
                                        },
                                        Label(item?.name ?: "").apply {
                                            styleClass.addAll("lead")
                                        },
                                        Button("打印用户信息").apply {
                                            prefHeight = 40.0

                                            setOnAction {
                                                println(item?.toString() ?: "")
                                            }
                                        }
                                )
                            }
                        }
                    }
                }
            }

            // 添加数据
            repeat(10) {
                listView.items.add(User("用户$it", it))
            }

            // 设置item的高度
//            listView.fixedCellSize = 50.0

            // 滚动到最后
            listView.scrollTo(Int.MAX_VALUE)

            // 设置多选
//            listView.selectionModel.selectionMode = SelectionMode.MULTIPLE

            // 单击事件
//            listView.selectionModel.selectedItemProperty().addListener { observable, oldValue, newValue ->
//                println("oldValue=$oldValue newValue=$newValue")
//            }

            children.add(listView)
            children.add(Button("测试").apply {
                styleClass.addAll("lead", "btn-danger")
                setPrefSize(400.0, 50.0)
                setOnAction {

                    // 获取当前选择的item
//                    println(listView.selectionModel.selectedItem)

                    listView.items.forEach { println(it) }
                }
            })
        }

        primaryStage?.scene = Scene(root).apply {
            stylesheets.add("org/kordamp/bootstrapfx/bootstrapfx.css")
        }
        primaryStage?.title = "BootStrapFXDemo"
        primaryStage?.sizeToScene()
        primaryStage?.show()
    }
}