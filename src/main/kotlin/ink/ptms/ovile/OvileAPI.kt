package ink.ptms.ovile

import org.bukkit.Location
import taboolib.common5.Baffle
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

@Suppress("SpellCheckingInspection")
object OvileAPI {

    internal val notifyBaffle = Baffle.of(200, TimeUnit.MILLISECONDS)

    internal val ignoreBlockChangeMap = ConcurrentHashMap<String, MutableSet<Location>>()
}