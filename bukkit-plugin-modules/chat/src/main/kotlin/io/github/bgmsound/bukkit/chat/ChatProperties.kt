package io.github.bgmsound.bukkit.chat

import org.bukkit.entity.Player

object ChatProperties {
    const val CHAT_TOPIC = "chat"
    const val CHAT_GROUP = "bukkit-msa-default-group"

    private const val CHAT_FORMAT = "%s: %s"

    fun String.asChatFormat(sender: Player): String {
        return CHAT_FORMAT.format(sender.name, this)
    }
}