package org.example;

//canvas create (name) [x] [y]
//canvas delete (name)
//canvas reset (name)


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.example.models.Canvas;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CanvasCommands implements TabExecutor {
    private Main plugin;

    public CanvasCommands() {
        plugin = Main.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return true;
        }
        if(!((Player) commandSender).isOp()) {
            return true;
        }
        if (strings.length < 2) {
            commandSender.sendMessage("Zadej příkaz ve tvaru /canvas (create|delete|reset|enable|disable) (name)");
            return true;
        }
        if(strings[0].equalsIgnoreCase("create")) {
            if(strings.length == 4) {plugin.getController().createCanvas(commandSender.getName(), strings[1], strings[2], strings[3]); return true; }
            plugin.getController().createCanvas(commandSender.getName(), strings[1]);

        }
        if(strings[0].equalsIgnoreCase("delete")) {
            plugin.getManager().removeCanvas((Player) commandSender,strings[1]);
        }
        if(strings[0].equalsIgnoreCase("reset")) {
            plugin.getManager().resetCanvas((Player) commandSender,strings[1]);
        }
        if(strings[0].equalsIgnoreCase("enable")) {
            plugin.getManager().enableCanvas((Player) commandSender,strings[1]);
        }
        if(strings[0].equalsIgnoreCase("disable")) {
            plugin.getManager().disableCanvas((Player) commandSender,strings[1]);
        }


        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>(Arrays.asList("create","delete","reset","enable","disable"));
        if(strings.length == 1) { StringUtil.copyPartialMatches(strings[0], commands, completions);}
        if(strings.length == 2 && !strings[0].equalsIgnoreCase("create")) { StringUtil.copyPartialMatches(strings[1], plugin.getManager().getCanvases(), completions);}
        Collections.sort(completions);
        return completions;

    }
}
