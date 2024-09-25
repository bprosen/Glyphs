package com.renatusnetwork.glyphs.utils.database;

import com.renatusnetwork.glyphs.Glyphs;
import com.renatusnetwork.glyphs.managers.DatabaseManager;
import com.renatusnetwork.glyphs.objects.menus.types.FilterType;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.*;

public class DatabaseUtils {

    public static List<HashMap<String, String>> getResults(String tableName, String selection, String trailingSQL, Object... parameters) {
        List<HashMap<String, String>> finalResults = new ArrayList<>();

        String query = "SELECT " + selection + " FROM " + tableName;

        if (!trailingSQL.isEmpty())
            query += " " + trailingSQL;

        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);

            // secure
            for (int i = 0; i < parameters.length; i++)
                statement.setObject(i + 1, parameters[i]); // who knows why it starts at 1

            ResultSet results = statement.executeQuery();
            while (results.next()) {
                // parse results
                ResultSetMetaData meta = results.getMetaData();

                HashMap<String, String> resultMap = new HashMap<>();

                int columns = meta.getColumnCount();

                for (int i = 1; i <= columns; ++i)
                    resultMap.put(meta.getColumnName(i), results.getString(i));

                finalResults.add(resultMap);
            }
        }
        catch (SQLException exception) {
            Glyphs.getLog().info("Error in DatabaseQueries.getResults(" + tableName + ", " + trailingSQL + ")");
            Glyphs.getLog().info("Query='" + query + "'");

            exception.printStackTrace();
        }

        return finalResults;
    }

    public static HashMap<String, String> getResult(String tableName, String selection, String trailingSQL, Object... parameters) {
        // this is a use case where we are using a primary key to get a single result, just cleaner code
        List<HashMap<String, String>> results = getResults(tableName, selection, trailingSQL, parameters);

        return !results.isEmpty() ? results.get(0) : new HashMap<>();
    }

    public static void runAync(String query, Object... params) {
        new BukkitRunnable() {
            @Override
            public void run() {
                DatabaseUtils.run(query, params);
            }
        }.runTaskAsynchronously(Glyphs.getInstance());
    }

    public static void run(String query, Object... params) {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);

            for (int i = 0; i < params.length; i++)
                statement.setObject(i + 1, params[i]); // who knows why it starts at 1

            statement.executeUpdate();
        } catch (SQLException exception) {
            Glyphs.getLog().info("Error occured running async query: " + query + ", and params: " + Arrays.toString(params));
            exception.printStackTrace();
        }
    }

    public static String filterKey(FilterType type) {
        return "filter_" + type.name().toLowerCase();
    }
}
