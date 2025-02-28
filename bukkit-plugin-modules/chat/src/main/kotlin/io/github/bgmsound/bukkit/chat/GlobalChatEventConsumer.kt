package io.github.bgmsound.bukkit.chat

import org.bukkit.Bukkit
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import net.kyori.adventure.text.Component as ChatComponent

@Component
class GlobalChatEventConsumer {
    @KafkaListener(groupId = ChatProperties.CHAT_GROUP, topics = [ChatProperties.CHAT_TOPIC])
    fun consume(message: String) {
        Bukkit.broadcast(ChatComponent.text(message))
    }
}