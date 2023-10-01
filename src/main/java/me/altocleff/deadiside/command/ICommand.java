package me.altocleff.deadiside.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public interface ICommand {

    void handle(MessageReceivedEvent event, List<String> args) throws RuntimeException;

    String getName();

    default List<String> getAliases(){ return List.of();}
}
