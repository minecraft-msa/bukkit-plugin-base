package io.github.bgmsound.bukkit.plugin.core

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.env.PropertySource
import org.springframework.core.io.Resource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

class YamlPropertiesFactory : PropertySourceFactory {
    @Throws(IOException::class)
    override fun createPropertySource(name: String?, resource: EncodedResource): PropertySource<*> {
        val propertiesFromYaml = loadYamlIntoProperties(resource.resource)
        val sourceName = name ?: resource.resource.filename!!
        return PropertiesPropertySource(sourceName, propertiesFromYaml!!)
    }

    companion object {
        @Throws(FileNotFoundException::class)
        fun loadYamlIntoProperties(resource: Resource?): Properties? {
            return runCatching {
                val factory = YamlPropertiesFactoryBean()
                factory.setResources(resource)
                factory.afterPropertiesSet()
                factory.getObject()
            }.getOrElse { exception ->
                val cause = exception.cause
                if (cause is FileNotFoundException) throw (exception.cause as FileNotFoundException?)!!
                throw exception
            }
        }
    }
}