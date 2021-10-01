package me.khora.eris.command.audio

import me.khora.eris.audio.AudioManager
import me.khora.eris.command.ISlashCommand
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

object VolumeCommand: ISlashCommand {
    override val command = "volume"
    override val commandData =
        CommandData(command,"Change the volume of the bot")

    override fun handle(event: SlashCommandEvent) {
        var guildAudioPlayer = AudioManager.getAudioPlayer(event.guild!!.idLong)!!
        if (guildAudioPlayer.scheduler.isRepeat) {
            guildAudioPlayer.scheduler.isRepeat = false
        }
        if (guildAudioPlayer.player.playingTrack == null){
            event.reply("There is no music to skip!").queue()
        }
        guildAudioPlayer.scheduler.nextTrack()
        val next = guildAudioPlayer.player.playingTrack
        if (next == null) {
            event.reply("**Skipped!**").queue()
        } else {
            event.reply("**Skipped!** Now playing: `${next.info.title}`").queue()
        }
    }
}