package ink.ptms.ovile

import ink.ptms.ovile.function.FunctionCreateArea
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import taboolib.platform.util.sendLang

@Suppress("SpellCheckingInspection")
@CommandHeader(name = "Ovile", aliases = ["o"])
object Commands {

    @CommandBody
    val main = mainCommand {
        createHelper()
    }

    @CommandBody
    val create = subCommand {
        execute<Player> { player, _, _ ->
            if (!FunctionCreateArea.isLegalLocation()) {
                player.sendLang("player-create-area-fail-by-points")
                return@execute
            }
            if (Ovile.create(FunctionCreateArea.selectPoints[0], FunctionCreateArea.selectPoints[1]) == null) {
                player.sendLang("player-create-area-fail-by-conflict")
                return@execute
            }
            player.sendLang("player-create-area-succ")
        }
    }
}