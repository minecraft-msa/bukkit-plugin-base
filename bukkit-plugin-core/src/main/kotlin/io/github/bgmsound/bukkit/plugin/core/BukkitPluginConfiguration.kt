package io.github.bgmsound.bukkit.plugin.core

import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class BukkitPluginConfiguration {
    @Bean
    @Primary
    fun plugin(): Plugin {
        return Bukkit.getPluginManager().plugins.single { it is SpringBootBukkitPlugin }
    }

    @Bean
    @Primary
    fun lifecycleManager(plugin: Plugin): LifecycleEventManager<Plugin> {
        return plugin.lifecycleManager
    }
}