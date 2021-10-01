package me.khora.eris.audio

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.requests.RestAction
import net.dv8tion.jda.internal.utils.Checks
import java.time.OffsetDateTime

class CachedMessage(message: Message) {
    val messageId: Long
    val channelId: Long
    val authorId: Long
    val content: String
    val creationDate: OffsetDateTime
    private val jda: JDA

    fun retrieveMessage(): RestAction<Message> {
        val channel = channel
        Checks.check(channel != null, "Channel no longer exists!")
        return channel!!.retrieveMessageById(messageId)
    }

    val channel: TextChannel?
        get() = jda.getTextChannelById(channelId)

    init {
        Checks.notNull(message, "Message")
        messageId = message.idLong
        channelId = message.channel.idLong
        authorId = message.author.idLong
        content = message.contentRaw
        creationDate = message.timeCreated
        jda = message.jda
    }
}