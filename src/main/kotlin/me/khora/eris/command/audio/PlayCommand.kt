package me.khora.eris.command.audio

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import me.khora.eris.Utils.EmbedUtil
import me.khora.eris.Utils.Util
import me.khora.eris.audio.AudioManager
import me.khora.eris.audio.FormatUtil.formatTime
import me.khora.eris.audio.MusicUtil.getAddedToQueueMessage
import me.khora.eris.command.ISlashCommand
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import java.util.function.Consumer


object PlayCommand : ISlashCommand {
    override val command = "play"
    override val commandData =
        CommandData(command, "Plays a song")
            .addOptions(
                OptionData(OptionType.STRING, "song", "Songs that you want to play")
                    .setRequired(true)
            )

    override fun handle(event: SlashCommandEvent) {

        var member = event.member!!
        var guild = event.guild!!
        var voiceState = member.voiceState!!
        var manager = guild.audioManager
        if (manager.connectedChannel == null) {
            manager.openAudioConnection(voiceState.channel)
        }
        var guildAudioPlayer = AudioManager.getAudioPlayer(guild.idLong)!!
        if (manager.sendingHandler == null)
            manager.sendingHandler = guildAudioPlayer.getSendHandler()

        var query = event.getOption("song")!!.asString.replace("[", "").replace("]", "").replace(",", " ")
        query = if (query.startsWith("http://") || query.startsWith("https://")) query else "ytsearch:$query"

        AudioManager.getPlayerManager().loadItemOrdered(guildAudioPlayer, query, object : AudioLoadResultHandler {
            override fun trackLoaded(track: AudioTrack) {
                track.userData = event.member!!.idLong
                event.replyEmbeds(getAddedToQueueMessage(guildAudioPlayer, track)).queue()
                guildAudioPlayer.scheduler.queue(track)
            }

            override fun playlistLoaded(playlist: AudioPlaylist) {
                playlist.tracks.forEach {

                }
                if (playlist.tracks.size == 1 || playlist.isSearchResult) {
                    val single = if (playlist.selectedTrack == null) playlist.tracks[0] else playlist.selectedTrack
                    single.userData = event.member!!.idLong
                    event.replyEmbeds(getAddedToQueueMessage(guildAudioPlayer, single)).queue()
                    guildAudioPlayer.scheduler.queue(single)
                    return
                }
                var amount = "Added `" + playlist.tracks.size + "` tracks! (`" + formatTime(
                    playlist.tracks.stream().map { obj: AudioTrack -> obj.duration }
                        .reduce(0L) { a: Long, b: Long -> a + b }) + "`)"
                if (guildAudioPlayer.player.getPlayingTrack() == null) {
                    amount += "**Now playing**: ${Util.titleMarkdown(playlist.tracks[0])}".trimIndent()
                }
                event.replyEmbeds(EmbedUtil.defaultEmbed(amount)).queue()
                playlist.tracks.forEach(Consumer { track: AudioTrack ->
                    track.userData = event.member!!.idLong
                    guildAudioPlayer.scheduler.queue(track)
                })
            }

            override fun noMatches() {
                event.replyEmbeds(EmbedUtil.errorEmbed("Sorry, i couldn't find anything!")).queue()
            }

            override fun loadFailed(exception: FriendlyException) {
                event.replyEmbeds(
                    EmbedUtil.errorEmbed(
                        """
                                An error occurred while loading a track!
                                `${exception.message}`
                            """.trimIndent()
                    )
                ).queue()
            }
        })


    }
}