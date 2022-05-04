package ink.ptms.ovile.util.extension

import ink.ptms.ovile.Ovile
import ink.ptms.ovile.ingame.action.block.PlayerBlockAction
import org.bukkit.inventory.ItemStack
import taboolib.module.nms.MinecraftVersion

internal fun ItemStack.matchBlockAction(): PlayerBlockAction? {
    return Ovile.blockActions.firstOrNull { it.match(this) }
}

internal fun ItemStack.blockActionType(): String {
    return if (MinecraftVersion.majorLegacy > 11200) type.createBlockData().javaClass.simpleName else type.name
}