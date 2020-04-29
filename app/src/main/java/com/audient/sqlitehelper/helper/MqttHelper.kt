package com.audient.sqlitehelper.helper

import com.audient.sqlitehelper.Device
import com.audient.sqlitehelper.MqttEnv
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import java.util.Random

@Suppress("SpellCheckingInspection")
class MqttHelper(val device: Device) {

    private var client: MqttClient? = null

    val isConnected: Boolean
        get() = client?.isConnected == true

    var onMessageListener: ((original: String) -> Unit)? = null

    fun connect(mqttEnv: MqttEnv) {
        val url = mqttEnv.url
        val username = mqttEnv.username
        val password = mqttEnv.password

        try {
            if (isConnected) {
                client?.disconnectForcibly()
            }

            val clientId = "client_by_mqtt_helper_" + Random().nextInt(999999)
            client = MqttClient(url, clientId, MemoryPersistence())
            println("mqtt connecting...")
            val options = MqttConnectOptions()
            options.isCleanSession = true
            options.userName = username
            options.password = password.toCharArray()
            client!!.connect(options)
            println(">>> mqtt connect successful! 【${url}】")

            // 订阅callback
            client!!.subscribe("device/push/callback", 2) { _, message ->
                val msg = String(message.payload)
                println("【收到callback】 => $msg")
                onMessageListener?.invoke(msg)
            }

            // 订阅message
            client!!.subscribe("device/msg/${device.deviceCode}", 2) { _, message ->
                val msg = String(message.payload)
                println("【收到message】 => $msg")
                onMessageListener?.invoke(msg)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun publish(text: String, topic: String = "device/notify/${device.deviceCode}") {
        try {
            println("【发送】 >>> $topic $text")
            val message = MqttMessage(text.toByteArray()).apply {
                qos = 2
                isRetained = false
            }
            client!!.publish(topic, message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        try {
            client?.disconnect()
        } catch (e: Exception) {
            // nothing
        }
    }
}