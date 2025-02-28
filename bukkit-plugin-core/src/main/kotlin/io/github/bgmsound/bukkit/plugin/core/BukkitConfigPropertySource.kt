package io.github.bgmsound.bukkit.plugin.core

import org.bukkit.configuration.file.FileConfiguration
import org.springframework.core.env.PropertySource

class BukkitConfigPropertySource(source: FileConfiguration) : PropertySource<FileConfiguration>("config", source) {
    override fun getProperty(key: String): Any? {
        return source[key]
    }
}