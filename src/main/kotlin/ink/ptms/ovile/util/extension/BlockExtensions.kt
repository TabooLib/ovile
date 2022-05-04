package ink.ptms.ovile.util.extension

import org.bukkit.block.BlockFace

fun BlockFace.right(): BlockFace {
    return if (ordinal + 1 > 3) BlockFace.NORTH else BlockFace.values()[ordinal + 1]
}

fun BlockFace.left(): BlockFace {
    return right().oppositeFace
}