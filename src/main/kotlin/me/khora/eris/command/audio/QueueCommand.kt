package me.khora.eris.command.audio

import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import me.khora.eris.Utils.EmbedUtil
import me.khora.eris.Utils.Util
import me.khora.eris.audio.AudioManager
import me.khora.eris.audio.FormatUtil.filter
import me.khora.eris.audio.FormatUtil.formatTime
import me.khora.eris.audio.GuildAudioPlayer
import me.khora.eris.command.ISlashCommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import org.slf4j.LoggerFactory
import kotlin.math.ceil

object QueueCommand: ISlashCommand {
    override val command = "queue"
    override val commandData =
        CommandData(command,"Shows the current queue")
            .addOptions(
                OptionData(OptionType.INTEGER,"page","Page number")
                    .setRequired(false)
            )

    override fun handle(event: SlashCommandEvent) {
        event.deferReply().queue()
        var page = event.getOption("page")?.asLong?.toInt()
        if (page == null) page = 1
        val guildAudioPlayer = AudioManager.getAudioPlayer(event.guild!!.idLong)!!
        val player = guildAudioPlayer.player!!
        val queue = guildAudioPlayer.scheduler.queue
        if (queue.isEmpty()) {
            if (player.playingTrack != null) {
                if (guildAudioPlayer.scheduler.isRepeat){
                    event.hook.sendMessageEmbeds(EmbedUtil.successEmbed("\uD83D\uDD01 **Currently playing**: " + Util.titleMarkdown(player.playingTrack))).queue()
                } else {
                    event.hook.sendMessageEmbeds(EmbedUtil.defaultEmbed("**Currently playing**: " + Util.titleMarkdown(player.playingTrack))).queue()
                }
            } else {
                event.hook.sendMessageEmbeds(EmbedUtil.errorEmbed("There is no music Playing!")).queue()
            }
            return
        }
        val tracks = queue.stream().map { x: AudioTrack ->
            "`[" + formatTime(x.duration) + "]` " + Util.titleMarkdown(x) + " (<@" + x.userData as Long + ">)"
        }.toArray()

        val pages = ceil(tracks.size.toDouble() / 10).toInt()
        if (page > pages) page = pages
        val start = if (page == 1) 0 else (page - 1) * 10
        val end: Int = Integer.min(tracks.size, page * 10)
        val sb = StringBuilder()
        for (i in start until end) {
            sb.append("`" + (i + 1) + ".` ").append(tracks[i]).append("\n")
        }
        val embed = EmbedBuilder()
            .setTitle(getQueueTitle(guildAudioPlayer))
            .setFooter("Page $page/$pages")
            .setColor(0x452350)
            .setDescription(sb.toString().trim { it <= ' ' })
            .build()
        event.hook.sendMessageEmbeds(embed).queue()
    }

    private fun getQueueTitle(player: GuildAudioPlayer): String {
        val sb = StringBuilder()
        if (player.player.playingTrack != null) {
            sb.append(if (player.player.isPaused) "\u23f8" else "\u25b6")
                .append(if (player.scheduler.isRepeat) "\uD83D\uDD01" else "").append(" ")
                .append(Util.titleMarkdown(player.player.playingTrack)).append("\n")
        }
        val entries = player.scheduler.queue.size
        var length: Long = 0
        for (track in player.scheduler.queue) {
            length += track.duration
        }
        return filter(
            sb.append(entries).append(if (entries == 1) " entry | `" else " entries | `").append(formatTime(length))
                .append("`").toString()
        )
    }
}