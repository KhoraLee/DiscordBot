package me.khora.eris.audio

import me.khora.eris.Utils.Util
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed

object MusicUtil {
    fun getStageTopicString(track: AudioTrack?): String {
        return if (track == null) "Queue is empty!" else "Now playing: " + track.info.title + " by " + track.info.author
    }

    fun getAddedToQueueMessage(player: GuildAudioPlayer, track: AudioTrack): MessageEmbed {
        val audioPlayer: AudioPlayer = player.player
        val builder: EmbedBuilder = EmbedBuilder()
            .setColor(0x452350)
        if (audioPlayer.playingTrack == null) {
            builder.setDescription(
                "**Now playing**: " + Util.titleMarkdown(track) + " (`" + FormatUtil.formatTime(track.duration) + "`)"
            )
        } else {
            builder.setDescription(
                "**Added to queue**: " + Util.titleMarkdown(track) + " (`" + FormatUtil.formatTime(track.duration) + "`)"
            )
        }
        if (track is YoutubeAudioTrack) {
            builder.setThumbnail("https://img.youtube.com/vi/" + track.getIdentifier() + "/mqdefault.jpg")
        }
        val author: String = track.info.author
        if (author != null) builder.setFooter("Source: $author")
        return builder.build()
    }
}