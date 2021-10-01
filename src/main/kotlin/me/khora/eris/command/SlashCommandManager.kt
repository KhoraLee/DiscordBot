package me.khora.eris.command

import me.khora.eris.command.audio.*
import net.dv8tion.jda.api.JDA

class SlashCommandManager {
    private val commands = mutableListOf<ISlashCommand>()
    private val cmdNames = mutableListOf<String>()

    init {
        addCommand(PingCommand)
        addCommand(PruneCommand)

        // Music related
        addCommand(PlayCommand)
        addCommand(PauseCommand)
        addCommand(ResumeCommand)
        addCommand(SkipCommand)
        addCommand(RepeatCommand)
        addCommand(QueueCommand)
    }

    private fun addCommand(com: ISlashCommand) {
        commands.add(com)
        cmdNames.add(com.command)
    }

    fun getCommand(input: String): ISlashCommand? {
        for (cmd in commands) {
            if (cmd.command == input || cmd.alias.contains(input)) {
                return cmd
            }
        }
        return null
    }

    fun update(jda: JDA) {
        val cmds = jda.updateCommands()
        commands.forEach { cmd ->
            cmds.addCommands(cmd.commandData)
        }
        cmds.queue()
    }
}
