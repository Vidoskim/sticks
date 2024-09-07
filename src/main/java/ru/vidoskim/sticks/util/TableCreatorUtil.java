package ru.vidoskim.sticks.util;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.experimental.UtilityClass;
import ru.vidoskim.sticks.model.SticksUser;

import java.sql.SQLException;

@UtilityClass
public class TableCreatorUtil {
    public void create(JdbcPooledConnectionSource connectionSource) throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, SticksUser.class);
    }
}