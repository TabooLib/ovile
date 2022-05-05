package ink.ptms.ovile.util

import taboolib.module.nms.MinecraftVersion

@Suppress("SpellCheckingInspection")
object Version {

    // Mojang 在 1.13 更新了 blockData, 我们以此为分界线来判断是否开启老版本兼容
    val isLegacyVersion: Boolean
        get() = MinecraftVersion.major < 11300
}