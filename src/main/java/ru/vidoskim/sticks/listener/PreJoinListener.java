package ru.vidoskim.sticks.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import ru.vidoskim.sticks.model.SticksUser;
import ru.vidoskim.sticks.service.SticksUserService;

@RequiredArgsConstructor
public class PreJoinListener implements Listener {
    private final SticksUserService sticksUserService;

    @EventHandler
    private void onJoin(AsyncPlayerPreLoginEvent event) {
        String username = event.getName();

        if(username.isEmpty()) {
            return;
        }

        if(sticksUserService.findByUsername(username).isPresent()) {
            return;
        }

        SticksUser user = SticksUser.builder()
                .name(username)
                .isSticksEnabled(true)
                .build();
        sticksUserService.save(user);
    }
}
