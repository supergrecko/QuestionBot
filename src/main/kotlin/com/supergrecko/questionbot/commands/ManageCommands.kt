package com.supergrecko.questionbot.commands

import com.supergrecko.questionbot.arguments.QuestionArg
import com.supergrecko.questionbot.extensions.PermissionLevel
import com.supergrecko.questionbot.extensions.permission
import com.supergrecko.questionbot.services.ConfigService
import com.supergrecko.questionbot.services.LogService
import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.api.dsl.embed
import me.aberrantfox.kjdautils.internal.arguments.MessageArg
import me.aberrantfox.kjdautils.internal.arguments.RoleArg
import me.aberrantfox.kjdautils.internal.arguments.TextChannelArg
import me.aberrantfox.kjdautils.internal.arguments.WordArg
import java.awt.Color
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@CommandSet("manage")
fun manageCommands(config: ConfigService, logService: LogService) = commands {
    command("setrole") {
        description = "Set the lowest required role to invoke commands."
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(RoleArg)

        execute {
            logService.log(it)
            val (role) = it.args

            // TODO: Implement it
            it.respond(role.toString())
        }
    }

    command("setprefix") {
        description = "Sets the bot prefix."
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(WordArg)

        execute {
            logService.log(it)
            config.setPrefix(it.args.first() as String)
            config.save()

            it.respond(embed {
                color = Color(0xfb8c00)
                title = "Success!"
                description = "Bot Prefix has successfully been updated."

                addInlineField("New Prefix", it.args.first() as String)
                addInlineField("Invoked By", it.author.name)
                addInlineField("Date", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))
            })
        }
    }

    command("setlog") {
        description = "Sets the log channel."
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(TextChannelArg)

        execute {
            logService.log(it)
            config.setLogChannel(it.guild?.id!!, it.args.first() as String)
            config.save()

            it.respond(embed {
                color = Color(0xfb8c00)
                title = "Success!"
                description = "Log Channel has successfully been updated."

                addInlineField("New Channel", it.args.first() as String)
                addInlineField("Invoked By", it.author.name)
                addInlineField("Date", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))
            })
        }
    }

    command("delanswer") {
        description = "Delete an answer from a question."
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(MessageArg)

        execute {
            logService.log(it)

        }
    }

    command("addanswer") {
        description = "Manually add an already existing message as a reply to a question"
        requiresGuild = true
        permission = PermissionLevel.ADMIN

        expect(MessageArg, QuestionArg)

        execute {
            logService.log(it)
        }
    }
}
