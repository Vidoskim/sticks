package ru.vidoskim.sticks.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import ru.vidoskim.sticks.model.SticksUser;
import ru.vidoskim.sticks.service.SticksUserService;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Command(name = "sticks")
public class SticksCommand {
    private final SticksUserService sticksUserService;
    private final Map<String, SticksUser> userMap;
    private final FileConfiguration config;
    private final MiniMessage miniMessage;

    @Async
    @Execute
    @Permission("sticks.change")
    void change(@Context Player sender) {
        changeSticks(sender, userMap.get(sender.getName()));
    }

    @Async
    @Execute
    @Permission("sticks.change.admin")
    void change(@Context CommandSender sender, @Arg("игрок") Player target) {
        changeSticks(sender, userMap.get(target.getName()));
    }

    @Async
    @Execute(name = "on")
    @Permission("sticks.change.on")
    void on(@Context Player sender) {
        SticksUser user = userMap.get(sender.getName());
        user.setSticksEnabled(true);
        sticksUserService.save(user);
        sender.sendMessage(miniMessage.deserialize(
                Objects.requireNonNull(config.getString("language.commands.sticks.on"))));
    }

    @Async
    @Execute(name = "off")
    @Permission("sticks.change.off")
    void off(@Context Player sender) {
        SticksUser user = userMap.get(sender.getName());
        user.setSticksEnabled(false);
        sticksUserService.save(user);
        sender.sendMessage(miniMessage.deserialize(
                Objects.requireNonNull(config.getString("language.commands.sticks.off"))));
    }

    @Async
    @Execute(name = "on")
    @Permission("sticks.change.on.admin")
    void on(@Context Player sender, @Arg("игрок") Player target) {
        SticksUser user = userMap.get(target.getName());
        user.setSticksEnabled(true);
        sticksUserService.save(user);
        sender.sendMessage(miniMessage.deserialize(
                Objects.requireNonNull(config.getString("language.commands.sticks.on"))));
    }

    @Async
    @Execute(name = "off")
    @Permission("sticks.change.off")
    void off(@Context Player sender, @Arg("игрок") Player target) {
        SticksUser user = userMap.get(target.getName());
        user.setSticksEnabled(false);
        sticksUserService.save(user);
        sender.sendMessage(miniMessage.deserialize(
                Objects.requireNonNull(config.getString("language.commands.sticks.off"))));
    }

    private void changeSticks(CommandSender sender, SticksUser user) {
        if(user.isSticksEnabled()) {
            user.setSticksEnabled(false);
            sender.sendMessage(miniMessage.deserialize(
                    Objects.requireNonNull(config.getString("language.commands.sticks.change-off"))));
        } else {
            user.setSticksEnabled(true);
            sender.sendMessage(miniMessage.deserialize(
                    Objects.requireNonNull(config.getString("language.commands.sticks.change-on"))));
        }
        sticksUserService.save(user);
    }
}
