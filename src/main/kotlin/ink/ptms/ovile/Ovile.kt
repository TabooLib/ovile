package ink.ptms.ovile

import ink.ptms.ovile.api.Region
import org.bukkit.Location
import taboolib.common.platform.Plugin
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.max
import kotlin.math.min

object Ovile : Plugin() {

    internal val regions: MutableMap<String, MutableList<Region>> = ConcurrentHashMap()

    fun create(min: Location, max: Location): Region {
        // 检查世界
        if (min.world != max.world || min.world == null || max.world == null) {
            error("Cannot create region between ${min.world?.name} and ${min.world?.name}")
        }
        // 修正坐标
        val minPos = Location(min.world, min(min.x, max.x), min(min.y, max.y), min(min.z, max.z)).toCenter(0.0)
        val maxPos = Location(max.world, max(min.x, max.x), max(min.y, max.y), max(min.z, max.z)).toCenter(1.0)
        // 检查重叠
        if (min.world!!.getRegion(minPos) != null || max.world!!.getRegion(maxPos) != null) {
            error("Overlap with existing region")
        }
        val region = Region(minPos, maxPos)
        regions.computeIfAbsent(min.world!!.name) { CopyOnWriteArrayList() }.add(region)
        return region
    }

    fun destroy(region: Region) {
        regions[region.world.name]?.remove(region)
    }
}