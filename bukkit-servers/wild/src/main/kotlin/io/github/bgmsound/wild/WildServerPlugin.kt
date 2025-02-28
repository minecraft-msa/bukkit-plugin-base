package io.github.bgmsound.wild

import io.github.bgmsound.bukkit.plugin.core.SpringBootBukkitPlugin
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class WildServerPlugin : SpringBootBukkitPlugin(WildServerPlugin::class.java)