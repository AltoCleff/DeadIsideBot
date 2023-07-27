package me.altocleff.deadiside.config;

import me.altocleff.deadiside.listener.DotaListener;
import me.altocleff.deadiside.listener.commandListener.CommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JDAConfig {

    @Value("${bot.token}")
    private String token;

    @Bean
    public JDA jda(){
        return JDABuilder.createDefault(token)
                .addEventListeners(commandListener())
                .addEventListeners(dotaListener())
                .build();
    }

    @Bean
    public CommandListener commandListener(){
        return new CommandListener();
    }

    @Bean
    public DotaListener dotaListener(){
        return new DotaListener();
    }

}
