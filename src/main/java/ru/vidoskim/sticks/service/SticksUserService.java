package ru.vidoskim.sticks.service;

import ru.vidoskim.sticks.model.SticksUser;

import java.util.List;
import java.util.Optional;

public interface SticksUserService extends Service {
    SticksUser save(SticksUser user);

    void saveAll(List<SticksUser> users);

    Optional<SticksUser> findById(Long id);

    Optional<SticksUser> findByUsername(String username);

    List<SticksUser> findAll();

    SticksUser getUser(String username);
}
