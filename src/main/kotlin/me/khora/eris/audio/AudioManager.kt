package me.khora.eris.audio

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import java.util.concurrent.ConcurrentHashMap

class AudioManager {

    fun getLastNPMessage(guildId: Long): CachedMessage? {
        return nowPlayingMessages[guildId]
    }

    fun setLastNPMessage(guildId: Long, message: Message?) {
        nowPlayingMessages[guildId] = CachedMessage(message!!)
    }

    companion object {
        private var playerManager: AudioPlayerManager? = null
        private var audioPlayers: MutableMap<Long, GuildAudioPlayer>? = null
        private val nowPlayingMessages: MutableMap<Long, CachedMessage> = HashMap()

        @Synchronized
        fun getAudioPlayer(guildId: Long): GuildAudioPlayer? {
            if (audioPlayers == null) {
                AudioManager()
            }
            if (audioPlayers!!.containsKey(guildId)) return audioPlayers!![guildId]
            val player = GuildAudioPlayer(playerManager!!, guildId)
            audioPlayers!![guildId] = player
            return player
        }

        fun getPlayerManager(): AudioPlayerManager {
            return playerManager!!
        }
    }

    init {
        playerManager = DefaultAudioPlayerManager()
        audioPlayers = ConcurrentHashMap<Long, GuildAudioPlayer>()
        AudioSourceManagers.registerRemoteSources(playerManager)
    }
}