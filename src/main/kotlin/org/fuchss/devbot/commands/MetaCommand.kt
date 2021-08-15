package org.fuchss.devbot.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.fuchss.devbot.Configuration
import org.fuchss.devbot.Constants
import java.awt.Color
import java.util.concurrent.TimeUnit

/**
 * The Meta command shows meta information for mentioned elements.
 */
class MetaCommand(configuration: Configuration) : Command(configuration, "meta") {
    override fun onCommand(event: MessageReceivedEvent) {
        var anyMention = false

        anyMention = anyMention or provideMeta(event.channel, event.message.mentionedChannels)
        anyMention = anyMention or provideMeta(event.channel, event.message.mentionedMembers)
        anyMention = anyMention or provideMeta(event.channel, event.message.mentionedRoles)

        if (!anyMention) {
            val msg = event.message.channel.sendMessage("I need a mention to get meta information").complete()
            msg.delete().queueAfter(10, TimeUnit.SECONDS)
        }
    }

    @JvmName("provideMetaRoles")
    private fun provideMeta(channel: MessageChannel, mentionedRoles: List<Role>): Boolean {
        if (mentionedRoles.isEmpty())
            return false

        var meta = ""
        for (r in mentionedRoles) {
            meta += "Role ${r.asMention}\nID: ${r.id}\nPermissions: ${r.permissions}\n"
        }

        sendAndDelete(channel, "Meta for Roles", meta)
        return true
    }


    @JvmName("provideMetaMembers")
    private fun provideMeta(channel: MessageChannel, mentionedMembers: List<Member>): Boolean {
        if (mentionedMembers.isEmpty())
            return false

        var meta = ""
        for (m in mentionedMembers) {
            meta += "Member ${m.asMention}\nID: ${m.id}\nPermissions: ${m.permissions}\nRoles: ${m.roles}\n"
        }

        sendAndDelete(channel, "Meta for Members", meta)

        return true
    }

    @JvmName("provideMetaChannels")
    private fun provideMeta(channel: MessageChannel, mentionedChannels: List<TextChannel>): Boolean {
        if (mentionedChannels.isEmpty())
            return false

        var meta = ""
        for (c in mentionedChannels) {
            meta += "Channel ${c.asMention}\nID: ${c}\n"
        }

        sendAndDelete(channel, "Meta for Channels", meta)

        return true
    }

    private fun sendAndDelete(channel: MessageChannel, title: String, text: String) {
        val embed =
            EmbedBuilder().setTitle(title).setDescription(text.trim()).setColor(Constants.BLUE).build()
        val msg = channel.sendMessageEmbeds(embed).complete()
        msg.delete().queueAfter(30, TimeUnit.SECONDS)
    }
}