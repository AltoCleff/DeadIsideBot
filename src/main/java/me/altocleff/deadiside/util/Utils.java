package me.altocleff.deadiside.util;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Utils {
    public static boolean UserCheck(MessageReceivedEvent event) {
        return !(event.getAuthor().isBot() || event.isWebhookMessage());
    }

}
