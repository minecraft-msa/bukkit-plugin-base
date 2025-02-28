package io.github.bgmsound.bukkit

import io.github.bgmsound.bukkit.plugin.core.SpringBootBukkitPlugin
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class LobbyServerPlugin : SpringBootBukkitPlugin(LobbyServerPlugin::class.java)