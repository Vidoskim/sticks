package ru.vidoskim.sticks.dao;

import com.j256.ormlite.dao.Dao;
import ru.vidoskim.sticks.model.SticksUser;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public interface SticksUserDao extends Dao<SticksUser, Long> {
    SticksUser save(SticksUser user) throws SQLException;

    void saveAll(List<SticksUser> users) throws SQLException;

    Optional<SticksUser> findById(Long id) throws SQLException;

    Optional<SticksUser> findByUsername(String username) throws SQLException;

    List<SticksUser> findAll() throws SQLException;
}
