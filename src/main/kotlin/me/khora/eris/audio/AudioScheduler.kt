package me.khora.eris.audio

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue


class AudioScheduler(val player: AudioPlayer, val guildId: Long) : AudioEventAdapter() {
    val queue: BlockingQueue<AudioTrack>
    var isRepeat = false
    private var playingSong: AudioTrack? = null
    fun queue(track: AudioTrack) {
        if (!player.startTrack(track, true)) {
            queue.offer(track)
        }
    }

    fun nextTrack() {
        if (isRepeat) {
            player.startTrack(playingSong!!.makeClone(), false)
            return
        }
        val track = queue.poll()
        player.startTrack(track, false)
    }

    override fun onPlayerPause(player: AudioPlayer) {
        super.onPlayerPause(player)
    }

    override fun onPlayerResume(player: AudioPlayer) {
        super.onPlayerResume(player)
    }

    override fun onTrackStart(player: AudioPlayer, track: AudioTrack) {
        super.onTrackStart(player, track)
        playingSong = track
    }

    override fun onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason) {
        if (endReason.mayStartNext) nextTrack()
    }

    override fun onTrackException(player: AudioPlayer, track: AudioTrack, exception: FriendlyException) {
        super.onTrackException(player, track, exception)
    }

    override fun onTrackStuck(player: AudioPlayer, track: AudioTrack, thresholdMs: Long) {
        super.onTrackStuck(player, track, thresholdMs)
    }

    override fun onTrackStuck(
        player: AudioPlayer,
        track: AudioTrack,
        thresholdMs: Long,
        stackTrace: Array<StackTraceElement>
    ) {
        super.onTrackStuck(player, track, thresholdMs, stackTrace)
    }

    override fun onEvent(event: AudioEvent) {
        super.onEvent(event)
    }

    init {
        queue = LinkedBlockingQueue()
    }
}