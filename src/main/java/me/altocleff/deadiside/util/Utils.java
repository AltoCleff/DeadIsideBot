package me.altocleff.deadiside.util;

import me.altocleff.deadiside.config.AppConfig;
import me.altocleff.deadiside.config.LavaPlayerConfig;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Utils {

    public static final AnnotationConfigApplicationContext APP_CONTEXT = new AnnotationConfigApplicationContext(AppConfig.class);
    public static final AnnotationConfigApplicationContext LAVA_CONTEXT = new AnnotationConfigApplicationContext(LavaPlayerConfig.class);
    public static boolean UserCheck(MessageReceivedEvent event) {
        return !(event.getAuthor().isBot() || event.isWebhookMessage());
    }

    public static boolean UserInVoiceChanel(MessageReceivedEvent event) {
        GuildVoiceState guildVoiceState = event.getMember().getVoiceState();
        return guildVoiceState.inAudioChannel();
    }
    public static void ReplyToMessage(MessageReceivedEvent event, MessageCreateData message) {
        event.getMessage().reply(message).queue();
    }

    public static void ReplyToMessage(MessageReceivedEvent event, MessageEmbed...embeds) {
        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
        messageCreateBuilder.setEmbeds(embeds);
        event.getMessage().reply(messageCreateBuilder.build()).queue();
    }



}
