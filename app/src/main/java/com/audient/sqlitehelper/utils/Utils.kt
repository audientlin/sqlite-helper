package com.audient.sqlitehelper.utils

import com.audient.libsuper.utils.FileUtils
import com.audient.sqlitehelper.ToastUtil
import com.github.kevinsawicki.http.HttpRequest
import java.io.File

fun downloadFile(url: String) {
    try {
        val filename = url.substringAfterLast("/")
        val request = HttpRequest.get(url)
        if (request.ok()) {
            val file = File(FileUtils.desktop, filename)
            request.receive(file)
            ToastUtil.toast("已下载到桌面")
            return
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    ToastUtil.toast("下载失败")
}