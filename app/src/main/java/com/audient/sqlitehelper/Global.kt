@file:Suppress("unused", "SpellCheckingInspection")

package com.audient.sqlitehelper

import com.alibaba.fastjson.JSON
import com.audient.libsuper.utils.FileUtils
import java.io.File

private val localFile: File by lazy { File(FileUtils.userHome, "rj_local_file.data") }

val mLocal: Local by lazy {
    try {
        val json = localFile.readText()
        JSON.parseObject(json, Local::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        Local()
    }
}

data class Local(
        var mqttDevel: MqttEnv = MqttEnv(),
        var mqttProduct: MqttEnv = MqttEnv(),
        var devicesDevel: ArrayList<Device> = ArrayList(),
        var devicesProduct: ArrayList<Device> = ArrayList()
) {
    fun save() {
        val json = JSON.toJSONString(this)
        localFile.writeText(json)
    }
}

data class MqttEnv(
        var url: String = "",
        var username: String = "",
        var password: String = ""
)

data class Device(
        var name: String = "",
        var deviceId: String = "",
        var deviceCode: String = ""
)