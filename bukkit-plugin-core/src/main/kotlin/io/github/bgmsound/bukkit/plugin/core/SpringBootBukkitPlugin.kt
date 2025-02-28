package io.github.bgmsound.bukkit.plugin.core

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.springframework.boot.Banner
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.logging.LoggingSystem
import org.springframework.boot.logging.logback.LogbackLoggingSystem
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.FileSystemResource
import java.io.File
import java.util.*


open class SpringBootBukkitPlugin(
    private val mainClass: Class<*>,
) : JavaPlugin() {
    private var applicationContext: ConfigurableApplicationContext? = null

    override fun onEnable() {
        System.setProperty(
            LoggingSystem.SYSTEM_PROPERTY,
            LogbackLoggingSystem::class.java.name
        )
        this.applicationContext = SpringApplicationBuilder()
            .sources(mainClass)
            .initializers(ApplicationContextInitializer<ConfigurableApplicationContext> {
                val propertySources = it.environment.propertySources
                propertySources.addLast(BukkitConfigPropertySource(config))
                propertySources.addLast(PropertiesPropertySource("main-yaml", primaryApplicationProperties()))
            })
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

    private fun primaryApplicationProperties(): Properties {
        if (!dataFolder.exists()) dataFolder.mkdirs()
        val configurationFile = FileSystemResource(File(dataFolder, "application.yml"))
        if (!configurationFile.exists()) {
            configurationFile.file.createNewFile()
        }
        return YamlPropertiesFactory.loadYamlIntoProperties(configurationFile)!!
    }
}