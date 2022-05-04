package ink.ptms.ovile.util.extension

import ink.ptms.ovile.OvileAPI
import ink.ptms.ovile.api.RegionBlock
import ink.ptms.ovile.ingame.action.block.PlayerBlockAction

internal fun RegionBlock.matchBlockAction(): PlayerBlockAction? {
    return OvileAPI.blockActions.firstOrNull { it.match(this) }
}