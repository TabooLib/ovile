package ink.ptms.ovile

import taboolib.common.platform.Plugin
import taboolib.common.platform.function.disablePlugin
import taboolib.common.platform.function.warning
import taboolib.module.nms.MinecraftVersion

@Suppress("SpellCheckingInspection")
object Ovile : Plugin() {

    override fun onLoad() {
        if (MinecraftVersion.majorLegacy <= 11200) {
            warning("Ovile requires Minecraft 1.12 or higher.")
            disablePlugin()
        }
    }
}