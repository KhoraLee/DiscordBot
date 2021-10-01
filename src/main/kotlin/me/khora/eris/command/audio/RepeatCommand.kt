package me.khora.eris.command.audio

import me.khora.eris.Utils.EmbedUtil
import me.khora.eris.audio.AudioManager
import me.khora.eris.command.ISlashCommand
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

object RepeatCommand: ISlashCommand {
    override val command = "repeat"
    override val commandData =
        CommandData(command,"Loops the current queue/song")

    override fun handle(event: SlashCommandEvent) {
        val guildAudioPlayer = AudioManager.getAudioPlayer(event.guild!!.idLong)!!
        val player = guildAudioPlayer.player
        if (player.playingTrack == null){
            event.reply("Currently no music is playing!").queue()
        }
        if (!guildAudioPlayer.scheduler.isRepeat) {
            guildAudioPlayer.scheduler.isRepeat = true
            event.replyEmbeds(EmbedUtil.successEmbed("\uD83D\uDD01 Repeat mode turned **ON**\nUse this command again to turn it off.")).queue()
        } else {
            guildAudioPlayer.scheduler.isRepeat = false
            event.replyEmbeds(EmbedUtil.successEmbed("\uD83D\uDD01 Repeat mode turned **OFF**")).queue();
        }
    }
}