package ru.vidoskim.sticks.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vidoskim.sticks.dao.impl.SticksUserDaoImpl;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "players", daoClass = SticksUserDaoImpl.class)
public class SticksUser {
    @DatabaseField(generatedId = true, unique = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false, columnName = "is_sticks_enabled")
    private boolean isSticksEnabled;
}
