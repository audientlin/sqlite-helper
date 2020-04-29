package com.audient.sqlitehelper.bean

import com.alibaba.fastjson.JSON
import com.audient.libsuper.base.app
import com.audient.libsuper.utils.addAll
import com.audient.libsuper.utils.setOnClick

@Suppress("EnumEntryName")
enum class Action {
    callback,
    heart,
    openLog,
    ;

    companion object {
        fun valueOfOrNull(name: String): Action? {
            return try {
                valueOf(name)
            } catch (e: Exception) {
                null
            }
        }
    }
}

data class Message(
        var action: String = "",
        var content: String = "",
        var deviceCode: String = "",
        var time: Long = 0,
        var tno: String = "",
        var vendorCode: String = "",

        var original: String = "",
        var local: String? = null,
        var index: Int = 0
) {

    fun getShowText(): String {
        return local?.let {
            "$index$local"
        } ?: run {
            "${index}【${action}】$original"
        }
    }

    // shell数据
    // {"action":"callback","content":{"desc":"result: 0\nsuccessMsg: backup\nbackup_stage\ndeleteApkFile.dat\nlost+found\nmegvii\nrecovery\ntest\nerrorMsg: ","result":2},"deviceCode":"27b6425a6489477e910f505e8f693cba","time":"1584714055573","tno":"676a561be9924585a85e255b6b0d09cd","vendorCode":"rejia"}
    fun try2GetShellMessage(): String? {
        return try {
            val contentObj = JSON.parseObject(content)
            val desc = contentObj.getString("desc")
            if (desc.startsWith("result:")) desc else null
        } catch (e: Exception) {
            null
        }
    }

    // 屏幕截图
    // {"action":"callback","content":{"desc":"http://acs-image.corelines.cn/2905a98031900096_20200321030045390_screenshot.jpg","result":2},"deviceCode":"27b6425a6489477e910f505e8f693cba","time":"1584730847794","tno":"d3cec23458214c1f899e8e21d197f546","vendorCode":"rejia"}
    fun try2GetPictureUrl(): String? {
        return try {
            val contentObj = JSON.parseObject(content)
            val desc = contentObj.getString("desc")
            return desc.takeIf { it.endsWith(".jpg") || it.endsWith(".png") || it.endsWith("jpeg") }
        } catch (e: Exception) {
            null
        }
    }

    // 文件，包括日志、其它文件
    // {"action":"callback","content":{"desc":"{\"action\":\"deviceInfo\",\"content\":{\"url\":\"http://acs-dm.corelines.cn/2905a98031900096_1584722438317_20200317_121719.log\"},\"deviceCode\":\"27b6425a6489477e910f505e8f693cba\",\"time\":\"1584722461565\",\"vendorCode\":\"rejia\"}","result":2},"deviceCode":"27b6425a6489477e910f505e8f693cba","time":"1584722461571","tno":"0a90f505315441358eaed29eb3fc767a","vendorCode":"rejia"}
    fun try2GetFileUrl(): String? {
        return try {
            // 日志文件
            val desc = JSON.parseObject(content).getString("desc")
            val content2 = JSON.parseObject(desc).getString("content")
            val url = JSON.parseObject(content2).getString("url")
            url.takeIf { it.startsWith("http") }
        } catch (e: Exception) {
            null
        }
    }

    // 开门截图
    // {"action":"openLog","content":{"allowOpen":"1","direction":0,"ext":{},"openTime":"1584729667971","openType":5,"operator":"0","personnelType":"1","score":0,"snapUrl":"http://acs-image.corelines.cn/2905a98031900096_20200321024107969_0.jpg","videoUrl":"http://acs-video.corelines.cn/20200321024107825.mp4"},"deviceCode":"27b6425a6489477e910f505e8f693cba","time":"1584729667973","tno":"3ff695f1fcd746b9b75665b00808d504","vendorCode":"rejia"}
    fun try2GetOpenLogSnapUrl(): String? {
        return JSON.parseObject(content).getString("snapUrl").takeIf { it.isNotBlank() }
    }
}

data class Callback(
        var result: Int = -1,
        var desc: String = ""
)

data class CallbackDesc(
        var action: String = "",// deviceInfo
        var content: String = ""
)

data class CallbackDescContent(
        var url: String = ""
)

data class Heart(
        var appVer: String = "",
        var cardVer: Int = -1,
        var certVer: Int = -1,
        var cidNo: String = "",
        var deviceModel: String = "",
        var faceVer: Int = -1,
        var token: String = "",
        var signal: String = "",
        var netType: String = "",
        var xxx: String = "",
        var onTop: Boolean = false,
        var configVer: Long = -1
)

data class OpenLog(
        var allowOpen: String = "",
        var openTime: String = "",
        var operator: String = "",
        var personnelType: String = "",
        var snapUrl: String = "",
        var videoUrl: String = "",
        var direction: Int = -1,
        var openType: Int = -1,
        var score: Int = -1
)