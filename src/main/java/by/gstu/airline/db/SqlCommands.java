package main.java.by.gstu.airline.db;

import java.util.ResourceBundle;

/**
 * Class with method reading SQL commands from .properties file
 */
public class SqlCommands {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("main.resources.db_commands");

    /**
     * Gets from db_commands file an sql command according with given String command
     * and returns it as a string
     *
     * @param command command
     * @return sql command
     */
    public static String getCommand(String command) {
        return resourceBundle.getString(command);
    }
}
