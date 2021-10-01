package me.khora.eris.Utils

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color

object EmbedUtil {
    const val DEFAULT_COLOR = 0x452350
    const val SUCCESS_COLOR = 0x3EB489
    const val ERROR_COLOR = 0x790604
    const val WARNING_COLOR = 0xFFEA17
    const val SUCCESS_UNICODE = "✅"
    const val ERROR_UNICODE = "❌"
    const val WARNING_UNICODE = "⚠"
    const val NO_ENTRY_UNICODE = "⛔"

    fun defaultEmbed(content: String?, color: Color?): MessageEmbed {
        return EmbedBuilder()
            .setDescription(content)
            .setColor(color)
            .build()
    }

    fun defaultEmbed(content: String?, color: Int): MessageEmbed {
        return EmbedBuilder()
            .setDescription(content)
            .setColor(color)
            .build()
    }

    fun defaultEmbed(content: String?, r: Byte, g: Byte, b: Byte): MessageEmbed {
        return EmbedBuilder()
            .setDescription(content)
            .setColor(rgbToInt(r, g, b))
            .build()
    }

    fun defaultEmbed(content: String?): MessageEmbed {
        return EmbedBuilder()
            .setDescription(content)
            .setColor(DEFAULT_COLOR)
            .build()
    }

    fun errorEmbed(content: String): MessageEmbed {
        return EmbedBuilder()
            .setDescription(ERROR_UNICODE + " " + content)
            .setColor(ERROR_COLOR)
            .build()
    }

    fun warningEmbed(content: String): MessageEmbed {
        return EmbedBuilder()
            .setDescription(WARNING_UNICODE + " " + content)
            .setColor(WARNING_COLOR)
            .build()
    }

    fun successEmbed(content: String): MessageEmbed {
        return EmbedBuilder()
            .setDescription(SUCCESS_UNICODE + " " + content)
            .setColor(SUCCESS_COLOR)
            .build()
    }

    fun noEntryEmbed(content: String): MessageEmbed {
        return EmbedBuilder()
            .setDescription(NO_ENTRY_UNICODE + " " + content)
            .setColor(ERROR_COLOR)
            .build()
    }

    fun errorEmbedBuilder(content: String): EmbedBuilder {
        return EmbedBuilder()
            .setDescription(ERROR_UNICODE + " " + content)
            .setColor(ERROR_COLOR)
    }

    fun warningEmbedBuilder(content: String): EmbedBuilder {
        return EmbedBuilder()
            .setDescription(WARNING_UNICODE + " " + content)
            .setColor(WARNING_COLOR)
    }

    fun successEmbedBuilder(content: String): EmbedBuilder {
        return EmbedBuilder()
            .setDescription(SUCCESS_UNICODE + " " + content)
            .setColor(SUCCESS_COLOR)
    }

    fun noEntryEmbedBuilder(content: String): EmbedBuilder {
        return EmbedBuilder()
            .setDescription(NO_ENTRY_UNICODE + " " + content)
            .setColor(ERROR_COLOR)
    }

    fun rgbToInt(r: Byte, g: Byte, b: Byte): Int {
        var rgb = r.toInt()
        rgb = (rgb shl 8) + g
        rgb = (rgb shl 8) + b
        return rgb
    }

    fun intToColor(rgb: Int): Color {
        val red = rgb shr 16 and 0xFF
        val green = rgb shr 8 and 0xFF
        val blue = rgb and 0xFF
        return Color(red, green, blue)
    }
}
