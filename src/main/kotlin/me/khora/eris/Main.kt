package me.khora.eris

import dev.minn.jda.ktx.injectKTX
import me.khora.eris.command.SlashCommandManager
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent.GUILD_MESSAGES
import net.dv8tion.jda.api.requests.GatewayIntent.GUILD_VOICE_STATES

fun main(args: Array<String>) {

    val slashCmdMgr = SlashCommandManager()
    val jda = JDABuilder.create(
        "Key",
        GUILD_MESSAGES, GUILD_VOICE_STATES
    )
        .addEventListeners(SlashCommandListener(slashCmdMgr))
        .build()

    slashCmdMgr.update(jda)
}
