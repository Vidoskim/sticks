package ru.vidoskim.sticks.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.vidoskim.sticks.model.SticksUser;

import java.util.Map;

@RequiredArgsConstructor
public class QuitListener implements Listener {
    private final Map<String, SticksUser> userMap;

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        userMap.remove(event.getPlayer().getName());
    }
}
