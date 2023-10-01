package me.altocleff.deadiside.util;

import lombok.Data;

import java.util.Map;

@Data
public class DiceCollection {
    private String diceType;

    private int edges;

    private Map<String, String> diceMap;
}
