package me.khora.eris.command.audio

import me.khora.eris.Utils.EmbedUtil
import me.khora.eris.audio.AudioManager
import me.khora.eris.command.ISlashCommand
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

object PauseCommand: ISlashCommand {
    override val command = "pause"

    override val commandData =
        CommandData(command,"Pause the currently playing song")

    override fun handle(event: SlashCommandEvent) {
        val guildAudioPlayer = AudioManager.getAudioPlayer(event.guild!!.idLong)!!
        val player = guildAudioPlayer.player
        if (player.playingTrack == null){
            event.reply("Currently no music is playing!").queue()
            return
        }
        if (player.isPaused) {
            event.replyEmbeds(EmbedUtil.errorEmbed("The player is already paused! Use `/resume`")).queue()
            return
        }
        player.isPaused = true
        event.replyEmbeds(EmbedUtil.successEmbed("The player is now paused! ")).queue()
    }
}