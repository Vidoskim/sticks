package ru.vidoskim.sticks.service.impl;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import ru.vidoskim.sticks.Sticks;
import ru.vidoskim.sticks.dao.SticksUserDao;
import ru.vidoskim.sticks.listener.JoinListener;
import ru.vidoskim.sticks.listener.PreJoinListener;
import ru.vidoskim.sticks.listener.QuitListener;
import ru.vidoskim.sticks.model.SticksUser;
import ru.vidoskim.sticks.service.SticksUserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class SticksUserServiceImpl implements SticksUserService {
    private final Map<String, SticksUser> userMap;
    private final SticksUserDao sticksUserDao;
    private final Sticks plugin;

    @Override
    public void enable() {
        Bukkit.getServer().getPluginManager()
                .registerEvents(new PreJoinListener(this), plugin);
        Bukkit.getServer().getPluginManager()
                .registerEvents(new JoinListener(this, userMap), plugin);
        Bukkit.getServer().getPluginManager()
                .registerEvents(new QuitListener(userMap), plugin);
    }

    @Override
    public SticksUser save(SticksUser user) {
        try {
            return sticksUserDao.save(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(List<SticksUser> users) {
        try {
            sticksUserDao.saveAll(users);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<SticksUser> findById(Long id) {
        try {
            return sticksUserDao.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<SticksUser> findByUsername(String username) {
        try {
            return sticksUserDao.findByUsername(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SticksUser> findAll() {
        try {
            return sticksUserDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SticksUser getUser(String username) {
        return findByUsername(username).orElseGet(() -> SticksUser.builder()
                .name(username)
                .isSticksEnabled(true)
                .build());
    }
}
