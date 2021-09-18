package com.wyvencraft.api.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class Command implements CommandExecutor {

    public final List<SubCommand> subCommands;

    public Command(SubCommand... subCommands) {
        this.subCommands = Arrays.asList(subCommands);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command cmd, @NotNull String label, String[] args) {
        // If there are no arguments send help list by default
        if (args.length == 0) {
            SubCommand helpCmd = subCommands.get(0);
            helpCmd.command(sender, new String[0]);
            return true;
        }

        Optional<SubCommand> optionalSubCommand = subCommands.stream().filter(subCommand -> {
            if (args.length >= subCommand.getMinArgs()) {
                return args[0].equalsIgnoreCase(subCommand.getName());
            }
            return false;
        }).findFirst();

        optionalSubCommand.ifPresent(sub -> sub.command(sender, getSubArgs(args)));
        return true;
    }

    // args = ["give", "yeet", "craft"]
    // removes the subcommand from the args array
    public String[] getSubArgs(String[] args) {
        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, subArgs.length);
        return subArgs;
    }
}
