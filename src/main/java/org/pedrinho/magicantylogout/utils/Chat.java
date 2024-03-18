package org.pedrinho.magicantylogout.utils;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class Chat {

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> colorList(List<String> lore){
        return lore.stream().map(Chat::color).collect(Collectors.toList());
    }
}
