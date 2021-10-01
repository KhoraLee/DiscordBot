package me.khora.eris.command

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

interface ISlashCommand {
    val command: String
    val alias: MutableList<String> get() = mutableListOf<String>()
    val commandData: CommandData
    fun handle(event: SlashCommandEvent)
}