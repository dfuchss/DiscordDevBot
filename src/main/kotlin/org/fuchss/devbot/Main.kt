package org.fuchss.devbot

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.EventListener
import org.fuchss.devbot.commands.HelpCommand
import org.fuchss.devbot.commands.MetaCommand
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Change the presence to something important.
 */
class StatusListener(configuration: Configuration) : EventListener {
    private val config = configuration

    override fun onEvent(event: GenericEvent) {
        if (event !is ReadyEvent)
            return

        event.jda.presence.setPresence(OnlineStatus.ONLINE, Activity.listening("${config.command}help"))
    }
}

class LoggerListener : EventListener {
    private val logger: Logger = LoggerFactory.getLogger("DevBot")

    override fun onEvent(event: GenericEvent) {
        if (event !is MessageReceivedEvent)
            return

        logger.info("${event.message}")
    }

}

fun main() {
    val config = Configuration()

    val token = System.getenv("DISCORD_TOKEN") ?: error("DISCORD_TOKEN not set")
    val builder = JDABuilder.createDefault(token)
    val jda = builder.addEventListeners(LoggerListener(), StatusListener(config), HelpCommand(config), MetaCommand(config)).build()
    jda.awaitReady()
}
