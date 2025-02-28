package io.github.bgmsound.bukkit.plugin.core

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.springframework.boot.Banner
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.logging.LoggingSystem
import org.springframework.boot.logging.logback.LogbackLoggingSystem
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.io.DefaultResourceLoader

open class SpringBootBukkitPlugin(
    private val mainClass: Class<*>
) : JavaPlugin() {
    private var applicationContext: ConfigurableApplicationContext? = null

    override fun onEnable() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }
        System.setProperty(
            LoggingSystem.SYSTEM_PROPERTY,
            LogbackLoggingSystem::class.java.name
        )
        this.applicationContext = SpringApplicationBuilder()
            .sources(mainClass)
            .resourceLoader(DefaultResourceLoader(mainClass.classLoader))
            .bannerMode(Banner.Mode.OFF)
            .registerShutdownHook(false)
            .logStartupInfo(true)
            .run()
        applicationContext?.getBeansOfType(Listener::class.java)?.values?.forEach {
            Bukkit.getPluginManager().registerEvents(it, this)
        }
    }

    override fun onDisable() {
        applicationContext?.close()
    }
}