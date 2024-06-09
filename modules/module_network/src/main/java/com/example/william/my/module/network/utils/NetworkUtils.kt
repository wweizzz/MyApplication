package com.example.william.my.module.network.utils

import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.LinkedList
import java.util.Locale
import java.util.Objects

object NetworkUtils {

    /**
     * 获取IP地址
     */
    fun getIPAddress(useIPv4: Boolean): String {
        try {
            val nis = NetworkInterface.getNetworkInterfaces()
            val adds = LinkedList<InetAddress>()
            while (nis.hasMoreElements()) {
                val ni = nis.nextElement()
                // To prevent phone of xiaomi return "10.0.2.15"
                if (!ni.isUp || ni.isLoopback) {
                    continue
                }
                val addresses = ni.inetAddresses
                while (addresses.hasMoreElements()) {
                    adds.addFirst(addresses.nextElement())
                }
            }
            for (add in adds) {
                if (!add.isLoopbackAddress) {
                    val hostAddress = add.hostAddress
                    val isIPv4 = Objects.requireNonNull(hostAddress).indexOf(':') < 0
                    if (useIPv4) {
                        if (isIPv4) {
                            return hostAddress ?: ""
                        }
                    } else {
                        if (!isIPv4) {
                            hostAddress?.let {
                                val index = hostAddress.indexOf('%')
                                return if (index < 0) {
                                    hostAddress.uppercase(Locale.getDefault())
                                } else {
                                    hostAddress.substring(0, index).uppercase(Locale.getDefault())
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        return ""
    }
}