package me.khora.eris.command

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.components.Button

object PruneCommand: ISlashCommand {
    override val command = "prune"
    override val commandData =
        CommandData(command,"Shows the current ping from the bot to the discord servers")
            .addOptions(OptionData(OptionType.INTEGER, "amount", "How many messages to prune (Default 100)"))

    override fun handle(event: SlashCommandEvent) {
        if (event.member!!.hasPermission(Permission.MANAGE_CHANNEL)) {
            val amountOption = event.getOption("amount") // This is configured to be optional so check for null
            val amount = if (amountOption == null) 100 // default 100
            else Math.min(200, Math.max(2, amountOption.asLong)).toInt() // enforcement: must be between 2-200
            val userId = event.user.id
            event.reply("This will delete $amount messages.\nAre you sure?") // prompt the user with a button menu
                .addActionRow( // this means "<style>(<id>, <label>)" the id can be spoofed by the user so setup some kinda verification system
                    Button.secondary("$userId:delete", "Nevermind!"),
                    Button.danger("$userId:prune:$amount", "Yes!")
                ) // the first parameter is the component id we use in onButtonClick above
                .queue()
        } else {
            event.reply("You don't have enough permission to prune message.")
        }
    }

}