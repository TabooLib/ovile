package ink.ptms.ovile.api

import ink.ptms.ovile.Ovile
import ink.ptms.ovile.api.event.OvilePlayerEnterRegionEvent
import ink.ptms.ovile.api.event.OvilePlayerLeaveRegionEvent
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyParticle
import taboolib.common.platform.function.adaptPlayer
import taboolib.common.util.Vector
import taboolib.module.effect.Cube
import taboolib.module.effect.Line
import taboolib.module.effect.ParticleSpawner
import taboolib.platform.util.toProxyLocation
import java.util.*
import java.util.concurrent.ConcurrentSkipListSet
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CopyOnWriteArraySet

/**
 * @author 坏黑
 * @since 2022/5/1 19:54
 */
class Region(val min: Location, val max: Location) : Box(min.x, min.y, min.z, max.x, max.y, max.z) {

    val id: UUID = UUID.randomUUID()
    val world = min.world!!

    internal val activeRegions = CopyOnWriteArraySet<ActiveRegion>()

    fun createActiveRegion(): ActiveRegion {
        return ActiveRegion(this)
    }

    fun getActiveRegion(player: Player): ActiveRegion? {
        return activeRegions.firstOrNull { it.getPlayers().contains(player) }
    }

    fun destroy() {
        Ovile.regions[world.name]?.remove(this)
    }

    fun enter(player: Player) {
        showBorder(player, ProxyParticle.VILLAGER_HAPPY)
        val event = OvilePlayerEnterRegionEvent(player, createActiveRegion())
        if (event.call()) {
            activeRegions.add(event.region)
            event.region.players.add(player)
        }
    }

    fun leave(player: Player) {
        val activeRegion = getActiveRegion(player) ?: return
        showBorder(player, ProxyParticle.FLAME)
        OvilePlayerLeaveRegionEvent(player, activeRegion).call()
        activeRegion.players.remove(player)
        if (activeRegion.players.isEmpty()) {
            activeRegions.remove(activeRegion)
        }
    }

    fun showBorder(player: Player, particle: ProxyParticle) {
        Cube(min.toProxyLocation(), max.toProxyLocation(), object : ParticleSpawner {

            override fun spawn(location: taboolib.common.util.Location) {
                adaptPlayer(player).sendParticle(particle, location, Vector(0, 0, 0), 1, 0.0, null)
            }
        }).show()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Region) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Region(id=$id, world=$world) ${super.toString()}"
    }
}