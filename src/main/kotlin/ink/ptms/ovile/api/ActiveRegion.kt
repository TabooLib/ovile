package ink.ptms.ovile.api

import ink.ptms.ovile.left
import ink.ptms.ovile.relative
import ink.ptms.ovile.right
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.block.data.type.Chest
import org.bukkit.block.data.type.Stairs
import org.bukkit.entity.Player
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet

/**
 * Ovile
 * ink.ptms.ovile.api.ActiveRegion
 *
 * @author 坏黑
 * @since 2022/5/1 20:49
 */
class ActiveRegion(val region: Region) {

    internal val players = CopyOnWriteArraySet<Player>()
    internal val blocks = ConcurrentHashMap<Location, BlockData>()

    init {
        blocks.putAll(region.defaultBlocks)
    }

    fun getPlayers(): Set<Player> {
        return players
    }

    fun getExternalPlayers(): Set<Player> {
        return Bukkit.getOnlinePlayers().filter { it !in players }.toSet()
    }

    fun getBlock(location: Location): BlockData? {
        return blocks[location]
    }

    fun setBlock(location: Location, data: BlockData?, ignored: List<Player> = emptyList()) {
        if (data == null) {
            blocks.remove(location.clone())
            players.filterNot { it in ignored }.forEach { it.sendBlockChange(location, Material.AIR.createBlockData()) }
        } else {
            if (data is Chest) {
                setChest(location, data, ignored)
            }
            blocks[location.clone()] = data
            players.filterNot { it in ignored }.forEach { it.sendBlockChange(location, data) }
        }
    }

    fun displayBlocks(player: Player) {
        blocks.forEach { player.sendBlockChange(it.key, it.value) }
    }

    fun destroyBlocks(player: Player) {
        val air = Material.AIR.createBlockData()
        blocks.forEach { player.sendBlockChange(it.key, air) }
    }

    private fun setChest(location: Location, data: Chest, ignored: List<Player>) {
        val right = location.relative(data.facing.right())
        val rightData = getBlock(right)
        if (rightData is Chest && rightData.type == Chest.Type.SINGLE) {
            data.type = Chest.Type.LEFT
            rightData.type = Chest.Type.RIGHT
            players.filterNot { it in ignored }.forEach { it.sendBlockChange(right, rightData) }
        } else {
            val left = location.relative(data.facing.left())
            val leftData = getBlock(left)
            if (leftData is Chest && leftData.type == Chest.Type.SINGLE) {
                data.type = Chest.Type.RIGHT
                leftData.type = Chest.Type.LEFT
                players.filterNot { it in ignored }.forEach { it.sendBlockChange(left, leftData) }
            }
        }
    }
}