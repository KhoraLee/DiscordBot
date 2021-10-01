package me.khora.eris.Utils

import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.PrivateChannel
import net.dv8tion.jda.api.entities.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ThreadFactory


object Util {
    private val LOGGER = LoggerFactory.getLogger(Util::class.java)

    /**
     * Auto closes AutoClosables
     *
     * @param closeables Closeables
     */
    fun closeQuietly(vararg closeables: AutoCloseable?) {
        for (c in closeables) {
            if (c != null) {
                try {
                    c.close()
                } catch (ignored: Exception) {
                }
            }
        }
    }

    fun titleMarkdown(track: AudioTrack): String {
        return "[" + track.info.title + "](<" + track.info.uri + ">)"
    }

    fun timeFormat(seconds: Long): String {
        return SimpleDateFormat("HH:mm:ss").format(Date(seconds))
    }

    fun sendPM(user: User, message: Message?) {
        user.openPrivateChannel()
            .flatMap { x: PrivateChannel ->
                x.sendMessage(
                    message!!
                )
            }
            .queue(
                { s: Message? -> }
            ) { e: Throwable? -> }
    }

    fun sendPM(user: User, sequence: CharSequence?) {
        user.openPrivateChannel()
            .flatMap { x: PrivateChannel ->
                x.sendMessage(
                    sequence!!
                )
            }
            .queue(
                { s: Message? -> }
            ) { e: Throwable? -> }
    }

    fun sendPM(user: User, embed: MessageEmbed?) {
        user.openPrivateChannel()
            .flatMap { x: PrivateChannel ->
                x.sendMessageEmbeds(
                    embed!!
                )
            }
            .queue(
                { s: Message? -> }
            ) { e: Throwable? -> }
    }


}