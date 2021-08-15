package org.fuchss.devbot.commands

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.EventListener
import org.fuchss.devbot.Configuration

/**
 * Base class for my commands
 */
abstract class Command(configuration: Configuration, private val commandName: String) : EventListener {
    protected val config = configuration

    final override fun onEvent(event: GenericEvent) {
        if (event !is MessageReceivedEvent)
            return
        if (!event.message.contentRaw.startsWith("${config.command}${commandName}"))
            return

        this.onCommand(event)

        event.message.delete().complete()
    }

    abstract fun onCommand(event: MessageReceivedEvent)

}