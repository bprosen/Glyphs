package com.renatusnetwork.glyphs.managers;

import com.renatusnetwork.glyphs.Glyphs;
import com.renatusnetwork.glyphs.storage.HikariConnection;
import com.renatusnetwork.glyphs.storage.Tables;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;

public class DatabaseManager {

    private static DatabaseManager instance;

    public static DatabaseManager getInstance() {
        return instance == null ? instance = new DatabaseManager() : instance;
    }

    private final HikariConnection connection;

    public static final String PLAYERS_TABLE = "players";
    public static final String TAGS_TABLE = "tags";
    public static final String FAVORITE_TAGS_TABLE = "favorite_tags";

    public DatabaseManager() {
        connection = new HikariConnection();
    }

    public Connection getConnection() throws SQLException {
        return connection.get();
    }

    public void initTables() {
        HashSet<String> tables = Tables.getTables();

        // initialize all tables...
        if (tables.isEmpty()) {
            Tables.createTables();
            Tables.createKeys();
            Glyphs.getLog().info("Tables have successfully been created");
        }
    }

    public void close() {
        connection.close();
    }
}
