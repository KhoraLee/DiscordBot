package me.khora.eris.command

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

object PingCommand: ISlashCommand {
    override val command = "ping"
    override val commandData =
        CommandData(command,"Shows the current ping from the bot to the discord servers")

    override fun handle(event: SlashCommandEvent) {
        val time = System.currentTimeMillis()
        event.deferReply().queue {
            it.sendMessage("Pong: ${System.currentTimeMillis() - time} ms").queue()
        }

//        event.reply("Pong!")
//            .queue { it.editOriginal("Pong: ${System.currentTimeMillis() - time} ms").queue() }
    }

}