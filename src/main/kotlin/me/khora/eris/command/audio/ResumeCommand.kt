package me.khora.eris.command.audio

import me.khora.eris.Utils.EmbedUtil
import me.khora.eris.audio.AudioManager
import me.khora.eris.command.ISlashCommand
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

object ResumeCommand: ISlashCommand {
    override val command ="resume"
    override val commandData =
        CommandData(command, "Resumes the music")

    override fun handle(event: SlashCommandEvent) {
        val guildAudioPlayer = AudioManager.getAudioPlayer(event.guild!!.idLong)!!
        val player = guildAudioPlayer.player
        if (player.playingTrack == null) {
            event.reply("Currently no music is playing!").queue()
        }
        if (!player.isPaused) {
            event.replyEmbeds(EmbedUtil.errorEmbed("The player is not paused!")).queue()
            return
        }
        player.isPaused = false
        event.replyEmbeds(EmbedUtil.successEmbed("Resumed!")).queue()
    }
}