package io.github.bgmsound.bukkit.chat

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class BukkitChatEventListener(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) : Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    fun onChat(event: AsyncChatEvent) {
        if (event.isCancelled) {
            return
        }
        val message = event.message()
        if (message !is TextComponent) {
            return
        }
        kafkaTemplate.send(ChatProperties.CHAT_TOPIC, message.content())
    }
}