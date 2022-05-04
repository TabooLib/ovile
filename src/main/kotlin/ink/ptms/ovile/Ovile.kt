package ink.ptms.ovile

import ink.ptms.ovile.api.Region
import ink.ptms.ovile.ingame.action.block.PlayerBlockAction
import ink.ptms.ovile.ingame.action.region.PlayerRegionAction
import org.bukkit.Location
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.disablePlugin
import taboolib.common.platform.function.warning
import taboolib.module.nms.MinecraftVersion
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.max
import kotlin.math.min

object Ovile : Plugin() {

    internal val regions: MutableMap<String, MutableList<Region>> = ConcurrentHashMap()
    internal val blockActions: MutableList<PlayerBlockAction> = CopyOnWriteArrayList()
    internal val regionActions: MutableMap<Class<*>, MutableList<PlayerRegionAction<*>>> = HashMap()

    override fun onLoad() {
        if (MinecraftVersion.majorLegacy <= 11200) {
            warning("Ovile requires Minecraft 1.12 or higher.")
            disablePlugin()
        }
    }

    fun create(min: Location, max: Location): Region? {
        // 检查世界
        if (min.world != max.world || min.world == null || max.world == null) {
            error("Cannot create region between ${min.world?.name} and ${min.world?.name}")
        }
        // 修正坐标
        val minPos = Location(min.world, min(min.x, max.x), min(min.y, max.y), min(min.z, max.z)).toCenter(0.0)
        val maxPos = Location(max.world, max(min.x, max.x), max(min.y, max.y), max(min.z, max.z)).toCenter(1.0)
        // 检查重叠
        regions.values.forEach {
            it.forEach { region ->
                if (region.contains(minPos, maxPos)) {
                    return null
                }
            }
        }
        val region = Region(minPos, maxPos)
        regions.computeIfAbsent(min.world!!.name) { CopyOnWriteArrayList() }.add(region)
        return region
    }

    fun destroy(region: Region) {
        regions[region.world.name]?.remove(region)
    }

    fun registerBlockAction(action: PlayerBlockAction) {
        blockActions.add(action)
    }

    fun registerRegionAction(action: PlayerRegionAction<*>, bind: Class<*>) {
        regionActions.computeIfAbsent(bind) { ArrayList() }.add(action)
    }
}