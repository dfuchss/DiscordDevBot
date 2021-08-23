package org.fuchss.devbot.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.fuchss.devbot.Configuration
import java.util.concurrent.TimeUnit

/**
 * The help command that shows all command
 */
class HelpCommand(configuration: Configuration) : Command(configuration, "help") {
    override fun onCommand(event: MessageReceivedEvent) {
        val helpMessage = """Current Commands:
            |**${config.command}help**: Shows this message
            |**${config.command}meta [channel, user, role]**: Shows meta information about the mentioned elements
        """.trimMargin()

        val embed = EmbedBuilder().setTitle("${event.jda.selfUser.name} Help").setDescription(helpMessage).build()
        val msg = event.message.channel.sendMessageEmbeds(embed).complete()
        msg.delete().queueAfter(20, TimeUnit.SECONDS)
    }
}