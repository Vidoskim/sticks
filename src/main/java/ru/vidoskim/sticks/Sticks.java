package ru.vidoskim.sticks;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.SneakyThrows;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.vidoskim.sticks.command.SticksCommand;
import ru.vidoskim.sticks.model.SticksUser;
import ru.vidoskim.sticks.service.Service;
import ru.vidoskim.sticks.service.SticksUserService;
import ru.vidoskim.sticks.service.impl.SticksUserServiceImpl;
import ru.vidoskim.sticks.util.DaoCreatorUtil;
import ru.vidoskim.sticks.util.LiteCommandUtil;
import ru.vidoskim.sticks.util.TableCreatorUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public final class Sticks extends JavaPlugin {
    private final Map<Class<?>, Object> serviceMap = new HashMap<>();
    private final Map<String, SticksUser> userMap =
            new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private JdbcPooledConnectionSource connectionSource;
    private final FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        connectToDatabase(
                config.getString("mysql.host"),
                config.getString("mysql.database"),
                config.getString("mysql.user"),
                config.getString("mysql.pass"));

        registerService(SticksUserService.class, new SticksUserServiceImpl(userMap,
                getDao(SticksUser.class), this));

        new LiteCommandUtil(config).create(
                new SticksCommand(
                        (SticksUserService) getService(SticksUserService.class),
                        userMap,
                        config,
                        miniMessage
                )
        );
    }

    @Override
    @SneakyThrows
    public void onDisable() {
        if(connectionSource != null) connectionSource.close();
    }

    @SneakyThrows
    private void connectToDatabase(String host, String database, String user, String pass) {
        connectionSource = new JdbcPooledConnectionSource("jdbc:mysql://" + host + "/" + database + "?useSSL=true&autoReconnect=true");
        connectionSource.setUsername(user);
        connectionSource.setPassword(pass);
        connectionSource.setMaxConnectionsFree(5);

        TableCreatorUtil.create(connectionSource);
        DaoCreatorUtil.create(connectionSource);
    }

    public Object getService(Class<?> serviceClass) {
        Object object = serviceMap.get(serviceClass);
        if(object instanceof Service) {
            return object;
        }
        return null;
    }

    @SuppressWarnings("all")
    private void registerService(Class<?> serviceClass, Object service) {
        ((Service) service).enable();
        serviceMap.put(serviceClass, service);
    }

    @SuppressWarnings("all")
    private  <D extends Dao<T, ?>, T> D getDao(Class<T> daoClass) {
        return DaoManager.lookupDao(connectionSource, daoClass);
    }
}
