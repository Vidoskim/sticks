package ru.vidoskim.sticks.util;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import dev.rollczi.litecommands.schematic.SchematicFormat;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

@RequiredArgsConstructor
public class LiteCommandUtil {
    private final FileConfiguration config;

    @SuppressWarnings("all")
    public LiteCommands<CommandSender> create(Object... commands) {
        return LiteBukkitFactory.builder()
                .settings(settings -> settings
                        .fallbackPrefix("sticks")
                        .nativePermissions(true)
                )
                .commands(commands)
                .message(LiteBukkitMessages.INVALID_USAGE,
                        config.getString("language.commands.invalid-args-count"))
                .message(LiteBukkitMessages.PLAYER_ONLY,
                        config.getString("language.commands.only-for-players"))
                .message(LiteBukkitMessages.PLAYER_NOT_FOUND,
                        config.getString("language.commands.player-not-found"))
                .schematicGenerator(SchematicFormat.angleBrackets())
                .build();
    }
}