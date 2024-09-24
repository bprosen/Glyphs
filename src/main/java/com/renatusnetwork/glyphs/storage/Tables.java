package com.renatusnetwork.glyphs.storage;

import com.renatusnetwork.glyphs.managers.DatabaseManager;
import com.renatusnetwork.glyphs.utils.database.DatabaseUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class Tables {

    public static HashSet<String> getTables()
    {
        HashSet<String> tableNames = new HashSet<>();

        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            ResultSet rs = connection.getMetaData().getTables(null, null, "%", null);

            while (rs.next())
                tableNames.add(rs.getString(3));
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return tableNames;
    }

    public static void createTables() {
        createPlayers();
        createTags();
        createFavoriteTags();
    }

    public static void createKeys() {
        createPlayersKeys();
        createFavoriteTagsKeys();
    }

    public static void createPlayers() {
        DatabaseUtils.run("CREATE TABLE " + DatabaseManager.PLAYERS_TABLE + " (" +
                "uuid CHAR(36) NOT NULL, " +
                "name VARCHAR(16) NOT NULL, " +
                "current_tag VARCHAR(20) DEFAULT NULL, " +
                "PRIMARY KEY(uuid))"
        );
    }

    public static void createPlayersKeys() {
        DatabaseUtils.run("ALTER TABLE " + DatabaseManager.PLAYERS_TABLE + " ADD CONSTRAINT " + DatabaseManager.PLAYERS_TABLE + "_current_tag_fk " +
                "FOREIGN KEY(current_tag) REFERENCES " + DatabaseManager.TAGS_TABLE + "(name)" +
                "ON UPDATE CASCADE " +
                "ON DELETE SET NULL"
        );
    }

    public static void createTags() {
        DatabaseUtils.run("CREATE TABLE " + DatabaseManager.TAGS_TABLE + " (" +
                "name VARCHAR(20) NOT NULL, " +
                "title VARCHAR(100) DEFAULT NULL, " +
                "creator_name VARCHAR(16) NOT NULL, " +
                "creation_date INT NOT NULL, " +
                "PRIMARY KEY(name))"
        );
    }

    public static void createFavoriteTags() {
        DatabaseUtils.run("CREATE TABLE " + DatabaseManager.FAVORITE_TAGS_TABLE + " (" +
                "player_uuid CHAR(36) NOT NULL, " +
                "tag_name VARCHAR(20) NOT NULL, " +
                "PRIMARY KEY (player_uuid))"
        );
    }

    public static void createFavoriteTagsKeys() {
        DatabaseUtils.run("ALTER TABLE " + DatabaseManager.FAVORITE_TAGS_TABLE + " ADD CONSTRAINT " + DatabaseManager.FAVORITE_TAGS_TABLE + "_player_uuid_fk " +
                "FOREIGN KEY(player_uuid) REFERENCES " + DatabaseManager.PLAYERS_TABLE + "(uuid) " +
                "ON UPDATE CASCADE " +
                "ON DELETE CASCADE, " +
                "ADD CONSTRAINT " + DatabaseManager.FAVORITE_TAGS_TABLE + "_tag_name_fk " +
                "FOREIGN KEY(tag_name) REFERENCES " + DatabaseManager.TAGS_TABLE + "(name) " +
                "ON UPDATE CASCADE " +
                "ON DELETE CASCADE"
        );
    }
}