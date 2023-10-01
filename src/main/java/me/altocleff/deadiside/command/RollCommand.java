package me.altocleff.deadiside.command;

import lombok.RequiredArgsConstructor;
import me.altocleff.deadiside.exception.DiceTypeException;
import me.altocleff.deadiside.exception.MessageBuildException;
import me.altocleff.deadiside.util.DiceCollection;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class RollCommand implements ICommand{

    private final List<DiceCollection> diceCollections;
    @Override
    public void handle(MessageReceivedEvent event, List<String> args) throws MessageBuildException, DiceTypeException {
        Pattern pattern = Pattern.compile("(\\d+)([a-zA-Z])(.*)");
        MessageCreateBuilder messageBuilder = new MessageCreateBuilder();
        for (String arg : args) {
            Matcher matcher = pattern.matcher(arg);
            if (matcher.find()) {
                diceGenerator(messageBuilder, Integer.parseInt(matcher.group(1)), matcher.group(2), matcher.group(3));
            }
        }
        if (messageBuilder.isEmpty()) {
            throw new MessageBuildException("Error in command arguments!");
        }
        event.getChannel().sendMessage(messageBuilder.build()).queue();

    }

    private void diceGenerator(MessageCreateBuilder builder ,int value, String colour, String diceType) throws DiceTypeException {
        if (diceType.equals("")) {diceType = "d6";}

        String finalDiceType = diceType;

        Optional<DiceCollection> foundDice = diceCollections.stream()
                .filter(d -> d.getDiceType().equalsIgnoreCase(finalDiceType))
                .findFirst();

        if (foundDice.isPresent()) {
            DiceCollection dice = foundDice.get();
            for (int i = 0; i < value; i++) {
                int randomNum = ThreadLocalRandom.current().nextInt(1, dice.getEdges() + 1);
                if(dice.getDiceMap().get(colour + randomNum) != null) {
                    builder.addContent(dice.getDiceMap().get(colour + randomNum));
                }
            }
        } else {
            throw new DiceTypeException("Wrong dice type!");
        }
    }

    @Override
    public String getName() {
        return "roll";
    }
}
