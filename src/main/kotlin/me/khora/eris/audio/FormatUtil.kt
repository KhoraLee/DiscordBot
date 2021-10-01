package me.khora.eris.audio

import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.VoiceChannel

object FormatUtil {
    fun formatTime(duration: Long): String {
        if (duration == Long.MAX_VALUE) {
            return "LIVE"
        }
        var seconds = Math.round(duration / 1000.0)
        val hours = seconds / 3600L
        seconds %= 3600L
        val minutes = seconds / 60L
        seconds %= 60L

        var out = ""

        out += if (hours > 0L) "$hours:" else ""
        out += if (minutes < 10L) "0$minutes" else java.lang.Long.valueOf(minutes)
        out += ":"
        out += if (seconds < 10L) "0$seconds" else java.lang.Long.valueOf(seconds)

        return out
    }

    fun progressBar(percent: Double): String {
        var str = ""
        for (i in 0..11) {
            str += if (i == (percent * 12.0).toInt()) {
                "\ud83d\udd18"
            } else {
                "\u25ac"
            }
        }
        return str
    }

    fun volumeIcon(volume: Int): String {
        if (volume == 0) {
            return "\ud83d\udd07"
        }
        if (volume < 30) {
            return "\ud83d\udd08"
        }
        return if (volume < 70) {
            "\ud83d\udd09"
        } else "\ud83d\udd0a"
    }

    fun listOfTChannels(list: List<TextChannel>, query: String): String {
        var out = " Multiple text channels found matching \"$query\":"
        var i = 0
        while (i < 6 && i < list.size) {
            out += "\n - ${list[i].name} (<#${list[i].id}>)";
        }
        if (list.size > 6) {
            out += "\n**And ${list.size - 6} more...**";
        }
        return out
    }

    fun listOfVChannels(list: List<VoiceChannel>, query: String): String {
        var out = " Multiple voice channels found matching \"$query\":"
        var i = 0
        while (i < 6 && i < list.size) {
            out += "\n - ${list[i].name} (<#${list[i].id}>)";
        }
        if (list.size > 6) {
            out += "\n**And ${list.size - 6} more...**";
        }
        return out
    }

    fun listOfRoles(list: List<Role>, query: String): String {
        var out = " Multiple text channels found matching \"$query\":"
        var i = 0
        while (i < 6 && i < list.size) {
            out += "\n - ${list[i].name} (<#${list[i].id}>)";
        }
        if (list.size > 6) {
            out += "\n**And ${list.size - 6} more...**";
        }
        return out
    }

    fun filter(input: String): String {
        return input.replace("\u202e", "").replace("@everyone", "@\u0435veryone").replace("@here", "@h\u0435re")
            .trim { it <= ' ' }
    }
}