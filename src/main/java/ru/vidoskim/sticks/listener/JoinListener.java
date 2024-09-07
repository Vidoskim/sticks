package ru.vidoskim.sticks.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.vidoskim.sticks.model.SticksUser;
import ru.vidoskim.sticks.service.SticksUserService;

import java.util.Map;

@RequiredArgsConstructor
public class JoinListener implements Listener {
    private final SticksUserService sticksUserService;
    private final Map<String, SticksUser> userMap;

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        String username = event.getPlayer().getName();
        userMap.put(username, sticksUserService.getUser(username));
    }
}
