package com.audient.sqlitehelper.bean

import com.alibaba.fastjson.JSONObject
import java.util.*

fun getPlatformCmd(notify: Int): String {
    val obj = JSONObject()
    obj["tno"] = UUID.randomUUID().toString().replace("-", "")
    obj["action"] = "platform"
    obj["time"] = System.currentTimeMillis()
    obj["vendorCode"] = "rejia_test"
    obj["ext"] = "{}"
    obj["content"] = JSONObject().apply {
        put("notifyMessage", notify)
    }
    return obj.toString()
}

fun getAppCmd(notify: Int, content: String = ""): String {
    val obj = JSONObject()
    obj["tno"] = UUID.randomUUID().toString().replace("-", "")
    obj["action"] = "app"
    obj["time"] = System.currentTimeMillis()
    obj["vendorCode"] = "rejia_test"
    obj["ext"] = "{}"
    obj["content"] = JSONObject().apply {
        put("action", notify)
        put("content", content)
    }
    return obj.toString()
}

fun getDeviceCmd(cmdKey: String, cmdValue: String): String {
    val obj = JSONObject()
    obj["tno"] = UUID.randomUUID().toString().replace("-", "")
    obj["action"] = "device"
    obj["time"] = System.currentTimeMillis()
    obj["vendorCode"] = "rejia_test"
    obj["ext"] = "{}"
    obj["content"] = JSONObject().apply {
        put("cmdKey", cmdKey)
        put("cmdValue", cmdValue)
    }
    return obj.toString()
}

fun getShellCmd(cmd: String): String {
    val obj = JSONObject()
    obj["tno"] = UUID.randomUUID().toString().replace("-", "")
    obj["action"] = "shell"
    obj["time"] = System.currentTimeMillis()
    obj["vendorCode"] = "rejia_test"
    obj["ext"] = "{}"
    obj["content"] = JSONObject().apply {
        put("path", "/")
        put("cmd", cmd)
    }
    return obj.toString()
}