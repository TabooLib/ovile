package ink.ptms.ovile.util.extension

import org.bukkit.Material
import org.bukkit.block.BlockFace

internal val Material.isChest: Boolean
    get() = when (this) {
        Material.TRAPPED_CHEST -> true
        Material.CHEST -> true
        Material.ENDER_CHEST -> true
        else -> false
    }

fun BlockFace.right(): BlockFace {
    return if (ordinal + 1 > 3) BlockFace.NORTH else BlockFace.values()[ordinal + 1]
}

fun BlockFace.left(): BlockFace {
    return right().oppositeFace
}