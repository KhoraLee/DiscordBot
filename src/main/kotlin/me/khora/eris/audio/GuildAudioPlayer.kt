package me.khora.eris.audio

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager

class GuildAudioPlayer(manager: AudioPlayerManager, val guildId: Long) {
    val player: AudioPlayer
    val scheduler: AudioScheduler

    fun getSendHandler(): AudioPlayerSendHandler? {
        return AudioPlayerSendHandler(player)
    }

    init {
        player = manager.createPlayer()
        scheduler = AudioScheduler(player, guildId)
        player.addListener(scheduler)
    }
}